package com.github.eokasta.kotlinplugin.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

object LocationSerializer {

    fun serialize(location: Location) : String {
        return "${location.world.name};${location.x};${location.y};${location.z};${location.yaw};${location.pitch}"
    }

    fun deserializer(string: String) : Location{
        val split = string.split(";")
        val world: World = Bukkit.getWorld(split[0])
        val x = split[1].toDouble()
        val y = split[2].toDouble()
        val z = split[3].toDouble()
        val yaw = split[4].toFloat()
        val pitch = split[5].toFloat()

        return Location(world, x, y, z, yaw, pitch)
    }

}