package space.minota.amore.commands.essentials

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ClearPotionEffectsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.cleareffects")) {
            sender.sendMessage("§8[§4UHC§8]§7 You do not have permission to use this command.")
            return false;
        }

        return true;
    }
}