package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.InteractionCommand
import me.trup10ka.puby.event.PubyEventManager

class ListMembersCommand(
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

        }
    }

    override suspend fun handleCommand(
        responseBehavior: DeferredPublicMessageInteractionResponseBehavior,
        command: InteractionCommand,
        pubyEventManager: PubyEventManager
    ) {
        responseBehavior.respond { content = "List members command" }
    }
}
