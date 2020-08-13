package space.minota.amore

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

import space.minota.amore.commands.essentials.*
import space.minota.amore.commands.player.MessageCommand

import space.minota.amore.utils.PlayersUtil
import space.minota.amore.utils.Settings


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
            if (settings.data!!.contains("game.state")) {
                settings.data!!.set("game.state", settings.data!!.getString("game.state"))
            } else {
                settings.data!!.set("game.state", "LOBBY")
            }
        } catch (e: Exception) {
            settings.data!!.set("game.state", "LOBBY")
        }

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