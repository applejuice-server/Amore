package space.minota.amore

import org.bukkit.Bukkit
import org.bukkit.inventory.Recipe
import org.bukkit.plugin.java.JavaPlugin
import space.minota.amore.commands.essentials.*
import space.minota.amore.commands.player.HealthCommand
import space.minota.amore.commands.player.MessageCommand
import space.minota.amore.commands.player.PMCommand
import space.minota.amore.commands.player.TeamCommand
import space.minota.amore.features.AntiNotchApples
import space.minota.amore.features.GoldenHeads
import space.minota.amore.features.TabHealthFeature
import space.minota.amore.listeners.*
import space.minota.amore.utils.GameState
import space.minota.amore.utils.Settings


class Main : JavaPlugin() {

    private val settings: Settings = Settings.instance

    // Variable declarations (to use in chat messages)
    companion object {
        const val prefix = "§8[§4UHC§8]§7"
        const val dash = "§8»"
        const val line = "§8§m-------------------------------------"
        var plugin: Main? = null
        var absorption = false
        var goldenheads = false
        var notchapples = false
        var ffa = false
        var teamSize = 0
        var res: Recipe? = null
    }

    // Main funcs (funky)
    override fun onEnable() {

        settings.setup(this)

        if (settings.data!!.contains("game.state")) {
            GameState.setState(GameState.valueOf(settings.data!!.getString("game.state")))
        } else {
            GameState.setState(GameState.LOBBY)
        }
        if (!settings.data!!.contains("game.ffa")) {
            settings.data!!.set("game.ffa", true)
        }
        if (!settings.data!!.contains("game.teamsize")) {
            settings.data!!.set("game.teamsize", 1)
        }
        if (!settings.data!!.contains("game.options.absorption")) {
            settings.data!!.set("game.options.absorption", true)
        }
        if (!settings.data!!.contains("game.options.notchapples")) {
            settings.data!!.set("game.options.notchapples", true)
        }
        if (!settings.data!!.contains("game.options.goldenheads")) {
            settings.data!!.set("game.options.goldenheads", true)
        }

        settings.saveData()
        absorption = settings.data!!.getBoolean("game.options.absorption")
        notchapples = settings.data!!.getBoolean("game.options.notches")
        goldenheads = settings.data!!.getBoolean("game.options.goldenheads")
        ffa = settings.data!!.getBoolean("game.ffa")
        teamSize = settings.data!!.getInt("game.teamsize")




        logger.info("Amore enabled!")

        registerCommands()
        registerFeatures()
        registerListeners()

    }

    private fun registerCommands() {
        getCommand("heal").executor = HealCommand()
        getCommand("feed").executor = FeedCommand()
        getCommand("ci").executor = ClearInventoryCommand()
        getCommand("cleareffects").executor = ClearPotionEffectsCommand()
        getCommand("fly").executor = FlyCommand()
        getCommand("gm").executor = GamemodeCommand()
        getCommand("gamemode").executor = GamemodeCommand()
        getCommand("gma").executor = GamemodeCommand()
        getCommand("gms").executor = GamemodeCommand()
        getCommand("team").executor = TeamCommand()
        getCommand("gmsp").executor = GamemodeCommand()
        getCommand("gmc").executor = GamemodeCommand()
        getCommand("msg").executor = MessageCommand()
        getCommand("health").executor = HealthCommand()
        getCommand("whitelist").executor = WhitelistCommand()
        getCommand("pm").executor = PMCommand()
        getCommand("pregen").executor = PMCommand()
    }

    private fun registerListeners() {
        Bukkit.getServer().pluginManager.registerEvents(Players(), this)
        Bukkit.getServer().pluginManager.registerEvents(Chat(), this)
        Bukkit.getServer().pluginManager.registerEvents(Join(), this)
        Bukkit.getServer().pluginManager.registerEvents(World(), this)
        Bukkit.getServer().pluginManager.registerEvents(Quit(), this)
    }

    private fun registerFeatures() {
        TabHealthFeature(this)
        if (goldenheads) {
            GoldenHeads()
        }
        if (!notchapples) {
            AntiNotchApples()
        }
        Teams.manager.setupTeams()
    }

    override fun onDisable() {
        logger.info("Amore disabled!")
        settings.data!!.set("game.state", GameState.valueOf(settings.data!!.getString("game.state")))
        settings.data!!.set("game.teamsize", settings.data!!.getInt("game.teamsize"))
        settings.data!!.set("game.options.absorption", settings.data!!.getBoolean("game.options.absorption"))
        settings.data!!.set("game.options.notchapples", settings.data!!.getBoolean("game.options.notchapples"))
        settings.data!!.set("game.options.goldenheads", settings.data!!.getBoolean("game.options.goldenheads"))


        settings.saveData()
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