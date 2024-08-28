package me.trup10ka.puby.command.member

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.InteractionCommand
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.user
import me.trup10ka.puby.command.PubyCommand
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.event.PubyEventMember
import me.trup10ka.puby.util.respondEmbeddedFail
import me.trup10ka.puby.util.respondEmbeddedSuccess

class AddMemberCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    override suspend fun init(kordClient: Kord)
    {
        kordClient.createGlobalChatInputCommand(
            commandName,
            commandDescription
        ) {
            integer("id", "The id of the event") { required = true }
            user("discord_tag", "The discord tag of the member to add") { required = true }
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredPublicMessageInteractionResponseBehavior, command: InteractionCommand, pubyEventManager: PubyEventManager)
    {
        val event = pubyEventManager.pubyEvents.find { it.id == command.integers["id"]!!.toInt() }

        if (event == null)
        {
            responseBehavior.respondEmbeddedFail { title = "Event not found" }
            return
        }

        val member = addMemberToEvent(event, command)
        respondWhetherMemberAdded(responseBehavior, member)
    }

    private suspend fun respondWhetherMemberAdded(response: DeferredPublicMessageInteractionResponseBehavior, member: PubyEventMember?)
    {
        if (member == null)
            response.respondEmbeddedFail { title = "Member already in the event!" }
        else
            response.respondEmbeddedSuccess(member) { title = "Member `${member.discordId}` added" }
    }

    private fun addMemberToEvent(event: PubyEvent, command: InteractionCommand): PubyEventMember?
    {
        val user = command.users["discord_tag"]!!

        val member = PubyEventMember(
            user.tag,
            user.id
        )

        return if (event.addMember(member))
            member
        else null
    }
}
