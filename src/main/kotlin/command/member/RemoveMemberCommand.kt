package me.trup10ka.puby.command.member

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.user
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedSuccess

class RemoveMemberCommand(
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
            user("discord_tag", "The member to remove") { required = true }
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers["id"]!!.toInt(), responseBehavior) ?: return

        if (!isCallerMemberOfEvent(event, interaction.user.tag, responseBehavior)) return

        removeMemberFromEvent(
            event,
            interaction.command.users["discord_tag"]!!.tag to interaction.command.users["discord_tag"]!!.id,
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
