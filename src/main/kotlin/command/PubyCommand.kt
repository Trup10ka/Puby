package me.trup10ka.puby.command

import dev.kord.core.Kord

interface PubyCommand
{
    suspend fun init(kordClient: Kord)

    suspend fun registerListener(kordClient: Kord)
}
