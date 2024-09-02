package me.trup10ka.puby.command.member

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.core.entity.interaction.InteractionCommand
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.user
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.data.PubyEventMember
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedFail
import me.trup10ka.puby.util.respondEmbeddedSuccess

class AddMemberCommand(
    commandName: String,
    commandDescription: String
) : EventUtilizerCommand(commandName, commandDescription)
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

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers["id"]!!.toInt(), responseBehavior) ?: return

        val member = addMemberToEvent(event, interaction.command)
        respondWhetherMemberAdded(responseBehavior, member)
    }

    private suspend fun respondWhetherMemberAdded(response: DeferredResponseBehavior, member: PubyEventMember?)
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
