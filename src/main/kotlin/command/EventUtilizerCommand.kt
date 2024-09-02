package me.trup10ka.puby.command

import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedFail

abstract class EventUtilizerCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    suspend fun getEvent(pubyEventManager: PubyEventManager, id: Int, responseBehavior: DeferredResponseBehavior): PubyEvent?
    {
        val event = pubyEventManager.pubyEvents.find { it.id == id }

        if (event == null)
            responseBehavior.respondEmbeddedFail { title = "Event not found" }

        return event
    }

    suspend fun isCallerMemberOfEvent(event: PubyEvent, discordId: String, responseBehavior: DeferredResponseBehavior): Boolean
    {
        if (!event.isMemberOfEvent(discordId))
        {
            responseBehavior.respondEmbeddedFail { title = "You are not a member of this event" }
            return false
        }
        return true
    }
}
