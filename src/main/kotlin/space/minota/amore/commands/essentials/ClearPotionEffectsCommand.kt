package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import space.minota.amore.Main
import java.util.ArrayList

class ClearPotionEffectsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.cleareffects")) {
            sender.sendMessage("${Main.prefix} You do not have permission to use this command.")
            return false;
        }
        if (args.isEmpty()) {
            val player = sender as Player
            val effects = player.activePotionEffects
            for (effect in effects) {
                player.removePotionEffect(effect.type)
            }
            sender.sendMessage("${Main.prefix} Your effects have been cleared.")
            return true;
        } else {
            if (args[0] == "*") {
                for (online in ArrayList(Bukkit.getServer().onlinePlayers)) {
                    val effects = online.activePotionEffects
                    for (effect in effects) {
                        online.removePotionEffect(effect.type)
                    }
                    online.sendMessage("${Main.prefix} Your effects have been cleared by §c${sender.name}§7.")
                }
                sender.sendMessage("${Main.prefix} Cleared the effects of all players.")
                return true;
            } else {
                val target = Bukkit.getServer().getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage("${ChatColor.RED}That player is not online or has never logged onto the server.")
                }
                val effects = target.activePotionEffects
                for (effect in effects) {
                    target.removePotionEffect(effect.type)
                }
                sender.sendMessage("${Main.prefix} Cleared the effects of §c${target.name}§7.")
                target.sendMessage("${Main.prefix} Your effects have been cleared by §c${sender.name}§7")
                return true;
            }
        }
    }
}