package me.trup10ka.puby.data

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class PubyEventDTO(
    val name: String,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
    val place: String?,
    val receipt: Boolean
)
