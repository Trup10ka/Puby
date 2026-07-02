package me.trup10ka.puby.event

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.trup10ka.puby.data.PubyEventMember
import me.trup10ka.puby.receipt.PubyReceipt

class PubyEvent(
    val id: Int,
    var name: String,
    var description: String?,
    var place: String?,
    var date: LocalDate?,
    var time: LocalTime?,
    var pubyReceipt: PubyReceipt?,
    private val creator: PubyEventMember,
    private val members: MutableList<PubyEventMember> = mutableListOf()
)
{
    fun addMember(member: PubyEventMember): Boolean
    {
        if (members.any { it.discordUsername == member.discordUsername })
            return false

        members.add(member)
        return true
    }

    fun formatMembersAsDiscordEmbed() = members.joinToString("\n") { "## `${it.discordUsername}`" }

    fun isMemberOfEvent(discordId: String) = members.any { it.discordUsername == discordId } || creator.discordUsername == discordId

    fun removeMember(discordId: String): Boolean
    {
        if (!isMemberOfEvent(discordId))
            return false

        return members.remove(members.find { it.discordUsername == discordId })
    }

    fun toFancyString(): String
    {
        val description = "Description: ${ if (description != null) "*$description*" else "*Not provided*" }\n"
        val place = "Place: ${ if (place != null) "*$place*" else "*Not provided*" }\n"
        val date = "Date: ${ if (date != null) "*$date*" else "*Not provided*" }\n"
        val time = "Time: ${ if (time != null) "*$time*" else "*Not provided*" }\n"
        val pubyReceipt = "Receipt: ${ if (pubyReceipt != null) "*Yes*" else "*No*" }\n"
        val creator = "Creator: `${creator.discordUsername}`"

        return """
            |## $name
            | $description
            | $place
            | $date
            | $time
            | $pubyReceipt
            | $creator
        """.trimMargin()
    }
}
