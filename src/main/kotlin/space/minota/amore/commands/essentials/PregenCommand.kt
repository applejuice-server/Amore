package space.minota.amore.commands.essentials

import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import space.minota.amore.Main
import com.wimbli.WorldBorder.Config
import space.minota.amore.utils.ActionBar
import space.minota.amore.utils.PlayersUtil

class PregenCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String?, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.pregen")) {
            sender.sendMessage("${ChatColor.RED}You don't have permission to use this command.")
            return false
        }

        if (args.isEmpty() || args[1].isEmpty()) {
            sender.sendMessage("${Main.prefix} Invalid usage: ${ChatColor.GREEN}/pregen <world> <border>")
            return false
        }


        val wc = WorldCreator(args[0])
        wc.environment(World.Environment.NORMAL)
        wc.type(WorldType.NORMAL)
        wc.generateStructures(true)
        val world = Bukkit.createWorld(wc)

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "wb ${world.name} clear"
        )
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "wb shape rectangular"
        )
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "wb ${world.name} setcorners ${args[1]} ${args[1]} -${args[1]} -${args[1]}"
        )
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "wb ${world.name} fill 75"
        )
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "wb fill confirm"
        )
        Bukkit.broadcastMessage("${Main.prefix} Pregeneration started in ${ChatColor.WHITE}${args[0]}${ChatColor.GRAY}.")

        runActionBar()


        return true
    }

    private fun runActionBar() {
        Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Main::class.java) /* ? */, Runnable breakout@{
            if (Config.fillTask == null) {
                Bukkit.broadcastMessage("${Main.prefix} Pregeneration is now finished.")
                return@breakout
            } else {
                val players = PlayersUtil.getPlayers()
                for (player in players) {
                    ActionBar.sendActionBarMessage(player, "${Main.prefix} Progress: ${ChatColor.GREEN}${Config.fillTask.percentageCompleted} ${Main.dash} ${ChatColor.GRAY}World: ${ChatColor.GREEN}${Config.fillTask.refWorld()}")
                }
            }
        }, 0, 20)
    }

}