package me.trup10ka.puby.event

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.trup10ka.puby.receipt.Receipt

class PubyEvent(
    val id: Int,
    var name: String,
    var description: String,
    var place: String,
    var date: LocalDate?,
    var time: LocalTime?,
    val members: MutableList<PubyEventMember> = mutableListOf(),
    var receipt: Receipt?
)
