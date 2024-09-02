package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import me.trup10ka.puby.command.PubyCommands.CREATE_EVENT
import me.trup10ka.puby.command.PubyCommands.DELETE_EVENT
import me.trup10ka.puby.command.PubyCommands.ALTER_EVENT
import me.trup10ka.puby.command.PubyCommands.LIST_MEMBERS
import me.trup10ka.puby.command.PubyCommands.REMOVE_MEMBER
import me.trup10ka.puby.command.PubyCommands.ADD_MEMBER
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
        CREATE_EVENT to CreateEventCommand(CREATE_EVENT.abbreviation, CREATE_EVENT.description),
        DELETE_EVENT to DeleteEventCommand(DELETE_EVENT.abbreviation, DELETE_EVENT.description),
        ALTER_EVENT to AlterEventCommand(ALTER_EVENT.abbreviation, ALTER_EVENT.description),
        ADD_MEMBER to AddMemberCommand(ADD_MEMBER.abbreviation, ADD_MEMBER.description),
        REMOVE_MEMBER to RemoveMemberCommand(REMOVE_MEMBER.abbreviation, REMOVE_MEMBER.description),
        LIST_MEMBERS to ListMembersCommand(LIST_MEMBERS.abbreviation, LIST_MEMBERS.description),
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
