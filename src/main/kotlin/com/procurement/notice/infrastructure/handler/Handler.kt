package com.procurement.notice.infrastructure.handler

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.notice.domain.utils.Action

interface Handler<T : Action, R : Any> {
    val action: T
    fun handle(node: JsonNode): R
}
