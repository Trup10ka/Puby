package me.trup10ka.puby.event

import dev.kord.common.entity.Snowflake

data class PubyEventMember(
    val discordId: String,
    val snowflake: Snowflake
)
{
    fun toDiscordMentionFormat() = "<@${snowflake.value}>"
}
