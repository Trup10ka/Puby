package me.trup10ka.puby.command.event

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.string
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.trup10ka.puby.command.EventUtilizerCommand
import me.trup10ka.puby.event.PubyEventManager
import me.trup10ka.puby.util.DeferredResponseBehavior
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_ID
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_ALTER_PARAM
import me.trup10ka.puby.command.PubyCommandArguments.EVENT_PARAM_NEW_VALUE
import me.trup10ka.puby.event.PubyEvent
import me.trup10ka.puby.util.respondEmbeddedFail
import me.trup10ka.puby.util.respondEmbeddedSuccess

class AlterEventCommand(
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
            integer(EVENT_ID.argName, EVENT_ID.description) { required = true }
            string(EVENT_ALTER_PARAM.argName, EVENT_ALTER_PARAM.description) {
                required = true
                choice("N", "Name")
                choice("DE", "Description")
                choice("P", "Place")
                choice("DA", "Date")
                choice("T", "Time")
            }
            string(EVENT_PARAM_NEW_VALUE.argName,EVENT_PARAM_NEW_VALUE.description) { required = true }
        }
    }

    override suspend fun handleCommand(responseBehavior: DeferredResponseBehavior, interaction: ChatInputCommandInteraction, pubyEventManager: PubyEventManager)
    {
        val event = getEvent(pubyEventManager, interaction.command.integers["id"]!!.toInt(), responseBehavior) ?: return

        if (updateEventParameter(event, interaction.command.strings))
            responseBehavior.respondEmbeddedSuccess {
                title = "Event \"${event.name}\" - ID: ${event.id} updated"
                description = event.toFancyString()
            }
        else
            responseBehavior.respondEmbeddedFail {
                title = "Invalid event parameter"
                description = "The event parameter provided is invalid"
            }
    }

    private fun updateEventParameter(event: PubyEvent, commandStrings: Map<String, String>): Boolean
    {
        val eventParam = commandStrings["event_param"]!!
        val newValue = commandStrings["new_value"]!!

        when(eventParam)
        {
            "N" -> event.name = newValue
            "DE" -> event.description = newValue
            "P" -> event.place = newValue
            "DA" -> event.date = LocalDate.parse(newValue)
            "T" -> event.time = LocalTime.parse(newValue)
            else -> return false
        }
        return true
    }
}
