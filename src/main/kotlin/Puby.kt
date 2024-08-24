package me.trup10ka.puby

import dev.kord.core.Kord
import me.trup10ka.puby.command.PubyCommandManager
import me.trup10ka.puby.config.Config
import me.trup10ka.puby.config.FileConfigLoader
import me.trup10ka.puby.event.PubyEventManager

class Puby
{
    private lateinit var kordClient: Kord

    private lateinit var config: Config

    private lateinit var pubyCommandManager: PubyCommandManager

    private val configProvider: FileConfigLoader = FileConfigLoader("puby.conf", Puby::class.java.classLoader)

    private val pubyEventManager = PubyEventManager()


    suspend fun init()
    {
        initKordClient()

        pubyCommandManager = PubyCommandManager(kordClient, pubyEventManager)

        initChatCommands()
    }

    suspend fun start()
    {
        kordClient.login()
    }

    private suspend fun initKordClient()
    {
        config = configProvider.loadConfig()

        kordClient = Kord(config.token)
    }

    private suspend fun initChatCommands()
    {
        pubyCommandManager.initCommands()
        pubyCommandManager.registerListeners()
    }
}
