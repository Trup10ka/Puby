package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.user
import dev.kord.rest.builder.interaction.string
import me.trup10ka.puby.event.PubyEventManager

object AddMemberCommand : PubyCommand
{
    override suspend fun init(kordClient: Kord)
    {
        kordClient.createGlobalChatInputCommand(
            "am",
            "Adds member to an event"
        ) {
            string("name", "The name of the event") { required = true }
            user("discord_tag", "The discord tag of the member to add") { required = true }
        }

        kordClient.createGlobalChatInputCommand(
            "am",
            "Adds member to an event"
        ) {
            integer("id", "The id of the event") { required = true }
            user("discord_tag", "The discord tag of the member to add") { required = true }
        }
    }

    override suspend fun registerListener(kordClient: Kord, pubyEventManager: PubyEventManager)
    {
    }
}
