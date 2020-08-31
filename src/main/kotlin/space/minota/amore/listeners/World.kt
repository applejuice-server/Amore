package space.minota.amore.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldInitEvent
import space.minota.amore.features.CanePopulator

class World : Listener {

    @EventHandler
    fun onWorldInitialize(e: WorldInitEvent) {
        val world = e.world
        world.populators.add(CanePopulator())
    }

}