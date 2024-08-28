package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.InteractionCommand
import dev.kord.rest.builder.interaction.integer
import me.trup10ka.puby.command.PubyCommand
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
            integer("id", "The id of the event") { required = true }
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredPublicMessageInteractionResponseBehavior, command: InteractionCommand, pubyEventManager: PubyEventManager)
    {
        val event = pubyEventManager.pubyEvents.find { it.id == command.integers["id"]!!.toInt() }

        if (event == null)
            responseBehavior.respond { content = "Event not found" }

        else
        {
            pubyEventManager.deleteEvent(event.id)
            responseBehavior.respond { content = "Event deleted" }
        }
    }
}
