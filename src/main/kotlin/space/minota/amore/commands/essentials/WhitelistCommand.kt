package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import space.minota.amore.Main
import space.minota.amore.utils.PlayersUtil


class WhitelistCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.whitelist")) {
            sender.sendMessage("${Main.prefix} You do not have permission to use this command.")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage(Main.line)
            sender.sendMessage("${Main.prefix} Invalid usage: ${ChatColor.GREEN}/wl <remove/add> <player>")
            sender.sendMessage("${Main.prefix} Invalid usage: ${ChatColor.GREEN}/wl <all/clear/off/on>")
            sender.sendMessage(Main.line)
            return false
        }

        when {
            args[0] == "add" -> {
                if (args[1].isEmpty()) {
                    sender.sendMessage("${ChatColor.RED} Usage: /wl add <player>")
                    return false
                }
                val target = Bukkit.getServer().getPlayer(args[1])
                val offline = Bukkit.getServer().getOfflinePlayer(args[1])
                if (target == null) {
                    for (online in PlayersUtil.getPlayers()) {
                        online.sendMessage("${Main.prefix} ${ChatColor.WHITE}${target?.name}${ChatColor.GRAY} has been added to the whitelist.")
                    }
                    offline.isWhitelisted = true
                    return true
                }
                for (online in PlayersUtil.getPlayers()) {
                    online.sendMessage("${Main.prefix} ${ChatColor.WHITE}${target.name}${ChatColor.GRAY} has been added to the whitelist.")
                }
                target.isWhitelisted = true
            }
            args[0] == "remove" -> {
                if (args[1].isEmpty()) {
                    sender.sendMessage("${ChatColor.RED} Usage: /wl remove <player>")
                    return false
                }
                val target = Bukkit.getServer().getPlayer(args[1])
                val offline = Bukkit.getServer().getOfflinePlayer(args[1])
                if (target == null) {
                    for (online in PlayersUtil.getPlayers()) {
                        online.sendMessage("${Main.prefix} ${ChatColor.WHITE}${offline.name.toString()}${ChatColor.GRAY} has been removed from the whitelist.")
                    }
                    offline.isWhitelisted = false
                    return true
                }
                for (online in PlayersUtil.getPlayers()) {
                    online.sendMessage("${Main.prefix} ${ChatColor.WHITE}${offline.name.toString()}${ChatColor.GRAY} has been removed from the whitelist.")
                }
                target.isWhitelisted = false
            }
            args[0] == "clear" -> {
                for (whitelisted in Bukkit.getWhitelistedPlayers()) {
                    whitelisted.isWhitelisted = false
                }
                for (online in PlayersUtil.getPlayers()) {
                    online.sendMessage("${Main.prefix} Whitelist cleared.")
                }
            }
            args[0] == "all" -> {
                for (online in PlayersUtil.getPlayers()) {
                    online.isWhitelisted = true
                    online.sendMessage("${Main.prefix} All players whitelisted.")
                }
            }
            args[0] == "off" -> {
                Bukkit.setWhitelist(false)
                for (online in PlayersUtil.getPlayers()) {
                    online.sendMessage("${Main.prefix} The whitelist is now ${ChatColor.RED}disabled${ChatColor.WHITE}.")
                }
            }
            args[0] == "on" -> {
                Bukkit.setWhitelist(true)
                for (online in PlayersUtil.getPlayers()) {
                    online.sendMessage("${Main.prefix} The whitelist is now ${ChatColor.GREEN}enabled${ChatColor.WHITE}.")
                }
            }
        }


        return true
    }

}