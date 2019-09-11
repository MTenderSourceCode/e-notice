package com.procurement.notice.model.bpe

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.JsonNode
import com.procurement.notice.exception.EnumException
import com.procurement.notice.exception.ErrorException
import com.procurement.notice.exception.ErrorType
import com.procurement.notice.utils.toLocalDateTime
import java.time.LocalDateTime

data class CommandMessage @JsonCreator constructor(

        val id: String,
        val command: CommandType,
        val context: Context,
        val data: JsonNode,
        val version: ApiVersion
)

val CommandMessage.cpid: String
    get() = this.context.cpid
        ?: throw ErrorException(error = ErrorType.CONTEXT, message = "Missing the 'cpid' attribute in context.")

val CommandMessage.ocid: String
    get() = this.context.ocid
        ?: throw ErrorException(error = ErrorType.CONTEXT, message = "Missing the 'ocid' attribute in context.")

val CommandMessage.stage: String
    get() = this.context.stage
        ?: throw ErrorException(error = ErrorType.CONTEXT, message = "Missing the 'stage' attribute in context.")

val CommandMessage.startDate: LocalDateTime
    get() = this.context.startDate?.toLocalDateTime()
        ?: throw ErrorException(error = ErrorType.CONTEXT, message = "Missing the 'startDate' attribute in context.")

data class Context @JsonCreator constructor(
        val operationId: String?,
        val cpid: String,
        val ocid: String?,
        val stage: String,
        val prevStage: String?,
        val processType: String?,
        val operationType: String,
        val phase: String?,
        val owner: String?,
        val country: String?,
        val language: String?,
        val pmd: String?,
        val startDate: String?,
        val endDate: String?,
        val isAuction: Boolean?,
        val timeStamp: Long
)

enum class CommandType(private val value: String) {
    CREATE_RELEASE("createRelease");

    @JsonValue
    fun value(): String {
        return this.value
    }

    override fun toString(): String {
        return this.value
    }
}

enum class ApiVersion(private val value: String) {
    V_0_0_1("0.0.1");

    @JsonValue
    fun value(): String {
        return this.value
    }

    override fun toString(): String {
        return this.value
    }
}

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ResponseDto(

        val errors: List<ResponseErrorDto>? = null,
        val data: DataResponseDto? = null,
        val id: String? = null
)

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class DataResponseDto(
        val cpid: String? = null,
        val ocid: String? = null,
        val amendmentsIds: List<String>? = null,
        val awardsIds: List<String>? = null,
        val releaseId: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseErrorDto(

        val code: String,

        val description: String?
)

fun getExceptionResponseDto(exception: Exception): ResponseDto {
    return ResponseDto(
            errors = listOf(ResponseErrorDto(
                    code = "400.02.00",
                    description = exception.message
            )))
}

fun getErrorExceptionResponseDto(error: ErrorException, id: String? = null): ResponseDto {
    return ResponseDto(
            errors = listOf(ResponseErrorDto(
                    code = "400.02." + error.code,
                    description = error.msg
            )),
            id = id)
}

fun getEnumExceptionResponseDto(error: EnumException, id: String? = null): ResponseDto {
    return ResponseDto(
            errors = listOf(ResponseErrorDto(
                    code = "400.02." + error.code,
                    description = error.msg
            )),
            id = id)
}

