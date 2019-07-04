package com.procurement.notice.model.contract.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.procurement.notice.model.contract.Can
import com.procurement.notice.model.contract.ContractTenderLot
import com.procurement.notice.model.ocds.Award
import com.procurement.notice.model.ocds.Classification
import com.procurement.notice.model.ocds.Contract
import com.procurement.notice.model.ocds.ContractTerm

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CreateAcDto @JsonCreator constructor(

        val cans: HashSet<Can>,

        val contract: Contract,

        val contractedAward: Award,

        val contractTerm: ContractTerm,

        val contractedTender: CreateAcTender
)

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CreateAcTender @JsonCreator constructor(

        val id: String?,

        var classification: Classification,

        val procurementMethod: String,

        val procurementMethodDetails: String,

        val mainProcurementCategory: String,

        var lots: HashSet<ContractTenderLot>

)