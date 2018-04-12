package com.procurement.notice.service

import com.datastax.driver.core.utils.UUIDs
import com.procurement.notice.model.budget.EI
import com.procurement.notice.model.budget.FS
import com.procurement.notice.model.ocds.RelatedProcess
import com.procurement.notice.model.ocds.RelatedProcessScheme
import com.procurement.notice.model.ocds.RelatedProcessType
import com.procurement.notice.model.tender.dto.CheckFsDto
import com.procurement.notice.model.tender.ms.Ms
import com.procurement.notice.model.tender.record.Record
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
interface RelatedProcessService {

    fun addFsRelatedProcessToEi(ei: EI, fsOcId: String)

    fun addEiRelatedProcessToFs(fs: FS, eiOcId: String)

    fun addMsRelatedProcessToEi(ei: EI, msOcId: String)

    fun addMsRelatedProcessToFs(fs: FS, msOcId: String)

    fun addEiFsRecordRelatedProcessToMs(ms: Ms, checkFs: CheckFsDto, ocId: String, processType: RelatedProcessType)

    fun addMsRelatedProcessToRecord(record: Record, msOcId: String)

    fun addRecordRelatedProcessToMs(record: Record, msOcId: String, processType: RelatedProcessType)

    fun addPervRecordRelatedProcessToRecord(record: Record, prevRecordOcId: String, msOcId: String)

}

@Service
class RelatedProcessServiceImpl : RelatedProcessService {

    @Value("\${uri.budget}")
    private val budgetUri: String? = null
    @Value("\${uri.tender}")
    private val tenderUri: String? = null

    override fun addFsRelatedProcessToEi(ei: EI, fsOcId: String) {
        val relatedProcesses = ei.relatedProcesses ?: hashSetOf()
        val relatedProcess = RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(RelatedProcessType.X_FINANCE_SOURCE),
                scheme = RelatedProcessScheme.OCID,
                identifier = fsOcId,
                uri = getBudgetUri(ei.ocid, fsOcId)
        )
        relatedProcesses.add(relatedProcess)
    }

    override fun addEiRelatedProcessToFs(fs: FS, eiOcId: String) {
        val relatedProcesses = fs.relatedProcesses ?: hashSetOf()
        val relatedProcess = RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(RelatedProcessType.PARENT),
                scheme = RelatedProcessScheme.OCID,
                identifier = eiOcId,
                uri = getBudgetUri(eiOcId, eiOcId)
        )
        relatedProcesses.add(relatedProcess)
    }

    override fun addMsRelatedProcessToEi(ei: EI, msOcId: String) {
        val relatedProcesses = ei.relatedProcesses ?: hashSetOf()
        val relatedProcess = RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(RelatedProcessType.X_EXECUTION),
                scheme = RelatedProcessScheme.OCID,
                identifier = msOcId,
                uri = getTenderUri(msOcId, msOcId)
        )
        relatedProcesses.add(relatedProcess)
    }

    override fun addMsRelatedProcessToFs(fs: FS, msOcId: String) {
        val relatedProcesses = fs.relatedProcesses ?: hashSetOf()
        val relatedProcess = RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(RelatedProcessType.X_EXECUTION),
                scheme = RelatedProcessScheme.OCID,
                identifier = msOcId,
                uri = getTenderUri(msOcId, msOcId)
        )
        relatedProcesses.add(relatedProcess)
    }

    override fun addEiFsRecordRelatedProcessToMs(ms: Ms, checkFs: CheckFsDto, ocId: String, processType: RelatedProcessType) {
        val relatedProcesses = ms.relatedProcesses ?: hashSetOf()
        /*record*/
        relatedProcesses.add(RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(processType),
                scheme = RelatedProcessScheme.OCID,
                identifier = ocId,
                uri = getTenderUri(ms.ocid!!, ocId)))
        /*expenditure items*/
        checkFs.ei.asSequence().forEach { eiCpId ->
            relatedProcesses.add(RelatedProcess(
                    id = UUIDs.timeBased().toString(),
                    relationship = listOf(RelatedProcessType.X_EXPENDITURE_ITEM),
                    scheme = RelatedProcessScheme.OCID,
                    identifier = eiCpId,
                    uri = getBudgetUri(eiCpId, eiCpId))
            )
        }
        /*financial sources*/
        ms.planning?.budget?.budgetBreakdown?.asSequence()?.forEach {
            relatedProcesses.add(RelatedProcess(
                    id = UUIDs.timeBased().toString(),
                    relationship = listOf(RelatedProcessType.X_BUDGET),
                    scheme = RelatedProcessScheme.OCID,
                    identifier = it.id,
                    uri = getBudgetUri(getEiCpIdFromOcId(it.id!!), it.id)))
        }
    }

    override fun addMsRelatedProcessToRecord(record: Record, msOcId: String) {
        val relatedProcesses = record.relatedProcesses ?: hashSetOf()
        /*ms*/
        relatedProcesses.add(RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(RelatedProcessType.PARENT),
                scheme = RelatedProcessScheme.OCID,
                identifier = msOcId,
                uri = getTenderUri(msOcId, msOcId)))
    }

    override fun addRecordRelatedProcessToMs(record: Record, msOcId: String, processType: RelatedProcessType) {
        val relatedProcesses = record.relatedProcesses ?: hashSetOf()
        relatedProcesses.add(RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(processType),
                scheme = RelatedProcessScheme.OCID,
                identifier = record.ocid,
                uri = getTenderUri(msOcId, record.ocid)))
    }

    override fun addPervRecordRelatedProcessToRecord(record: Record, prevRecordOcId: String, msOcId: String) {
        val relatedProcesses = record.relatedProcesses ?: hashSetOf()
        relatedProcesses.add(RelatedProcess(
                id = UUIDs.timeBased().toString(),
                relationship = listOf(RelatedProcessType.X_PRESELECTION),
                scheme = RelatedProcessScheme.OCID,
                identifier = prevRecordOcId,
                uri = getTenderUri(msOcId, prevRecordOcId)))
    }

    private fun getEiCpIdFromOcId(ocId: String): String {
        val pos = ocId.indexOf(FS_SEPARATOR)
        return ocId.substring(0, pos)
    }

    private fun getBudgetUri(cpId: String?, ocId: String?): String {
        return budgetUri + cpId + URI_SEPARATOR + ocId
    }

    private fun getTenderUri(cpId: String, ocId: String?): String {
        return tenderUri + cpId + URI_SEPARATOR + ocId
    }

    companion object {
        private val FS_SEPARATOR = "-FS-"
        private val URI_SEPARATOR = "/"
    }
}
