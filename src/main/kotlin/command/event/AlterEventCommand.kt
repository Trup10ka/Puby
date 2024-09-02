package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import me.trup10ka.puby.command.PubyCommand
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior

class AlterEventCommand(
    commandName: String,
    commandDescription: String
) : PubyCommand(commandName, commandDescription)
{
    override suspend fun init(kordClient: Kord)
    {
    }

    override suspend fun handleCommand(
        responseBehavior: DeferredResponseBehavior,
        interaction: ChatInputCommandInteraction,
        pubyEventManager: PubyEventManager
    ) {
    }

}
