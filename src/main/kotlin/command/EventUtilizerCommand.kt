package me.trup10ka.puby.command

import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.respondEmbeddedFail

abstract class EventUtilizerCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    suspend fun getEvent(pubyEventManager: PubyEventManager, id: Int, responseBehavior: DeferredPublicMessageInteractionResponseBehavior): PubyEvent?
    {
        val event = pubyEventManager.pubyEvents.find { it.id == id }

        if (event == null)
            responseBehavior.respondEmbeddedFail { title = "Event not found" }

        return event
    }
}
