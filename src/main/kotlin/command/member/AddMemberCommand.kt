package me.trup10ka.puby.command.member

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.core.entity.interaction.InteractionCommand
import dev.kord.rest.builder.interaction.GlobalMultiApplicationCommandBuilder
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.user
import io.github.oshai.kotlinlogging.KotlinLogging
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.data.PubyEventMember
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedFail
import me.trup10ka.puby.util.respondEmbeddedSuccess
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_ID
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_MEMBER_ID

class AddMemberCommand(
    commandName: String,
    commandDescription: String
) : EventUtilizerCommand(commandName, commandDescription)
{
    private val logger = KotlinLogging.logger {  }

    override fun register(builder: GlobalMultiApplicationCommandBuilder, kordClient: Kord)
    {
        logger.info { "Initializing command: $commandName" }
        builder.input(
            commandName,
            commandDescription
        ) {
            dmPermission = false

            integer(EVENT_ID.argName, EVENT_ID.description) { required = true }
            user(EVENT_MEMBER_ID.argName, EVENT_MEMBER_ID.description) { required = true }
        }
        logger.info { "Command '$commandName' initialized" }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers[EVENT_ID.argName]!!.toInt(), responseBehavior) ?: return

        val member = addMemberToEvent(event, interaction.command)
        respondWhetherMemberAdded(responseBehavior, member)
    }

    private suspend fun respondWhetherMemberAdded(response: DeferredResponseBehavior, member: PubyEventMember?)
    {
        if (member == null)
            response.respondEmbeddedFail { title = "Member already in the event!" }
        else
            response.respondEmbeddedSuccess(member) { title = "Member `${member.discordUsername}` added" }
    }

    private fun addMemberToEvent(event: PubyEvent, command: InteractionCommand): PubyEventMember?
    {
        val user = command.users[EVENT_MEMBER_ID.argName]!!

        val member = PubyEventMember(
            user.tag,
            user.id
        )

        return if (event.addMember(member))
            member
        else null
    }
}
