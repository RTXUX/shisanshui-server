package xyz.rtxux.game.shisanshui.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class BindDTO(
        @JsonProperty("student_number")
        val studentNumber: String,
        @JsonProperty("student_password")
        val studentPassword: String
)