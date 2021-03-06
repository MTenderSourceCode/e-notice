package com.procurement.notice.application.service.award

import java.time.LocalDateTime

data class StartAwardPeriodContext(
    val cpid: String,
    val ocid: String,
    val stage: String,
    val releaseDate: LocalDateTime,
    val startDate: LocalDateTime
)