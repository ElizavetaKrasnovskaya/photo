package com.vironit.data.retrofit.api

enum class RequestError(val statusCode: Int) {
    BAD_REQUEST(400) {
        override fun toString() = "Wrong parameter"
    },
    UNAUTHORIZED(401) {
        override fun toString() = "Not authorized"
    },
    FORBIDDEN(403) {
        override fun toString() = "You do not have permission"
    },
    NOT_FOUND(404) {
        override fun toString() = "Not found"
    },
    INTERNAL_ERROR(500) {
        override fun toString() = "Internal error"
    },
    SERVICE_UNAVAILABLE(503) {
        override fun toString() = "Service not available"
    },
    UNKNOWN(0) {
        override fun toString() = "Unknown error"
    };
    override fun toString(): String {
        return statusCode.toString()
    }
    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.statusCode == value }
    }
}