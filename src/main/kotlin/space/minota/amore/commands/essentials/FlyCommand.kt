package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList

import space.minota.amore.Main

class FlyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.fly")) {
            sender.sendMessage("${Main.prefix} You do not have permission to use this command.")
            return false
        }
        if (args.isEmpty()) {
            val player = sender as Player
            return if (!player.allowFlight) {
                player.allowFlight = true
                player.isFlying = true
                player.sendMessage("${Main.prefix} You have enabled flight for yourself.")
                true
            } else {
                player.isFlying = false
                player.allowFlight = false
                player.sendMessage("${Main.prefix} You have disabled flight for yourself.")
                true
            }

        } else {
            val target = Bukkit.getServer().getPlayer(args[0])
            if (target == null) {
                sender.sendMessage("${ChatColor.RED}That player is not online or has never logged onto the server.")
                return false
            }
            return if (!target.allowFlight) {
                target.allowFlight = true
                target.isFlying = true
                target.sendMessage("${Main.prefix} Your flight has been enabled by §c${sender.name}§7.")
                sender.sendMessage("${Main.prefix} Enabled §c${target.name}'s§7 flight.")
                true
            } else {
                target.allowFlight = false
                target.isFlying = false
                target.sendMessage("${Main.prefix} Your flight has been disabled by §c${sender.name}§7.")
                sender.sendMessage("${Main.prefix} Disabled §c${target.name}'s§7 flight.")
                true
            }
        }
    }
}
