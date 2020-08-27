package space.minota.amore.commands.player

import org.bukkit.Bukkit
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

    var invites = HashMap<Player, ArrayList<Player>>()

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
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team invite <player> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Creates and invites a player to your team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team leave ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Leave your team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team accept <player> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Accept a player's team invite.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team kick <player> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Kick a player from your team.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/pm <message> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Talk in team chat.")
            sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/pmc ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Send your coordinates.")
            sender.sendMessage(Main.line)
            if (sender.hasPermission("uhc.command.team")) {
                sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team reset ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Reset all teams.")
                sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team management <on/off> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Enable/disable team management.")
                sender.sendMessage("${Main.prefix} ${ChatColor.GREEN}/team size <size> ${ChatColor.DARK_GRAY}-${ChatColor.WHITE} Set the size of teams.")
                sender.sendMessage(Main.line)
            }
        if (args[0] == "invite") {
            val player = sender as Player
            val target = Bukkit.getServer().getPlayer(args[1])
            if (args[1] == null) {
                sender.sendMessage("${ChatColor.RED}You need a teammate to invite.")
                return false
            }
            val playerTeam: Team = player.scoreboard.getPlayerTeam(player)
            if (playerTeam == null) {
                val oteams = ArrayList<Team>()
                for (team in Teams.manager.getTeams()) {
                    if (team.size == 0) {
                        oteams.add(team)
                    }
                }
                oteams[Random().nextInt(oteams.size)].addPlayer(player)
                sender.sendMessage("${Main.prefix} You have created a team.")
            }
            val targetTeam: Team = player.scoreboard.getPlayerTeam(target)
            if (targetTeam != null) {
                sender.sendMessage("${ChatColor.RED} ${ChatColor.WHITE}${target.name}${ChatColor.GRAY} is already on a team.")
                return false
            }
            for (players in playerTeam.getPlayers()) {
                if (players is Player) {
                    players.sendMessage(Main.line)
                    players.sendMessage("${Main.prefix} ${ChatColor.WHITE}${target.name}${ChatColor.GRAY} was invited to your team.")
                    players.sendMessage(Main.line)
                }
            }
            invites[player]?.add(target)
            target.sendMessage(Main.line)
            target.sendMessage("${Main.prefix} You've been invited to join ${ChatColor.WHITE}${player.name}${ChatColor.GRAY}'s team, accept the invite using ${ChatColor.WHITE}/team accept ${player.name}${ChatColor.GRAY}!")
            target.sendMessage(Main.line)
        } else if (args[0] == "accept") {
            val player = sender as Player
            val target = Bukkit.getServer().getPlayer(args[1])
            if (invites.containsKey(target) && invites[target]!!.contains(player)) {
                val targetTeam = target.scoreboard.getPlayerTeam(target)
                if (targetTeam == null) {
                    player.sendMessage("${ChatColor.RED}That player is not on a team at the moment.")
                    return false
                } else if (targetTeam.size >= Main.teamSize) {
                    player.sendMessage("${ChatColor.RED}That player's team has reached the max teamsize.")
                    return false
                }
                targetTeam.addPlayer(player)
                for (players in targetTeam.getPlayers()) {
                    if (players is Player) {
                        players.sendMessage(Main.line)
                        players.sendMessage(Main.prefix + player.name + " joined your team.")
                        players.sendMessage(Main.line)
                    }
                }

            }
        }



        return true
    }
    return true
}}
