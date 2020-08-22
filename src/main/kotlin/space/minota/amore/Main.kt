package space.minota.amore

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import space.minota.amore.commands.essentials.*
import space.minota.amore.commands.player.MessageCommand
import space.minota.amore.utils.Settings
import kotlin.math.floor


class Main : JavaPlugin() {

    private val settings: Settings = Settings.instance

    // Variable declarations (to use in chat messages)
    companion object {
        const val prefix = "§8[§4UHC§8]§7"
        const val line = "§8§m-------------------------------------"
    }

    // Main funcs (funky)
    override fun onEnable() {
        settings.setup(this)

        try {
            if (settings.data?.contains("game.state")!!) {
                settings.data?.set("game.state", settings.data?.getString("game.state"))
            } else {
                settings.data?.set("game.state", "LOBBY")
            }
        } catch (e: Exception) {
            settings.data?.set("game.state", "LOBBY")
        }



        val manager = Bukkit.getScoreboardManager()
        val board: Scoreboard = manager.mainScoreboard
        var tab: Objective
        var name: Objective
        name = if (board.getObjective("HealthNamePL") == null) {
            board.registerNewObjective("HealthNamePL", "dummy")
        } else {
            board.getObjective("HealthNamePL")
        }
        if (board.getObjective("HealthTabPL") == null) {
            tab = board.registerNewObjective("HealthTabPL", "dummy");
        } else {
            tab = board.getObjective("HealthTabPL");
        }

        name.displaySlot = DisplaySlot.BELOW_NAME;
        name.displayName = "${ChatColor.DARK_RED}❤";

        tab.displaySlot = DisplaySlot.PLAYER_LIST

        Bukkit.getScheduler().runTaskTimer(this, {
            for (player in Bukkit.getOnlinePlayers()) {
                val health = floor(player.health / 20 * 100 + (player as CraftPlayer).handle.absorptionHearts).toInt()
                name.getScore(player.getName()).score = health
                tab.getScore(player.getName()).score = health
            }
        }, 1L, 1L)

        logger.info("Amore enabled!")

        getCommand("heal").executor = HealCommand()
        getCommand("feed").executor = FeedCommand()
        getCommand("ci").executor = ClearInventoryCommand()
        getCommand("cleareffects").executor = ClearPotionEffectsCommand()
        getCommand("fly").executor = FlyCommand()
        getCommand("gm").executor = GamemodeCommand()
        getCommand("gamemode").executor = GamemodeCommand()
        getCommand("gma").executor = GamemodeCommand()
        getCommand("gms").executor = GamemodeCommand()
        getCommand("gmsp").executor = GamemodeCommand()
        getCommand("gmc").executor = GamemodeCommand()
        getCommand("msg").executor = MessageCommand()
    }

    override fun onDisable() {
        logger.info("Amore disabled!")
    }

    /*

    fun start() {
        Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(this /* ? */, Runnable breakout@{
            if (currentTimer == 0) {
                cancel()
                // code relating to starting the game
                return@breakout
            }

            currentTimer -= 1
            // code relating to updating the timer visually (actionbar, scoreboard, whatever)
        }, 0, 20)
    }

    fun host(): Player? {
        return Bukkit.getPlayer(Settings.instance.data?.getString("game.host"))
            ?: return PlayersUtil.getPlayers()[0]
    }


     */

}