package space.minota.amore.listeners

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.SkullType
import org.bukkit.block.Block
import org.bukkit.block.Skull
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import space.minota.amore.Main
import space.minota.amore.utils.Blocks
import java.util.*


@SuppressWarnings("deprecation")
class Players : Listener {

    @EventHandler
    fun onEntityRegainHealth(event: EntityRegainHealthEvent) {
        if (event.entity !is Player) return
        if (event.regainReason == RegainReason.SATIATED) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity.player
        player.world.strikeLightningEffect(player.location)
        player.location.block.type = Material.NETHER_FENCE
        player.location.add(0.0, 1.0, 0.0).block.type = Material.SKULL

        val skull: Skull = player.location.add(0.0, 1.0, 0.0).block.state as Skull
        skull.skullType = SkullType.PLAYER
        skull.owner = player.name
        skull.rotation = Blocks.getBlockFaceDirection(player.location)
        skull.update()

        val b: Block = player.location.add(0.0, 1.0, 0.0).block
        b.setData(0x1.toByte(), true)
        player.sendTitle("${ChatColor.DARK_RED}${ChatColor.BOLD}YOU DIED!", event.deathMessage)
    }

    @EventHandler
    fun onPlayerConsume(event: PlayerItemConsumeEvent) {
        val player = event.player
        val before = player.saturation
        object : BukkitRunnable() {
            override fun run() {
                val change = player.saturation - before
                player.saturation = (before + change * 2.5).toFloat()
            }
        }.runTaskLater(Main.plugin, 1L)
        if (!Main.absorption) {
            if (event.item.type === Material.GOLDEN_APPLE) {
                Bukkit.getServer().scheduler.scheduleSyncDelayedTask(Main.plugin, {
                    player.removePotionEffect(PotionEffectType.ABSORPTION)
                }, 1L)
            }
        }
        if (event.item.type === Material.GOLDEN_APPLE && event.item.itemMeta.displayName != null && event.item.itemMeta.displayName == "§6Golden Head") {
            player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 200, 1))
        }
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (event.foodLevel < (event.entity as Player).foodLevel) {
            event.isCancelled = Random().nextInt(100) < 66
        }
    }

}