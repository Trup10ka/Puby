package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.integer
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedSuccess
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_ID

class DeleteEventCommand(
    commandName: String,
    commandDescription: String
) : EventUtilizerCommand(commandName, commandDescription)
{
    override suspend fun init(kordClient: Kord)
    {
        kordClient.createGlobalChatInputCommand(
            commandName,
            commandDescription
        ) {
            integer(EVENT_ID.argName, EVENT_ID.description) { required = true }
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers["id"]!!.toInt(), responseBehavior) ?: return

        pubyEventManager.deleteEvent(event.id)
        responseBehavior.respondEmbeddedSuccess { title = "Event deleted" }
    }
}
