package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.DeferredPublicMessageInteractionResponseBehavior
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.core.entity.interaction.InteractionCommand
import dev.kord.rest.builder.interaction.boolean
import dev.kord.rest.builder.interaction.string
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.trup10ka.puby.command.PubyCommand
import me.trup10ka.puby.data.PubyEventDTO
import me.trup10ka.puby.data.PubyEventMember
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.respondEmbeddedFail
import me.trup10ka.puby.util.respondEmbeddedSuccess

class CreateEventCommand(
    commandName: String,
    commandDescription: String,

) : PubyCommand(commandName, commandDescription)
{
    private lateinit var kord: Kord

    override suspend fun init(kordClient: Kord)
    {
        kord = kordClient

        kordClient.createGlobalChatInputCommand(
            commandName,
            commandDescription
        ) {
            string("name", "The name of the event") { required = true }
            string("description", "The description of the event")
            string("place", "The place of the event")
            string("date", "The date of the event")
            string("time", "The time of the event")
            boolean("receipt", "Whether the event has a receipt")
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredPublicMessageInteractionResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val eventId = pubyEventManager.createEvent(assembleEventDTO(interaction))

        if (!hasEventBeenCreated(eventId, responseBehavior))
            return

        val event = pubyEventManager.pubyEvents.find { it.id == eventId }!!
        respondWithEventCreated(event, responseBehavior)
    }

    private suspend fun respondWithEventCreated(event: PubyEvent, response: DeferredPublicMessageInteractionResponseBehavior)
    {
        response.respondEmbeddedSuccess {
            title = "Event created"
            this.description = event.toFancyString()
            footer { text = "Event ID: ${event.id}" }
        }
    }

    private suspend fun hasEventBeenCreated(eventCreationResult: Int, response: DeferredPublicMessageInteractionResponseBehavior): Boolean
    {
        if (eventCreationResult >= PubyEventManager.LOWER_BOND_OF_ID && eventCreationResult <= PubyEventManager.UPPER_BOND_OF_ID)
            return true

        when (eventCreationResult)
        {
            -2 -> response.respondEmbeddedFail { title = "`Max` number of events reached, cannot create any event now, wait for someone to finish theirs!" }
            else -> response.respondEmbeddedFail { title = "An `unknown` error occurred while creating the event" }
        }
        return false
    }

    private fun assembleEventDTO(interaction: ChatInputCommandInteraction): PubyEventDTO
    {
        val name = interaction.command.strings["name"]!!
        val description = interaction.command.strings["description"]
        val place = interaction.command.strings["place"]
        val date = interaction.command.strings["date"]?.let { LocalDate.parse(it) }
        val time = interaction.command.strings["time"]?.let { LocalTime.parse(it) }
        val receipt = interaction.command.booleans["receipt"] == true
        val creator = PubyEventMember(interaction.user.tag, interaction.user.id)

        return PubyEventDTO(
            name = name,
            description = description,
            place = place,
            date = date,
            time = time,
            receipt = receipt,
            creator = creator
        )
    }
}
