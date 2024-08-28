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
    var receipt: Receipt?,
    private val members: MutableList<PubyEventMember> = mutableListOf()
)
{
    fun addMember(member: PubyEventMember): Boolean
    {
        if (members.any { it.discordId == member.discordId })
            return false

        members.add(member)
        return true
    }

    fun toFancyString(): String
    {
        val description = if (description.isNotBlank()) "Description: $description\n" else ""
        val place = "Place: ${ if (place.isNotBlank()) "*$place*\n" else "*Not provided*" }"
        val date = "Date: ${ if (date != null) "Date: *$date*\n" else "*Not provided*\n" }"
        val time = "Time: ${ if (time != null) "Time: *$time*\n" else "*Not provided*\n" }"
        val receipt = "Receipt: ${ if (receipt != null) "Receipt: *Yes*" else "Receipt: *No*" }"

        return """
            |**$name**
            | $description
            | $place
            | $date
            | $time
            | $receipt
        """.trimMargin()
    }
}
