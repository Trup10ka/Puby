package me.trup10ka.puby.command.member

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.user
import io.github.oshai.kotlinlogging.KotlinLogging
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedSuccess
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_ID
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_MEMBER_ID

class RemoveMemberCommand(
    commandName: String,
    commandDescription: String
) : EventUtilizerCommand(commandName, commandDescription)
{

    private val logger = KotlinLogging.logger { }

    override suspend fun init(kordClient: Kord)
    {
        logger.info { "Initiating command: '$commandName'" }
        kordClient.createGlobalChatInputCommand(
            commandName,
            commandDescription
        ) {
            integer(EVENT_ID.argName, EVENT_ID.description) { required = true }
            user(EVENT_MEMBER_ID.argName, EVENT_MEMBER_ID.description) { required = true }
        }
        logger.info { "Initialized command: '$commandName'" }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers["id"]!!.toInt(), responseBehavior) ?: return

        if (!isCallerMemberOfEvent(event, interaction.user.tag, responseBehavior)) return

        removeMemberFromEvent(
            event,
            interaction.command.users[EVENT_MEMBER_ID.argName]!!.tag to interaction.command.users[EVENT_MEMBER_ID.argName]!!.id,
            responseBehavior)
    }

    suspend fun removeMemberFromEvent(event: PubyEvent, memberId: Pair<String, Snowflake>, responseBehavior: DeferredResponseBehavior)
    {
        if (event.removeMember(memberId.first))
            responseBehavior.respondEmbeddedSuccess(memberId.second) { title = "Member `${memberId.first}` removed" }
        else
            responseBehavior.respondEmbeddedSuccess { title = "Member that you are trying to remove is not registered for this event" }
    }
}
