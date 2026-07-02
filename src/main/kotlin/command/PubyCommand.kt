package me.trup10ka.puby.command

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.GlobalMultiApplicationCommandBuilder
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior

abstract class PubyCommand(val commandName: String, val commandDescription: String)
{
    abstract fun register(builder: GlobalMultiApplicationCommandBuilder, kordClient: Kord)

    abstract suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
}
