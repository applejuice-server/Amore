package space.minota.amore.features

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import kotlin.math.floor

class TabHealthFeature(plugin: Plugin) : BukkitRunnable() {

    private var tab: Objective
    private var name: Objective

    init {
        val manager = Bukkit.getScoreboardManager()
        val board: Scoreboard = manager.mainScoreboard
        name = if (board.getObjective("HealthNamePL") == null) {
            board.registerNewObjective("HealthNamePL", "dummy")
        } else {
            board.getObjective("HealthNamePL")
        }
        tab = if (board.getObjective("HealthTabPL") == null) {
            board.registerNewObjective("HealthTabPL", "dummy")
        } else {
            board.getObjective("HealthTabPL")
        }
        name.displaySlot = DisplaySlot.BELOW_NAME
        name.displayName = "${ChatColor.DARK_RED}❤"
        tab.displaySlot = DisplaySlot.PLAYER_LIST
        runTaskTimer(plugin, 1L, 1L)
    }

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val health = floor(player.health / 20 * 100 + (player as CraftPlayer).handle.absorptionHearts).toInt()
            name.getScore(player.getName()).score = health
            tab.getScore(player.getName()).score = health
        }
    }


}