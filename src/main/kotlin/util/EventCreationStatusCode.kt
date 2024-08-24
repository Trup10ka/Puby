package me.trup10ka.puby.util

enum class EventCreationStatusCode(val code: Int)
{
    EVENT_CREATED(1),
    FAIL_MAX_EVENTS_REACHED(-2),
}
