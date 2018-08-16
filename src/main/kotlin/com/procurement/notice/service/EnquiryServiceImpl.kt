package com.procurement.notice.service

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.notice.exception.ErrorException
import com.procurement.notice.exception.ErrorType
import com.procurement.notice.model.bpe.ResponseDto
import com.procurement.notice.model.ocds.Tag
import com.procurement.notice.model.tender.dto.UnsuspendTenderDto
import com.procurement.notice.model.tender.enquiry.RecordEnquiry
import com.procurement.notice.utils.toJson
import com.procurement.notice.utils.toObject
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface EnquiryService {

    fun createEnquiry(cpid: String,
                      ocid: String,
                      stage: String,
                      releaseDate: LocalDateTime,
                      data: JsonNode): ResponseDto

    fun addAnswer(cpid: String,
                  ocid: String,
                  stage: String,
                  releaseDate: LocalDateTime,
                  data: JsonNode): ResponseDto

    fun unsuspendTender(cpid: String,
                        ocid: String,
                        stage: String,
                        releaseDate: LocalDateTime,
                        data: JsonNode): ResponseDto
}

@Service
class EnquiryServiceImpl(private val releaseService: ReleaseService,
                         private val organizationService: OrganizationService) : EnquiryService {

    companion object {
        private const val ENQUIRY_JSON = "enquiry"
    }

    override fun createEnquiry(cpid: String,
                               ocid: String,
                               stage: String,
                               releaseDate: LocalDateTime,
                               data: JsonNode): ResponseDto {
        val enquiry = toObject(RecordEnquiry::class.java, toJson(data.get(ENQUIRY_JSON)))
        val recordEntity = releaseService.getRecordEntity(cpid, ocid)
        val record = releaseService.getRecord(recordEntity.jsonData)
        record.apply {
            id = releaseService.newReleaseId(ocid)
            date = releaseDate
            tender.hasEnquiries = true
        }
        var enquiries = record.tender.enquiries ?: hashSetOf()
        if (enquiries.asSequence().none { it.id == enquiry.id }) {
            enquiries.add(enquiry)
            organizationService.processRecordPartiesFromEnquiry(record, enquiry)
            record.tender.enquiries = enquiries
            if (enquiries.size == 1) {
                val msEntity = releaseService.getMsEntity(cpid)
                val ms = releaseService.getMs(msEntity.jsonData)
                ms.apply {
                    id = releaseService.newReleaseId(ocid)
                    date = releaseDate
                    tag = listOf(Tag.COMPILED)
                    tender.hasEnquiries = true
                }
                releaseService.saveMs(cpid, ms)
            }
        }
        releaseService.saveRecord(cpid, stage, record)
        return releaseService.responseDto(cpid, ocid)
    }

    override fun addAnswer(cpid: String,
                           ocid: String,
                           stage: String,
                           releaseDate: LocalDateTime,
                           data: JsonNode): ResponseDto {
        val enquiry = toObject(RecordEnquiry::class.java, toJson(data.get(ENQUIRY_JSON)))
        val recordEntity = releaseService.getRecordEntity(cpid, ocid)
        val record = releaseService.getRecord(recordEntity.jsonData)
        record.apply {
            id = releaseService.newReleaseId(ocid)
            date = releaseDate
        }
        record.tender.enquiries?.asSequence()?.firstOrNull { it.id == enquiry.id }?.apply {
            this.answer = enquiry.answer
            this.dateAnswered = enquiry.dateAnswered
        } ?: throw ErrorException(ErrorType.ENQUIRY_NOT_FOUND)
        releaseService.saveRecord(cpid, stage, record)
        return releaseService.responseDto(cpid, ocid)
    }

    override fun unsuspendTender(cpid: String,
                                 ocid: String,
                                 stage: String,
                                 releaseDate: LocalDateTime,
                                 data: JsonNode): ResponseDto {
        val dto = toObject(UnsuspendTenderDto::class.java, toJson(data))
        val recordEntity = releaseService.getRecordEntity(cpid, ocid)
        val record = releaseService.getRecord(recordEntity.jsonData)
        record.apply {
            id = releaseService.newReleaseId(ocid)
            date = releaseDate
            tender.statusDetails = dto.tender.statusDetails
            tender.tenderPeriod = dto.tender.tenderPeriod
            tender.enquiryPeriod = dto.tender.enquiryPeriod
        }
        addAnswerToEnquiry(record.tender.enquiries, dto.enquiry)
        releaseService.saveRecord(cpid, stage, record)
        return releaseService.responseDto(cpid, ocid)
    }

    private fun addAnswerToEnquiry(enquiries: HashSet<RecordEnquiry>?, enquiry: RecordEnquiry) {
        enquiries?.asSequence()?.firstOrNull { it.id == enquiry.id }?.apply {
            this.answer = enquiry.answer
            this.dateAnswered = enquiry.dateAnswered
        } ?: throw ErrorException(ErrorType.ENQUIRY_NOT_FOUND)
    }
}