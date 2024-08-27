package me.trup10ka.puby.command

import dev.kord.core.Kord
import me.trup10ka.puby.event.PubyEventManager

class AlterEventCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    override suspend fun init(kordClient: Kord)
    {
    }

    override suspend fun registerListener(kordClient: Kord, pubyEventManager: PubyEventManager)
    {
    }
}
