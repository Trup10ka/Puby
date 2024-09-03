package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import me.trup10ka.puby.util.DeferredResponseBehavior
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
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
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_DATE
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_DESCRIPTION
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_NAME
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_PLACE
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_RECEIPT
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_TIME

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
            string(EVENT_NAME.argName, EVENT_NAME.description) { required = true }
            string(EVENT_DESCRIPTION.argName, EVENT_DESCRIPTION.description)
            string(EVENT_PLACE.argName, EVENT_PLACE.description)
            string(EVENT_DATE.argName, EVENT_DATE.description)
            string(EVENT_TIME.argName, EVENT_TIME.description)
            boolean(EVENT_RECEIPT.argName, EVENT_RECEIPT.description)
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val eventId = pubyEventManager.createEvent(assembleEventDTO(interaction))

        if (!hasEventBeenCreated(eventId, responseBehavior))
            return

        val event = pubyEventManager.pubyEvents.find { it.id == eventId }!!
        respondWithEventCreated(event, responseBehavior)
    }

    private suspend fun respondWithEventCreated(event: PubyEvent, response: DeferredResponseBehavior)
    {
        response.respondEmbeddedSuccess {
            title = "Event created"
            this.description = event.toFancyString()
            footer { text = "Event ID: ${event.id}" }
        }
    }

    private suspend fun hasEventBeenCreated(eventCreationResult: Int, response: DeferredResponseBehavior): Boolean
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
