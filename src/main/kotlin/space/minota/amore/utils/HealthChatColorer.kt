package space.minota.amore.utils

class HealthChatColorer(health: Double) {
    companion object {
        fun returnHealth(health: Int): String {
            var c = ""
            if (health == 0) {
                c = "§8"
            } else if (health > 35) {
                c = "§c"
            } else if (health > 50) {
                c = "§e"
            } else if (health > 75) {
                c = "§6"
            } else if (health > 80) {
                c = "§a"
            } else if (health > 90) {
                c = "§2"
            }
            return c
        }
    }

}