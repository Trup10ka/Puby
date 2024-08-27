package me.trup10ka.puby.command

import dev.kord.core.Kord
import me.trup10ka.puby.event.PubyEventManager

abstract class PubyCommand(val commandName: String, val commandDescription: String)
{
    abstract suspend fun init(kordClient: Kord)

    abstract suspend fun registerListener(kordClient: Kord, pubyEventManager: PubyEventManager)
}
