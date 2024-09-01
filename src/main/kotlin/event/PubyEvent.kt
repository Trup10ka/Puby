package me.trup10ka.puby.event

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.trup10ka.puby.data.PubyEventMember
import me.trup10ka.puby.receipt.Receipt

class PubyEvent(
    val id: Int,
    var name: String,
    var description: String?,
    var place: String?,
    var date: LocalDate?,
    var time: LocalTime?,
    var receipt: Receipt?,
    private val creator: PubyEventMember,
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

    fun isMemberOfEvent(discordId: String) = members.any { it.discordId == discordId } || creator.discordId == discordId

    fun removeMember(discordId: String): Boolean
    {
        if (!isMemberOfEvent(discordId))
            return false

        return members.remove(members.find { it.discordId == discordId })
    }

    fun toFancyString(): String
    {
        val description = "Description: ${ if (description != null) "*$description*" else "*Not provided*" }\n"
        val place = "Place: ${ if (place != null) "*$place*" else "*Not provided*" }\n"
        val date = "Date: ${ if (date != null) "Date: *$date*\n" else "*Not provided*\n" }"
        val time = "Time: ${ if (time != null) "Time: *$time*\n" else "*Not provided*\n" }"
        val receipt = "Receipt: ${ if (receipt != null) "*Yes*" else "*No*" }"
        val creator = "Creator: `${creator.discordId}`"

        return """
            |## $name
            | $description
            | $place
            | $date
            | $time
            | $receipt
            | $creator
        """.trimMargin()
    }
}
