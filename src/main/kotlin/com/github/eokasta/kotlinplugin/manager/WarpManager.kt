package com.github.eokasta.kotlinplugin.manager

import com.github.eokasta.kotlinplugin.KotlinPlugin
import com.github.eokasta.kotlinplugin.utils.LocationSerializer
import com.github.eokasta.kotlinplugin.utils.Settings
import com.github.eokasta.kotlinplugin.utils.YamlConfig
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class WarpManager(plugin: KotlinPlugin) {

    var settings: Settings = Settings(YamlConfig("config.yml", plugin, true))

    val warpMap = mutableMapOf<String, Location>()

    fun loadWarps() {
        val section = this.settings.yamlConfig.config!!.getConfigurationSection("locations") ?: return

        for (key in section.getKeys(false)) {
            val location = LocationSerializer.deserializer(section.getString(key))
            warpMap += key to location
        }
    }

    fun createWarp(id: String, location: Location) {
        warpMap += id.toLowerCase() to location
        settings.yamlConfig.config!!.set("locations.${id.toLowerCase()}", LocationSerializer.serialize(location))
        settings.yamlConfig.save()
    }

    fun deleteWarp(id: String) {
        warpMap -= id.toLowerCase()
        settings.yamlConfig.config!!.set("locations.${id.toLowerCase()}", null)
        settings.yamlConfig.save()
    }

    fun teleport(id: String?, player: Player?) : Boolean {
        val location = warpMap[id!!.toLowerCase()] ?: return false

        return player!!.teleport(location)
    }

}