package space.minota.amore.listeners

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent


class Quit : Listener {

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        e.quitMessage = ChatColor.DARK_GRAY.toString() + "(" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + ") " + ChatColor.RED + player.name
    }

}