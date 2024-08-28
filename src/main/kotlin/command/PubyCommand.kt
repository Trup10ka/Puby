package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import me.trup10ka.puby.event.PubyEventManager

abstract class PubyCommand(val commandName: String, val commandDescription: String)
{
    abstract suspend fun init(kordClient: Kord)

    abstract suspend fun handleCommand(responseBehavior: DeferredPublicMessageInteractionResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
}
