package space.minota.amore.commands.essentials

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import java.util.*

import space.minota.amore.Main

class ClearInventoryCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, lbl: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("uhc.command.ci")) {
            sender.sendMessage("${Main.prefix} You do not have permission to use this command.")
            return false;
        }
        if (args.isEmpty()) {
            val player = sender as Player
            val inv = player.inventory

            // clear main inventory
            inv.clear()

            // clear armour slots
            inv.armorContents = null

            player.itemOnCursor = ItemStack(Material.AIR)

            val openInventory = player.openInventory;
            if (openInventory.type == InventoryType.CRAFTING) {
                openInventory.topInventory.clear()
            }

            player.sendMessage("${Main.prefix} You've cleared your own inventory.")
        } else {
            if (args[0] == "*") {
                for (online in ArrayList(Bukkit.getServer().onlinePlayers)) {
                    val inv = online.inventory

                    // clear main inventory
                    inv.clear()

                    // clear armour slots
                    inv.armorContents = null

                    online.itemOnCursor = ItemStack(Material.AIR)

                    val openInventory = online.openInventory;
                    if (openInventory.type == InventoryType.CRAFTING) {
                        openInventory.topInventory.clear()
                    }
                    online.sendMessage("${Main.prefix} Your inventory has been cleared by §c${sender.name}§7.")
                }
                sender.sendMessage("${Main.prefix} You've cleared all players' inventories.")
                return true;
            } else {
                val target = Bukkit.getServer().getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage("${ChatColor.RED}That player is not online or has never logged onto the server.")
                }
                val inv = target.inventory

                // clear main inventory
                inv.clear()

                // clear armour slots
                inv.armorContents = null

                target.itemOnCursor = ItemStack(Material.AIR)

                val openInventory = target.openInventory;
                if (openInventory.type == InventoryType.CRAFTING) {
                    openInventory.topInventory.clear()
                }
                target.sendMessage("${Main.prefix} Your inventory has been cleared by §c${sender.name}§7.")
                sender.sendMessage("${Main.prefix} Cleared §c${target.name}'s§7 inventory.")
                return true;
            }
        }

        return true;
    }

}