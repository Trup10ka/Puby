package me.trup10ka.puby.command

import dev.kord.core.Kord
import me.trup10ka.puby.event.PubyEventManager

interface PubyCommand
{
    suspend fun init(kordClient: Kord)

    suspend fun registerListener(kordClient: Kord, pubyEventManager: PubyEventManager)
}
