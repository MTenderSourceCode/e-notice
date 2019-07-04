package com.procurement.notice.model.tender.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.procurement.notice.model.contract.Can
import com.procurement.notice.model.ocds.Period

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateCanDto @JsonCreator constructor(

        val can: Can
)