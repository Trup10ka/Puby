package me.trup10ka.puby.command

import dev.kord.core.Kord
import me.trup10ka.puby.event.PubyEventManager

class PubyCommandManager(
    private val kordClient: Kord,
    private val pubyEventManager: PubyEventManager
)
{
    private val commands = listOf(
        CreateEventCommand,
        DeleteEventCommand,
        AlterEventCommand,
        AddMemberCommand,
        RemoveMemberCommand,
        ListMembersCommand,
    )

    suspend fun initCommands()
    {
        commands.forEach { it.init(kordClient) }
    }

    suspend fun registerListeners()
    {
        commands.forEach { it.registerListener(kordClient, pubyEventManager) }
    }
}
