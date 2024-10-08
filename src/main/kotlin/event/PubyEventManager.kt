package me.trup10ka.puby.event

import me.trup10ka.puby.data.PubyEventDTO
import me.trup10ka.puby.receipt.Receipt
import me.trup10ka.puby.util.EventCreationStatusCode.FAIL_MAX_EVENTS_REACHED


class PubyEventManager
{
    val pubyEvents: MutableList<PubyEvent> = mutableListOf()

    fun createEvent(pubyEventDTO: PubyEventDTO): Int
    {
        if (pubyEvents.size + 1 > MAX_NUMBER_OF_EVENTS) return FAIL_MAX_EVENTS_REACHED.code

        val pubyEvent = PubyEvent(
            id = generateId(),
            name = pubyEventDTO.name,
            description = pubyEventDTO.description,
            place = pubyEventDTO.place,
            date = pubyEventDTO.date,
            time = pubyEventDTO.time,
            receipt = if (pubyEventDTO.receipt) Receipt() else null,
            creator = pubyEventDTO.creator
        )

        pubyEvents.add(pubyEvent)

        return pubyEvent.id
    }

    fun deleteEvent(id: Int) = pubyEvents.removeIf { it.id == id }

    private fun generateId(): Int
    {
        var randomNumber = (1000..10000).random()

        while (pubyEvents.any { it.id == randomNumber } )
            randomNumber = (LOWER_BOND_OF_ID..UPPER_BOND_OF_ID).random()

        return randomNumber
    }

    companion object
    {
        const val MAX_NUMBER_OF_EVENTS = 1000
        const val LOWER_BOND_OF_ID = 1000
        const val UPPER_BOND_OF_ID = 10000
    }
}
