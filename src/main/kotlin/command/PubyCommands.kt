package me.trup10ka.puby.command

enum class PubyCommands(val abbreviation: String, val description: String)
{
    CREATE_EVENT("ce", "Create a new event"),
    DELETE_EVENT("de", "Delete an event"),
    ALTER_EVENT("ae", "Alter an event"),
    ADD_MEMBER("am", "Add a member to an event"),
    REMOVE_MEMBER("rm", "Remove a member from an event"),
    LIST_MEMBERS("lm", "List members of an event"),
    SHOW_EVENT("se", "Show an event details")
}
