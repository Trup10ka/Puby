package me.trup10ka.puby.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import java.io.File
import java.nio.file.Files

class FileConfigLoader(
    private val path: String,
    private val classLoader: ClassLoader
) : ConfigLoader
{
    override fun loadConfig(): Config
    {
        val configFile = File(path)

        copyDefaultsIfNotExists(configFile)

        return ConfigLoaderBuilder
            .default()
            .addFileSource(configFile)
            .build()
            .loadConfigOrThrow<Config>()
    }

    private fun copyDefaultsIfNotExists(file: File)
    {
        if (file.exists()) return

        val defaultConfig = classLoader.getResourceAsStream(path) ?: throw NullPointerException("Default config not found")

        Files.copy(defaultConfig, file.toPath())
    }
}
