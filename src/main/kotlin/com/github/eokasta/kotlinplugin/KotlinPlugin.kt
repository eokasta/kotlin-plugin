package com.github.eokasta.kotlinplugin

import com.github.eokasta.kotlinplugin.commands.WarpCommand
import com.github.eokasta.kotlinplugin.manager.WarpManager
import com.github.eokasta.kotlinplugin.utils.Helper
import me.bristermitten.pdm.PluginDependencyManager
import me.saiintbrisson.bukkit.command.BukkitFrame
import me.saiintbrisson.minecraft.command.message.MessageType
import org.bukkit.plugin.java.JavaPlugin

class KotlinPlugin : JavaPlugin() {

    override fun onEnable() {
        PluginDependencyManager.of(this).downloadAllDependencies().thenRun {
            val warpManager = WarpManager(this)
            warpManager.loadWarps()

            BukkitFrame(this).also {
                it.messageHolder.setMessage(MessageType.INCORRECT_USAGE, Helper.format("&c{usage}"))
                it.registerCommands(
                        WarpCommand(warpManager)
                )
            }

        }
    }

    override fun onDisable() {

    }

}
