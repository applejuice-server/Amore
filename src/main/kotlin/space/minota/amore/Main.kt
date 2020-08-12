package space.minota.amore

import org.bukkit.plugin.java.JavaPlugin
import space.minota.amore.utils.Settings

import space.minota.amore.commands.essentials.ClearInventoryCommand
import space.minota.amore.commands.essentials.ClearPotionEffectsCommand
import space.minota.amore.commands.essentials.FeedCommand
import space.minota.amore.commands.essentials.HealCommand


class Main : JavaPlugin() {

    private val settings: Settings = Settings.instance

    // Variable declarations (to use in chat messages)
    //fun prefix(): String { return "§8[§4UHC§8]§7" }
    //fun line(): String { return "§8§m-------------------------------------" }

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
    }

    override fun onDisable() {
        logger.info("Amore disabled!")
    }

}