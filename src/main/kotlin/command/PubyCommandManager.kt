package me.trup10ka.puby.command

import dev.kord.core.Kord
import me.trup10ka.puby.event.PubyEventManager

class PubyCommandManager(
    private val kordClient: Kord,
    private val pubyEventManager: PubyEventManager
)
{
    private val commands = listOf(
        CreateEventCommand("ce", "Create a new event"),
        DeleteEventCommand("de", "Delete an event"),
        AlterEventCommand("ae", "Alter an event"),
        AddMemberCommand("am", "Add a member to an event"),
        RemoveMemberCommand("rm", "Remove a member from an event"),
        ListMembersCommand("lm", "List members of an event"),
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
