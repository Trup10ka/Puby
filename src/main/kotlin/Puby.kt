package me.trup10ka.puby

import dev.kord.core.Kord
import me.trup10ka.puby.config.Config
import me.trup10ka.puby.config.FileConfigLoader

class Puby
{
    private lateinit var kordClient: Kord

    private lateinit var config: Config

    private val configProvider: FileConfigLoader = FileConfigLoader("puby.conf", Puby::class.java.classLoader)

    suspend fun init()
    {
        config = configProvider.loadConfig()

        kordClient = Kord(config.token)
    }

    suspend fun start()
    {
        kordClient.login()
    }
}
