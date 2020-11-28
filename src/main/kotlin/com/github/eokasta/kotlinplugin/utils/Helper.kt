package com.github.eokasta.kotlinplugin.utils

import org.bukkit.ChatColor

object Helper {

    fun format(string: String) : String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

}