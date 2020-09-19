package space.minota.amore.commands.player

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team
import space.minota.amore.Main
import space.minota.amore.Teams
import space.minota.amore.utils.GameState
import space.minota.amore.utils.Settings
import java.util.*


class TeamCommand : CommandExecutor {

    private var invites = HashMap<Player, ArrayList<Player>>()

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {

        if (!sender.hasPermission("uhc.command.team")) {
            if (Main.ffa) {
                sender.sendMessage("${ChatColor.RED}You can't use this command at the moment. (It's an FFA game or Random teams)")
                return false
            } else if (GameState.valueOf(Settings.instance.data!!.getString("game.state")) != GameState.LOBBY) {
                sender.sendMessage("${ChatColor.RED}You can't use this command at the moment.")
                return false
            }
        }

        if (args.isEmpty()) {
            sender.sendMessage(Main.line)
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team create ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Creates a team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team invite <player> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Invites a player to your team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team leave ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Leave your team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team accept <player> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Accept a player's team invite.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team kick <player> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Kick a player from your team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team list ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Brings a list of teams and their members.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/pm <message> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Talk in team chat.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/pmc ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Send your coordinates.")
            sender.sendMessage(Main.line)
            if (sender.hasPermission("uhc.command.team")) {
                sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team reset ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Reset all teams.")
                sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team management <on/off> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Enable/disable team management.")
                sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team size <size> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Set the size of teams.")
                sender.sendMessage(Main.line)
            }
        } else if (args[0] == "create") {
            val player = sender as Player
            if (player.scoreboard.getPlayerTeam(player) != null) {
                player.sendMessage("${ChatColor.RED}You are already on a team.")
                return true
            }
            if (GameState.valueOf(Settings.instance.data!!.getString("game.state")) !== GameState.LOBBY) {
                player.sendMessage("${ChatColor.RED}You can't use this command at the moment.")
                return true
            }
            val oTeams = ArrayList<Team>()

            for (team in Teams.manager.getTeams()) {
                if (team.size == 0) {
                    oTeams.add(team)
                }
            }

            oTeams[Random().nextInt(oTeams.size)].addPlayer(player)
            player.sendMessage("${Main.prefix} Team created! Use ${ChatColor.WHITE}/team invite <player>${ChatColor.GRAY} to invite a player.")
        } else if (args[0] == "invite") {

        }

        return true
    }

}
