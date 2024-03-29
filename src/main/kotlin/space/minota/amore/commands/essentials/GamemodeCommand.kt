package space.minota.amore.commands.essentials

import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import space.minota.amore.Main

class GamemodeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.gamemode")) {
            sender.sendMessage("${Main.prefix} You don't have permission to use this command.")
            return false
        }

        if (args.isEmpty()) {
            val player = sender as Player
            when (cmd.name) {
                "gmsp" -> {
                    player.gameMode = GameMode.SPECTATOR
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSpectator§7.")
                }
                "gmc" -> {
                    player.gameMode = GameMode.CREATIVE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cCreative§7.")
                }
                "gma" -> {
                    player.gameMode = GameMode.ADVENTURE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cAdventure§7.")
                }
                "gms" -> {
                    player.gameMode = GameMode.SURVIVAL
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSurvival§7.")
                }
            }
        } else {
            when (args[0]) {
                "s" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.SURVIVAL
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSurvival§7.")
                }
                "0" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.SURVIVAL
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSurvival§7.")
                }
                "survival" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.SURVIVAL
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSurvival§7.")
                }

                "c" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.CREATIVE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cCreative§7.")
                }
                "1" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.CREATIVE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cCreative§7.")
                }
                "creative" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.CREATIVE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cCreative§7.")
                }

                "a" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.ADVENTURE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cAdventure§7.")
                }
                "2" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.ADVENTURE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cAdventure§7.")
                }
                "adventure" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.ADVENTURE
                    player.sendMessage("${Main.prefix} Set your gamemode to §cAdventure§7.")
                }

                "3" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.SPECTATOR
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSpectator§7.")
                }
                "sp" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.SPECTATOR
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSpectator§7.")
                }
                "spectator" -> {
                    val player = sender as Player
                    player.gameMode = GameMode.SPECTATOR
                    player.sendMessage("${Main.prefix} Set your gamemode to §cSpectator§7.")
                }
            }
        }

        return true
    }

}