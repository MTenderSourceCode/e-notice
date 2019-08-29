package com.procurement.notice.infrastructure.dto.tender.cancel

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.notice.infrastructure.bind.date.JsonDateTimeDeserializer
import com.procurement.notice.infrastructure.bind.date.JsonDateTimeSerializer
import java.time.LocalDateTime

data class CancelStandStillPeriodRequest(
    @field:JsonProperty("standstillPeriod") @param:JsonProperty("standstillPeriod") val standstillPeriod: StandstillPeriod,
    @field:JsonProperty("amendment") @param:JsonProperty("amendment") val amendment: Amendment,
    @field:JsonProperty("tender") @param:JsonProperty("tender") val tender: Tender,

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @field:JsonProperty("bids") @param:JsonProperty("bids") val bids: List<Bid>?
) {
    data class StandstillPeriod(
        @JsonDeserialize(using = JsonDateTimeDeserializer::class)
        @JsonSerialize(using = JsonDateTimeSerializer::class)
        @field:JsonProperty("startDate") @param:JsonProperty("startDate") val startDate: LocalDateTime,

        @JsonDeserialize(using = JsonDateTimeDeserializer::class)
        @JsonSerialize(using = JsonDateTimeSerializer::class)
        @field:JsonProperty("endDate") @param:JsonProperty("endDate") val endDate: LocalDateTime
    )

    data class Amendment(
        @field:JsonProperty("rationale") @param:JsonProperty("rationale") val rationale: String,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @field:JsonProperty("description") @param:JsonProperty("description") val description: String?,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @field:JsonProperty("documents") @param:JsonProperty("documents") val documents: List<Document>?
    ) {

        data class Document(
            @field:JsonProperty("documentType") @param:JsonProperty("documentType") val documentType: String,

            @JsonDeserialize(using = JsonDateTimeDeserializer::class)
            @JsonSerialize(using = JsonDateTimeSerializer::class)
            @field:JsonProperty("datePublished") @param:JsonProperty("datePublished") val datePublished: LocalDateTime,

            @field:JsonProperty("url") @param:JsonProperty("url") val url: String,
            @field:JsonProperty("id") @param:JsonProperty("id") val id: String,
            @field:JsonProperty("title") @param:JsonProperty("title") val title: String,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @field:JsonProperty("description") @param:JsonProperty("description") val description: String?
        )
    }

    data class Tender(
        @field:JsonProperty("statusDetails") @param:JsonProperty("statusDetails") val statusDetails: String
    )

    data class Bid(
        @field:JsonProperty("details") @param:JsonProperty("details") val details: List<Detail>
    ) {

        data class Detail(
            @field:JsonProperty("id") @param:JsonProperty("id") val id: String,
            @field:JsonProperty("status") @param:JsonProperty("status") val status: String,
            @field:JsonProperty("statusDetails") @param:JsonProperty("statusDetails") val statusDetails: String
        )
    }
}
