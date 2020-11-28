package com.github.eokasta.kotlinplugin.commands

import com.github.eokasta.kotlinplugin.manager.WarpManager
import com.github.eokasta.kotlinplugin.utils.Helper
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.annotation.Optional
import me.saiintbrisson.minecraft.command.command.Context
import me.saiintbrisson.minecraft.command.target.CommandTarget
import org.bukkit.entity.Player

class WarpCommand(private val manager: WarpManager) {

    @Command(name = "warp", target = CommandTarget.PLAYER)
    fun warpCommand(context: Context<Player>, @Optional warpId: String?) {
        val warpMap = manager.warpMap

        if (warpId == null) {
            context.sendMessage(" ")
            context.sendMessage(Helper.format("&6&lWARPS DISPONÍVEIS:"))
            context.sendMessage(" ")
            context.sendMessage(Helper.format(" &a" + if (warpMap.isEmpty()) "&cNenhuma warp foi definida." else warpMap.keys.joinToString(separator = ", ")))
            context.sendMessage(" ")

            return
        }

        if (manager.teleport(warpId, context.sender)) {
            context.sendMessage(Helper.format("&eVocê foi teletransportado para &f$warpId"))
        } else {
            context.sendMessage(Helper.format("&cEsta warp não está definida;"))
        }
    }

    @Command(name = "setwarp", target = CommandTarget.PLAYER, usage = "/setwarp <warp>")
    fun setwarpCommand(context: Context<Player>, warpId: String) {
        manager.createWarp(warpId, context.sender.location)
        context.sendMessage(Helper.format("&aWarp &f$warpId&a definida!"))
    }

    @Command(name = "delwarp", usage = "/delwarp <warp>")
    fun delwarpCommand(context: Context<Player>, warpId: String) {
        val warpMap = manager.warpMap
        if (!warpMap.containsKey(warpId.toLowerCase())) {
            context.sendMessage(Helper.format("&cWarp &f$warpId&c não existe."))
            return
        }

        manager.deleteWarp(warpId)
        context.sendMessage(Helper.format("&cWarp &f$warpId&c removida!"))
    }

}