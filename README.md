# Puby

Puby is a discord bot made for creating and managing events with your friends.

## Table of Contents


### 1. [Utilization and Focus](#utilization-and-focus)
### 2. [Features](#features)
### 3. [Commands](#commands)

## Utilization and Focus

You plan to go out with your friends, and you want to have it _organized_,
know your _expenses_ and overall info about the event?

You use [**Discord**](https://discord.com/) to communicate with your friends? **Puby** is the bot for you!

For example, when you visit a pub,
and you proceed to pay as one, you then have to calculate the expenses of each person, and it can be a hassle.
**Puby** can help you with that!

The Main focus of Puby is to be straightforward to use mainly for the members of the event,
meaning simple commands and easy to understand.

## Features

1. [ ] Creating a discord chat room for the event.
2. [ ] Option to create a receipt for the expenses of the event.
3. [ ] Reminder for people about the date and time of the event.
4. [ ] Notify people to pay an expense to the person who paeventId (case when one person paeventId).
5. [ ] Notify people to pay an expense to the person who paeventId (case when multiple people paeventId).
6. [ ] Creating a printable list of the expenses.

## Commands

Puby proveventIdes you with a set of commands to manage your events and event members.

Puby uses the latest discord API version for the bot, and all the commands are prefixed with `/`.

For easier usage, the command call names are build with each of the first letters that are in the command description.
For example `/ce` is the command for *C*reate *E*vent.

### Event Commands

| Command name / Description | Command usage |                                   Arguments                                    |         Example         |                                                                               Notes                                                                                |
|:---------------------------|:-------------:|:------------------------------------------------------------------------------:|:-----------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Create event               |      /ce      |        \[ name \] \[ place ] \[ date / time ] \[ members *\*optional* ]        | /ce Dead-Horse Old-Town | *When passing the name argument, name must NOT contain whitespaces* <br/> *When proveventIding place as a name, and not a link to a location, also no whitespaces* |
| Delete event               |      /de      |                                  \[ eventId ]                                  |     /de Dead-Horse      |                                                *When passing the name argument, name must NOT contain whitespaces*                                                 |
| Alter event                |      /ae      | \[ eventId ]  \[ -n *(name)* \| -p *(place)* \| -dt *(date time)* ] \[ value ] | /ae 5126 -n Dead-Moose  |                              *It is possible to alter events: Name, Place, Time/Date <br/> Members are handled by specific commands*                               |
| Add member                 |      /am      |                \[ eventId ] \[ member eventId \| member name ]                 |    /am 5126 someone     |                                                                                 ∅                                                                                  |
| Remove member              |      /rm      |                \[ eventId ] \[ member eventId \| member name ]                 |    /rm 5126 someone     |                                                                                 ∅                                                                                  |
| List members               |      /lm      |                                  \[ eventId ]                                  |        /lm 5126         |                                                                                 ∅                                                                                  |

### Receipt Commands

All commands bellow require the receipt to `EXIST` on the given event.

*Note: This does not apply to the first two commands*

| Command name / Description | Command usage |                   Arguments                   |         Example          |                             Notes                              |
|:---------------------------|:-------------:|:---------------------------------------------:|:------------------------:|:--------------------------------------------------------------:|
| Create receipt             |      /cr      |                 \[ eventId ]                  |         /cr 5126         |                               ∅                                |
| Delete receipt             |      /dr      |                 \[ eventId ]                  |         /dr 5126         | *Once deleted, data stored in the receipt cannot be restored!* |
| Add member account         |     /ama      |       \[ eventId ] \[ account number ]        | /ama 5126 200151432/0856 |                               ∅                                |
| Remove member account      |     /rma      | \[ eventId ]  \[ username \| account number ] | /rma 5126 200151432/0856 |                               ∅                                |
| Sum up receipt             |     /sur      |                 \[ eventId ]                  |        /sur 5126         |                               ∅                                |

## Documentation

For more detailed inseventIde information about the whole project, visit the [Wiki](https://github.com/Trup10ka/Puby/wiki)
