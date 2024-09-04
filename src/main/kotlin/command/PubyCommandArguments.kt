package me.trup10ka.puby.command

enum class PubyCommandArguments(val argName: String, val description: String)
{
    EVENT_ID("id", "The ID of the event to alter"),
    EVENT_NAME("name", "The name of the event"),
    EVENT_DESCRIPTION("description", "The description of the event"),
    EVENT_PLACE("place", "The place of the event"),
    EVENT_DATE("date", "The date of the event"),
    EVENT_TIME("time", "The time of the event"),
    EVENT_RECEIPT("receipt", "Whether the event has a receipt"),
    EVENT_MEMBER_ID("discord-id", "The ID of the member to add/remove"),
    EVENT_ALTER_PARAM("event_param", "The event parameter to change"),
    EVENT_PARAM_NEW_VALUE("new_value", "The new value of the event parameter")
}
