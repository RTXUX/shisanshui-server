package xyz.rtxux.game.shisanshui.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterDTO(
        val username: String,
        val password: String,
        @JsonProperty("student_number")
        val studentNumber: String? = null,
        @JsonProperty("student_password")
        val studentPassword: String? = null
)