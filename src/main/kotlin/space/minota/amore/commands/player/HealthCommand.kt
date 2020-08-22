package space.minota.amore.commands.player

import net.minecraft.server.v1_8_R3.EntityLiving
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import space.minota.amore.Main
import space.minota.amore.utils.HealthChatColorer

class HealthCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
            if (args.isEmpty()) {
                val player = sender as Player
                val el: EntityLiving = (player as CraftPlayer).handle
                val health = player.health + el.absorptionHearts
                val color = HealthChatColorer(health)
                sender.sendMessage("${Main.prefix} ${ChatColor.WHITE}${player.displayName}${ChatColor.GRAY} is at ${color}${health}%${ChatColor.GRAY}.")
            }
        return true
    }
}