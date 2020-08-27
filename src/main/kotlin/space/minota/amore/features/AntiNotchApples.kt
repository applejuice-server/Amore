package space.minota.amore.features

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Recipe


class AntiNotchApples {
    init {
        val it = Bukkit.getServer().recipeIterator()
        var recipe: Recipe?

        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.result.type == Material.GOLDEN_APPLE && recipe.result.durability.equals(1.0)) {
                it.remove();
            }
        }
    }

}