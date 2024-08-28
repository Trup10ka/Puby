package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import me.trup10ka.puby.command.CommandsAbbreviation.CREATE_EVENT
import me.trup10ka.puby.command.CommandsAbbreviation.DELETE_EVENT
import me.trup10ka.puby.command.CommandsAbbreviation.ALTER_EVENT
import me.trup10ka.puby.command.CommandsAbbreviation.LIST_MEMBERS
import me.trup10ka.puby.command.CommandsAbbreviation.REMOVE_MEMBER
import me.trup10ka.puby.command.CommandsAbbreviation.ADD_MEMBER
import me.trup10ka.puby.command.event.AlterEventCommand
import me.trup10ka.puby.command.event.CreateEventCommand
import me.trup10ka.puby.command.event.DeleteEventCommand
import me.trup10ka.puby.command.member.AddMemberCommand
import me.trup10ka.puby.command.member.ListMembersCommand
import me.trup10ka.puby.command.member.RemoveMemberCommand
import me.trup10ka.puby.event.PubyEventManager

class PubyCommandManager(
    private val kordClient: Kord,
    private val pubyEventManager: PubyEventManager
)
{
    private val commands = mapOf(
        CREATE_EVENT to CreateEventCommand(CREATE_EVENT.abbreviation, "Create a new event"),
        DELETE_EVENT to DeleteEventCommand(DELETE_EVENT.abbreviation, "Delete an event"),
        ALTER_EVENT to AlterEventCommand(ALTER_EVENT.abbreviation, "Alter an event"),
        ADD_MEMBER to AddMemberCommand(ADD_MEMBER.abbreviation, "Add a member to an event"),
        REMOVE_MEMBER to RemoveMemberCommand(REMOVE_MEMBER.abbreviation, "Remove a member from an event"),
        LIST_MEMBERS to ListMembersCommand(LIST_MEMBERS.abbreviation, "List members of an event"),
    )

    suspend fun initCommands()
    {
        commands.forEach { it.value.init(kordClient) }
    }

    fun registerListeners()
    {
        kordClient.on<ChatInputCommandInteractionCreateEvent> {

            val response = interaction.deferPublicResponse()
            val command = interaction.command

            when (command.rootName)
            {
                CREATE_EVENT.abbreviation -> commands[CREATE_EVENT]!!.handleCommand(response, interaction, pubyEventManager)
                DELETE_EVENT.abbreviation -> commands[DELETE_EVENT]!!.handleCommand(response, interaction, pubyEventManager)
                ALTER_EVENT.abbreviation -> commands[ALTER_EVENT]!!.handleCommand(response, interaction, pubyEventManager)
                ADD_MEMBER.abbreviation -> commands[ADD_MEMBER]!!.handleCommand(response, interaction, pubyEventManager)
                REMOVE_MEMBER.abbreviation -> commands[REMOVE_MEMBER]!!.handleCommand(response, interaction, pubyEventManager)
                LIST_MEMBERS.abbreviation -> commands[LIST_MEMBERS]!!.handleCommand(response, interaction, pubyEventManager)
            }
        }
    }
}
