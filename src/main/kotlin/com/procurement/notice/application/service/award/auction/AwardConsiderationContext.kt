package com.procurement.notice.application.service.award.auction

import java.time.LocalDateTime

data class AwardConsiderationContext(
    val cpid: String,
    val ocid: String,
    val stage: String,
    val releaseDate: LocalDateTime
)