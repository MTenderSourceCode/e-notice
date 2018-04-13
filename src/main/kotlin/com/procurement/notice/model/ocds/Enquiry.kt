package com.procurement.notice.model.ocds

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.procurement.point.databinding.JsonDateDeserializer
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "date", "author", "title", "description", "answer", "dateAnswered", "relatedItem", "relatedLot", "threadID")
data class Enquiry(

        @JsonProperty("id")
        val id: String?,

        @JsonProperty("date")
        @JsonDeserialize(using = JsonDateDeserializer::class)
        val date: LocalDateTime?,

        @JsonProperty("author")
        val author: OrganizationReference?,

        @JsonProperty("title")
        val title: String?,

        @JsonProperty("description")
        val description: String,

        @JsonProperty("answer")
        private val answer: String?,

        @JsonProperty("dateAnswered")
        @JsonDeserialize(using = JsonDateDeserializer::class)
        val dateAnswered: LocalDateTime?,

        @JsonProperty("relatedItem")
        val relatedItem: String?,

        @JsonProperty("relatedLot")
        val relatedLot: String?,

        @JsonProperty("threadID")
        val threadID: String?
)
