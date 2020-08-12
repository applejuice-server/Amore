package space.minota.amore.commands.essentials

import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player




class GamemodeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {

        if (!sender.hasPermission("uhc.command.gamemode")) {
            sender.sendMessage("§8[§4UHC§8]§7 You do not have permission to use this command.")
            return false;
        }

        if (cmd.toString().toLowerCase() == "gm" || cmd.toString().toLowerCase() == "gamemode") {
            if (args.isEmpty()) {
                sender.sendMessage("§8[§4UHC§8]§7 Invalid usage:§c /gm <adventure|creative|survival|spectator>§7.")
                return false;
            } else {
                if (args[0] !== "a" && args[0] !== "adventure" && args[0] !== "2" && args[0] !== "c" && args[0] !== "creative" && args[0] !== "1" && args[0] !== "s" && args[0] !== "survival" && args[0] !== "0" && args[0] !== "sp" && args[0] !== "spectator" && args[0] !== "3") {
                    sender.sendMessage("§8[§4UHC§8]§7 Invalid usage:§c /gm <adventure|creative|survival|spectator>§7.")
                    return false;
                }
                if (args[0] == "a" || args[0] == "adventure" || args[0] == "2") {
                    val player = sender as Player
                    player.gameMode = GameMode.ADVENTURE
                    player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cAdventure§7.");
                }
                if (args[0] == "c" || args[0] == "creative" || args[0] == "1") {
                    val player = sender as Player
                    player.gameMode = GameMode.CREATIVE
                    player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cCreative§7.");
                }
                if (args[0] == "sp" || args[0] == "spectator" || args[0] == "3") {
                    val player = sender as Player
                    player.gameMode = GameMode.SPECTATOR
                    player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cSpectator§7.");
                }
            }
        }

        if (cmd.toString().toLowerCase() == "gmsp") {
            val player = sender as Player
            player.gameMode = GameMode.SPECTATOR
            player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cSpectator§7.");
        }
        if (cmd.toString().toLowerCase() == "gmc") {
            val player = sender as Player
            player.gameMode = GameMode.CREATIVE
            player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cCreative§7.");
        }
        if (cmd.toString().toLowerCase() == "gma") {
            val player = sender as Player
            player.gameMode = GameMode.ADVENTURE
            player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cAdventure§7.");
        }
        if (cmd.toString().toLowerCase() == "gms") {
            val player = sender as Player
            player.gameMode = GameMode.SURVIVAL
            player.sendMessage("§8[§4UHC§8]§7 Set your gamemode to §cAdventure§7.");
        }

        return true;

    }

}