package space.minota.amore.commands.player

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team
import space.minota.amore.Main
import space.minota.amore.Teams
import space.minota.amore.utils.GameState
import space.minota.amore.utils.Settings
import sun.audio.AudioPlayer
import java.util.*


class TeamCommand : CommandExecutor {

    private var invites = HashMap<Player, ArrayList<Player>>()
    private val settings: Settings = Settings.instance

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
            if (Main.ffa) {
                player.sendMessage("${ChatColor.RED}You can't use this command at the moment.")
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
            val player = sender as Player
            val oPlayer = player as OfflinePlayer
            val team = player.scoreboard.getPlayerTeam(oPlayer)

            if (team == null) {
                player.sendMessage("${ChatColor.RED}You are not on a team.")
                return true
            }
            if (team.size >= Main.teamSize) {
                player.sendMessage("${ChatColor.RED}Your team has reached the max teamsize.")
                return true
            }
            val target = Bukkit.getServer().getPlayer(args[1])
            if (target == null) {
                player.sendMessage("${ChatColor.RED}That player is not online at the moment.")
                return false
            }
            val oTarget = target as OfflinePlayer
            val team1 = player.scoreboard.getPlayerTeam(oTarget)

            if (team1 != null) {
                player.sendMessage("${ChatColor.RED}That player is already on a team.")
                return true
            }
            if (target == player) {
                player.sendMessage("${ChatColor.RED}You can't send a invite request to yourself.")
                return true
            }

            for (players in team.players) {
                if (players is Player) {
                    players.sendMessage(Main.line)
                    players.sendMessage("${Main.prefix} ${ChatColor.WHITE}${target.name}${ChatColor.GRAY} was invited to your team.")
                    players.sendMessage(Main.line)
                }
            }

            if (!invites.containsKey(player)) invites[player] = ArrayList()
            invites[player]!!.add(target)
            target.sendMessage(Main.line)
            target.sendMessage("${Main.prefix} You have been invited to ${ChatColor.WHITE}${player.name}${ChatColor.GRAY}'s team.")
            target.sendMessage("${Main.prefix} §7To accept, type ${ChatColor.WHITE}/team accept ${player.name}${ChatColor.GRAY}.")
            target.sendMessage(Main.line)
        } else if (args[0] == "size") {
            if (!sender.hasPermission("uhc.command.team")) {
                sender.sendMessage("${ChatColor.RED}You don't have permission to use this command.")
                return false
            }
            if (args[1].isEmpty()) {
                sender.sendMessage("${ChatColor.RED}You need to send a valid teamsize.")
                return false
            }
            if (args[1].toIntOrNull() == null) {
                sender.sendMessage("${ChatColor.RED}You need to send a valid teamsize.")
                return false
            }
            Main.teamSize = args[1].toInt()
            settings.data!!.set("game.teamsize", args[1])
            settings.saveData()
            sender.sendMessage(Main.line)
            sender.sendMessage("${Main.prefix} ${ChatColor.GRAY}The teamsize has been set to ${ChatColor.WHITE}${args[1]}${ChatColor.GRAY}.")
            sender.sendMessage(Main.line)
        } else if (args[0] == "accept") {
            val player = sender as Player
            val target = Bukkit.getServer().getPlayer(args[1])
            val team = target.scoreboard.getPlayerTeam(target)
            if (Main.ffa) {
                player.sendMessage("${ChatColor.RED}This is an FFA game.")
                return true
            }
            if (GameState.currentState != GameState.LOBBY) {
                player.sendMessage("${ChatColor.RED}You cannot do this command at the moment.")
                return true
            }
            if (target == null) {
                sender.sendMessage("${ChatColor.RED}That player is not online.")
                return false
            }
            if (invites.containsKey(target) && invites[target]!!.contains(player)) {
                if (team.size >= Main.teamSize) {
                    player.sendMessage("${ChatColor.RED}That team has reached the max teamsize.")
                    return false
                }

                player.sendMessage("${Main.prefix} Request accepted.")
                team.addPlayer(player)
                for (players in team.players) {
                    if (players is Player) {
                        players.sendMessage(Main.line)
                        players.sendMessage("${Main.prefix} ${ChatColor.WHITE}${player.name}${ChatColor.GRAY} joined your team.")
                        players.sendMessage(Main.line)
                    }
                }

            } else {
                player.sendMessage("${ChatColor.RED}That player has not sent you a team invite.")
                return false
            }
        }

        return true
    }

}
