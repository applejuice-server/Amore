package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class FeedCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.feed")) {
            sender.sendMessage("§8[§4UHC§8]§7 You do not have permission to use this command.")
            return false;
        }
        if (args.isEmpty()) {
            val player = sender as Player
            player.foodLevel = 20
            player.sendMessage("§8[§4UHC§8]§7 You have fed yourself.");
            return true;
        } else {
            if (args[0] == "*") {
                for (online in ArrayList(Bukkit.getServer().onlinePlayers)) {
                    online.foodLevel = 20
                    online.sendMessage("§8[§4UHC§8]§7 You have been fed by §c${sender.name}§7.")
                }
                sender.sendMessage("§8[§4UHC§8]§7 You've fed all players.")
                return true;
            } else {
                val target = Bukkit.getServer().getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage("${ChatColor.RED}That player is not online or has never logged onto the server.")
                }
                target.foodLevel = 20
                target.sendMessage("§8[§4UHC§8]§7 You've been fed by §c${sender.name}§7.")
                sender.sendMessage("§8[§4UHC§8]§7 Fed §c${target.name}§7.")
                return true;
            }
        }
    }

}