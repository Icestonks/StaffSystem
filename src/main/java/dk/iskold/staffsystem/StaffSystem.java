package dk.iskold.staffsystem;

import dk.iskold.staffsystem.commands.Staff;
import dk.iskold.staffsystem.commands.StaffChat;
import dk.iskold.staffsystem.events.*;
import dk.iskold.staffsystem.tasks.InventoryManager;
import dk.iskold.staffsystem.tasks.Vanish;
import dk.iskold.staffsystem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

import static dk.iskold.staffsystem.commands.Staff.staffmode_players;

public class StaffSystem extends JavaPlugin {

    public static Config config, data, license;
    public static FileConfiguration configYML, dataYML, licenseYML;
    public static StaffSystem instance;
    @Override
    public void onEnable() {
        instance = this;

        if (!(new File(getDataFolder(), "license.yml")).exists())
            saveResource("license.yml", false);

        license = new Config(this, null, "license.yml");
        licenseYML = license.getConfig();

        String license = licenseYML.getString("License");
        if(!new AdvancedLicense(license, "https://license.cutekat.dk/verify.php", this).debug().register()) return;


        //config yml
        if (!(new File(getDataFolder(), "config.yml")).exists())
            saveResource("config.yml", false);

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();


        //data yml
        if (!(new File(getDataFolder(), "data.yml")).exists())saveResource("data.yml", false);

        data = new Config(this, null, "data.yml");
        dataYML = data.getConfig();

        //Register Events
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new ConsumeListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new StaffModeListener(), this);

        //Register Commands
        getCommand("Staff").setExecutor(new Staff());
        getCommand("staffchat").setExecutor(new StaffChat());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        for (Player player : staffmode_players) {
            InventoryManager.restoreInventory(player);
            player.setFlying(false);
            player.setAllowFlight(false);

            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.2F);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Player hiddenPlayer : Vanish.vanishedPlayers) {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.showPlayer(hiddenPlayer);
            }
        }
    }
}
