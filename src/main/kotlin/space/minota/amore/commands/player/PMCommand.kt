package space.minota.amore.commands.player

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import space.minota.amore.Main


class PMCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<String>): Boolean {
        val player = sender as Player
        if (player.scoreboard.getPlayerTeam(player) != null) {
            val message = StringBuilder()
            if (args.isEmpty()) {
                player.sendMessage("${ChatColor.RED}Usage: /pm <message>")
                return true
            }
           if (player.scoreboard.getPlayerTeam(player) == null) {
                player.sendMessage("${ChatColor.RED}You must be on a team to send a message.")
                return true
            }
            for (element in args) {
                message.append("${ChatColor.GRAY}${element}").append(" " + ChatColor.GRAY)
            }

            val msg = message.toString().trim()

            for (team in player.scoreboard.getPlayerTeam(player).players) {
                if (team is Player) {
                    team.sendMessage("§[§4Team Chat§8] ${ChatColor.WHITE}${sender} ${Main.dash} ${ChatColor.GRAY}${msg}")
                }
            }
        }
        return true
    }

}