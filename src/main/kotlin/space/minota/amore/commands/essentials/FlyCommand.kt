package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList

class FlyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.fly")) {
            sender.sendMessage("§8[§4UHC§8]§7 You do not have permission to use this command.")
            return false;
        }
        if (args.isEmpty()) {
            val player = sender as Player
            if (player.allowFlight == false) {
                player.allowFlight = true
                player.isFlying = true
                player.sendMessage("§8[§4UHC§8]§7 You have enabled flight for yourself.");
            } else {
                player.isFlying = false
                player.allowFlight = false
                player.sendMessage("§8[§4UHC§8]§7 You have disabled flight for yourself.");
            }

            return true;
        } else {
            val target = Bukkit.getServer().getPlayer(args[0])
            if (target == null) {
                sender.sendMessage("${ChatColor.RED}That player is not online or has never logged onto the server.")
            }
            if (!target.allowFlight) {
                target.allowFlight = true
                target.isFlying = true
                target.sendMessage("§8[§4UHC§8]§7 Your flight has been enabled by §c${sender.name}§7.");
                sender.sendMessage("§8[§4UHC§8]§7 Enabled §c${target.name}'s§7 flight.")
            } else {
                target.allowFlight = false
                target.isFlying = false
                target.sendMessage("§8[§4UHC§8]§7 Your flight has been disabled by §c${sender.name}§7.");
                sender.sendMessage("§8[§4UHC§8]§7 Disabled §c${target.name}'s§7 flight.")
            }
            return true;
        }
    }
}