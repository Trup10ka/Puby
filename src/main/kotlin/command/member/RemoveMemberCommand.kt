package me.trup10ka.puby.command.member

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.entity.interaction.InteractionCommand
import me.trup10ka.puby.command.PubyCommand
import me.trup10ka.puby.event.PubyEventManager

class RemoveMemberCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    override suspend fun init(kordClient: Kord)
    {
    }

    override suspend fun handleCommand(
        responseBehavior: DeferredPublicMessageInteractionResponseBehavior,
        command: InteractionCommand,
        pubyEventManager: PubyEventManager
    ) {

    }
}
