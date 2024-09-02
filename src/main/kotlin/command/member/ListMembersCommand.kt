package me.trup10ka.puby.command.member

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.integer
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.command.PubyCommand
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.respondEmbeddedSuccess

class ListMembersCommand(
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
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredPublicMessageInteractionResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers["id"]!!.toInt(), responseBehavior) ?: return

        responseBehavior.respondEmbeddedSuccess {
            title = "Members of \"${event.name}\" - ID: ${event.id}"
            description = event.formatMembersAsDiscordEmbed()
        }
    }
}
