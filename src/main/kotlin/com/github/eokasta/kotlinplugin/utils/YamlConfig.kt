package com.github.eokasta.kotlinplugin.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class YamlConfig(private val fileName: String,
                 private val javaPlugin: JavaPlugin,
                 private val saveDefault: Boolean
) {

    private var logger: Logger = javaPlugin.logger
    private var file: File
    var config: FileConfiguration? = null

    init {
        if (!javaPlugin.dataFolder.exists()) {
            javaPlugin.dataFolder.mkdir()
        }

        file = File(javaPlugin.dataFolder, fileName)

        if (saveDefault) {
            logger.info("Trying to save file from resources.")
            javaPlugin.saveResource(fileName, false)
        }

        if (!file.exists()) {
            file.parentFile.mkdirs()

            file.createNewFile()
            config = YamlConfiguration.loadConfiguration(file)
            logger.info("$fileName was created.")
        }

        config = YamlConfiguration.loadConfiguration(file)
    }

    fun save() {
        config?.save(file)
    }

    fun reload() {
        config = YamlConfiguration.loadConfiguration(file)
        logger.info("$fileName was reloaded.")
    }

    fun delete() : Boolean {
        config = null
        return file.delete()
    }

}