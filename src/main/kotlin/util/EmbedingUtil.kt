package me.trup10ka.puby.util

import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.embed
import me.trup10ka.puby.data.PubyEventMember

val EMBED_COLOR_SUCCESS = Color( 247, 168, 36)
val EMBED_COLOR_FAIL = Color( 255, 0, 0)
val Snowflake.discordMentionFormat: String
    get() = "<@$value>"


suspend fun DeferredResponseBehavior.respondEmbeddedSuccess(mention: PubyEventMember? = null, embedBuilder: EmbedBuilder.() -> Unit)
{
    respond {

        content = mention?.toDiscordMentionFormat() ?: ""

        embed {
            embedBuilder()
            color = EMBED_COLOR_SUCCESS
        }
    }
}

suspend fun DeferredResponseBehavior.respondEmbeddedSuccess(mention: Snowflake, embedBuilder: EmbedBuilder.() -> Unit)
{
    respond {

        content = mention.discordMentionFormat

        embed {
            embedBuilder()
            color = EMBED_COLOR_SUCCESS
        }
    }
}

suspend fun DeferredResponseBehavior.respondEmbeddedFail(mention: PubyEventMember? = null, embedBuilder: EmbedBuilder.() -> Unit)
{
    respond {

        content = mention?.toDiscordMentionFormat() ?: ""

        embed {
            embedBuilder()
            color = EMBED_COLOR_FAIL
        }
    }
}
