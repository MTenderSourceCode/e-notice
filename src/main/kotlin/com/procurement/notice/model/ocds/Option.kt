package com.procurement.notice.model.ocds

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("hasOptions", "optionDetails")
data class Option(

        @JsonProperty("hasOptions")
        @get:JsonProperty("hasOptions")
        val hasOptions: Boolean?,

        @JsonProperty("optionDetails")
        val optionDetails: String?
)