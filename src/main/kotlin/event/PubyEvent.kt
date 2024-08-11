package me.trup10ka.puby.event

import me.trup10ka.puby.event.member.PubyEventMember

class PubyEvent(
    val id: Int,
    val name: String,
    val description: String = "No description provided",
    val place: String = "No place provided",
    val members: MutableList<PubyEventMember> = mutableListOf()
)
