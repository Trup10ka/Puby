package me.trup10ka.puby.command

import dev.kord.core.Kord

class PubyCommandManager(private val kordClient: Kord)
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
        commands.forEach { it.registerListener(kordClient) }
    }
}
