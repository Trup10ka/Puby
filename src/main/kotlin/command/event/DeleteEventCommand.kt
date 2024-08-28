package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.integer
import me.trup10ka.puby.command.PubyCommand
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.respondEmbeddedFail
import me.trup10ka.puby.util.respondEmbeddedSuccess

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

    override suspend fun handleCommand(responseBehavior: DeferredPublicMessageInteractionResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = pubyEventManager.pubyEvents.find { it.id == interaction.command.integers["id"]!!.toInt() }

        if (event == null)
            responseBehavior.respondEmbeddedFail { title = "Event not found" }

        else
        {
            pubyEventManager.deleteEvent(event.id)
            responseBehavior.respondEmbeddedSuccess { title = "Event deleted" }
        }
    }
}
