package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class HealCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.heal")) {
            sender.sendMessage("§8[§4UHC§8]§7 You do not have permission to use this command.")
            return false;
        }
        if (args.isEmpty()) {
            val player = sender as Player
            player.health = 20.0
            player.sendMessage("§8[§4UHC§8]§7 You have healed yourself..");
            return true;
        } else {
            if (args[0] == "*") {
                for (online in ArrayList(Bukkit.getServer().onlinePlayers)) {
                    online.health = 20.0
                    online.sendMessage("§8[§4UHC§8]§7 You have been healed by §c${sender.name}§7..")
                }
                sender.sendMessage("§8[§4UHC§8]§7 You've healed all players.")
                return true;
            } else {
                val target = Bukkit.getServer().getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage("${ChatColor.RED}That player is not online or has never logged onto the server.")
                }
                target.health = 20.0
                target.sendMessage("§8[§4UHC§8]§7 You've been healed by §c${sender.name}§7.")
                return true;
            }
        }

        return true;
    }

}