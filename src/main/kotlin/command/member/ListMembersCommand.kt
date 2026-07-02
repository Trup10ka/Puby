package me.trup10ka.puby.command.member

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.GlobalMultiApplicationCommandBuilder
import dev.kord.rest.builder.interaction.integer
import io.github.oshai.kotlinlogging.KotlinLogging
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.util.respondEmbeddedSuccess
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_ID

class ListMembersCommand(
    commandName: String,
    commandDescription: String
) : EventUtilizerCommand(commandName, commandDescription)
{

    private val logger = KotlinLogging.logger {}

    override fun register(builder: GlobalMultiApplicationCommandBuilder, kordClient: Kord)
    {
        logger.info { "Initiating command: $commandName" }
        builder.input(
            commandName,
            commandDescription
        ) {
            dmPermission = true

            integer(EVENT_ID.argName, EVENT_ID.description) { required = true }
        }
        logger.info { "Command '$commandName' initiated" }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers[EVENT_ID.argName]!!.toInt(), responseBehavior) ?: return

        logger.info { "Listing members for event: ${event.id}" }
        responseBehavior.respondEmbeddedSuccess {
            title = "Members of \"${event.name}\" - ID: ${event.id}"
            description = event.formatMembersAsDiscordEmbed()
        }
    }
}
