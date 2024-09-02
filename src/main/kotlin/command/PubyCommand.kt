package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior

abstract class PubyCommand(val commandName: String, val commandDescription: String)
{
    abstract suspend fun init(kordClient: Kord)

    abstract suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
}
