package space.minota.amore

import org.bukkit.plugin.java.JavaPlugin

import space.minota.amore.commands.essentials.FeedCommand
import space.minota.amore.commands.essentials.HealCommand




class Main : JavaPlugin() {


    // Variable declarations (to use in chat messages)
    //fun prefix(): String { return "§8[§4UHC§8]§7" }
    //fun line(): String { return "§8§m-------------------------------------" }

    // Main funcs (funky)
    override fun onEnable() {
        logger.info("Amore enabled!")

        getCommand("heal").executor = HealCommand()
        getCommand("feed").executor = FeedCommand()
    }

    override fun onDisable() {
        logger.info("Amore disabled!")
    }

}