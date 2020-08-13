package space.minota.amore.commands.player

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import space.minota.amore.Main


class MessageCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("${Main.prefix} You need a user to send a message to.")
            return false
        }
        if (args.size < 2) {
            sender.sendMessage("${Main.prefix} You need a message to send to the user.")
            return false
        }

        var message = ""
        for (i in 1 until args.size) message += args[i] + " "
        val target = Bukkit.getPlayer(args[0])
        if (target == null) {
            sender.sendMessage("${Main.prefix} You need a valid user to send this to.")
            return false
        }
        val player = sender as Player
        sender.sendMessage("${ChatColor.GRAY}To: ${ChatColor.WHITE}${target.displayName} ${ChatColor.DARK_GRAY}- ${ChatColor.GRAY} $message")
        target.sendMessage("${ChatColor.GRAY}From: ${ChatColor.WHITE}${player.displayName} ${ChatColor.DARK_GRAY}- ${ChatColor.GRAY} $message")

        return true
    }

}