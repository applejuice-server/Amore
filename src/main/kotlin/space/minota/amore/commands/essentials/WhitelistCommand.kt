package space.minota.amore.commands.essentials

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import space.minota.amore.Main

class WhitelistCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.whitelist")) {
            sender.sendMessage("${Main.prefix} You do not have permission to use this command.")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage(Main.line)
            sender.sendMessage("${Main.prefix} Invalid usage: ${ChatColor.GREEN}/wl <remove/add> <player>")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/wl <all/clear/off/on>")
            sender.sendMessage(Main.line)
            return false
        }


        return true
    }

}