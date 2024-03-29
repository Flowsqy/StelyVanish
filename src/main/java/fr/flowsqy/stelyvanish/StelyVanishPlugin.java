package fr.flowsqy.stelyvanish;

import fr.flowsqy.stelyvanish.io.Messages;
import fr.flowsqy.teampacketmanager.TeamPacketManager;
import fr.flowsqy.teampacketmanager.TeamPacketManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StelyVanishPlugin extends JavaPlugin {

    private Messages messages;
    private TeamPacketManager teamPacketManager;

    @Override
    public void onEnable() {
        final Logger logger = getLogger();
        final File dataFolder = getDataFolder();

        if (!checkDataFolder(dataFolder)) {
            logger.log(Level.WARNING, "Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.log(Level.WARNING, "Disable the plugin");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.messages = new Messages(initFile(dataFolder, "messages.yml"));

        final TeamPacketManagerPlugin teamPacketManagerPlugin = JavaPlugin.getPlugin(TeamPacketManagerPlugin.class);
        teamPacketManager = teamPacketManagerPlugin.getTeamPacketManager();
    }

    private boolean checkDataFolder(File dataFolder) {
        if (dataFolder.exists())
            return dataFolder.canWrite();
        return dataFolder.mkdirs();
    }

    private YamlConfiguration initFile(File dataFolder, String fileName) {
        final File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(fileName)), file.toPath());
            } catch (IOException ignored) {
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public Messages getMessages() {
        return messages;
    }

    public TeamPacketManager getTeamPacketManager() {
        return teamPacketManager;
    }

}
