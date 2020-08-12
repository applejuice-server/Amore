package space.minota.amore.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*


class GUI {

    private var name: String? = null
    private var rows = 0
    private var items: HashMap<Int, ItemStack>? = null

    fun GUI() {
        name = "Inventory"
        rows = 1
        items = HashMap()
    }

    fun rows(newRows: Int): GUI? {
        rows = newRows
        return this
    }

    fun name(newName: String?): GUI? {
        name = ChatColor.translateAlternateColorCodes('&', newName);
        return this
    }

    fun item(slot: Int, item: ItemStack): GUI? {
        items!![slot] = item
        return this
    }

    fun make(): Inventory? {
        require(rows * 9 <= 54) { "Too many rows in the created inventory!" }
        val inv = Bukkit.createInventory(null, rows * 9, name)
        for (f in items!!.keys) {
            inv.setItem(f, items!![f])
        }
        return inv
    }

}