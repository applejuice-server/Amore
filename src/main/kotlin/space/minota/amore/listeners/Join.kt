package space.minota.amore.listeners

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


class Join : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        e.joinMessage = ChatColor.DARK_GRAY.toString() + "(" + ChatColor.DARK_GREEN + "+" + ChatColor.DARK_GRAY + ") " + ChatColor.GREEN + player.name
    }

}