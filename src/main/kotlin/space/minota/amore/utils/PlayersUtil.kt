package space.minota.amore.utils

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

@SuppressWarnings("deprecation")
class PlayersUtil {

    companion object {
        fun getPlayers(): List<Player> {
            val list = ArrayList<Player>()
            for (online in Bukkit.getServer().onlinePlayers) {
                list.add(online)
            }
            return list
        }
    }

}