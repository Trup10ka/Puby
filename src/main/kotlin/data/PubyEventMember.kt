package me.trup10ka.puby.data

import dev.kord.common.entity.Snowflake

data class PubyEventMember(
    val discordUsername: String,
    val snowflake: Snowflake
)
{
    fun toDiscordMentionFormat() = "<@${snowflake.value}>"
}
