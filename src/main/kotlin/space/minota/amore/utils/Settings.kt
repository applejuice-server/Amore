package space.minota.amore.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException


class Settings private constructor() {
    /**
     * Gets the data config.
     * @return the config.
     */
    var data: FileConfiguration? = null
    private var dfile: File? = null

    companion object {
        val instance = Settings()
        @JvmName("getInstance1")
        fun getInstance(): Settings? {
            return instance
        }

    }



    @JvmName("getData1")
    fun getData(): FileConfiguration? {
        return data
    }

    /**
     * Sets the settings manager up and creates missing files.
     * @param p the main class.
     */
    fun setup(p: Plugin) {
        if (!p.dataFolder.exists()) {
            p.dataFolder.mkdir()
        }
        dfile = File(p.dataFolder, "data.yml")
        if (!dfile!!.exists()) {
            try {
                dfile!!.createNewFile()
            } catch (ex: IOException) {
                Bukkit.getServer().logger.severe(ChatColor.RED.toString() + "Could not create data.yml!")
            }
        }
        data = YamlConfiguration.loadConfiguration(dfile)
    }


    /**
     * Saves the data config.
     */
    fun saveData() {
        try {
            data!!.save(dfile)
        } catch (ex: IOException) {
            Bukkit.getServer().logger.severe(ChatColor.RED.toString() + "Could not save data.yml!")
        }
    }

    /**
     * Reloads the data file.
     */
    fun reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile)
    }

}