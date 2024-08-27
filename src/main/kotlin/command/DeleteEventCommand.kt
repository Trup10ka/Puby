package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.string
import me.trup10ka.puby.event.PubyEventManager

class DeleteEventCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    override suspend fun init(kordClient: Kord)
    {
        kordClient.createGlobalChatInputCommand(
            commandName,
            commandDescription
        ) {
            string("name", "The name of the event") { required = true }
        }

        kordClient.createGlobalChatInputCommand(
            commandName,
            commandDescription
        ) {
            integer("id", "The id of the event") { required = true }
        }
    }

    override suspend fun registerListener(kordClient: Kord, pubyEventManager: PubyEventManager)
    {
        kordClient.on<ChatInputCommandInteractionCreateEvent> {

            val response = interaction.deferPublicResponse()
            val command = interaction.command

            if (command.rootName != "de") return@on

            val event = pubyEventManager.getEventWithIdOrName(command)

            if (event == null)
            {
                response.respond { content = "Event not found" }
                return@on
            }
            else
            {
                pubyEventManager.deleteEvent(event.id)
                response.respond { content = "Event deleted" }
            }
        }
    }
}
