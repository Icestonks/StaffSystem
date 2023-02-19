package dk.iskold.staffsystem.commands;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.tasks.InventoryManager;
import dk.iskold.staffsystem.tasks.TeleportMenu;
import dk.iskold.staffsystem.tasks.Through;
import dk.iskold.staffsystem.tasks.Vanish;
import dk.iskold.staffsystem.utils.Chat;
import dk.iskold.staffsystem.utils.GUI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Staff implements CommandExecutor {
    public static ArrayList<Player> staffmode_players = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        String permission = StaffSystem.configYML.getString("staffmode.permission");
        if(!p.hasPermission(permission)) {
            p.sendMessage(Chat.colored("&cDette har du ikke adgang til!"));
            return true;
        }

        String staffmode_prefix = StaffSystem.configYML.getString("staffmode.prefix");
        String staffmode_enabled = StaffSystem.configYML.getString("staffmode.staffmode-messages.enabled-staffmode");
        String staffmode_disabled = StaffSystem.configYML.getString("staffmode.staffmode-messages.disabled-staffmode");

        String fly_name = StaffSystem.configYML.getString("fly.item-name");
        boolean fly_enabled = StaffSystem.configYML.getBoolean("fly.enabled");

        String ride_name = StaffSystem.configYML.getString("ride.item-name");
        boolean ride_enabled = StaffSystem.configYML.getBoolean("ride.enabled");

        String vanish_name = StaffSystem.configYML.getString("vanish.item-name");
        boolean vanish_enabled = StaffSystem.configYML.getBoolean("vanish.enabled");

        String staffchat_name = StaffSystem.configYML.getString("staffchat.item-name");
        boolean staffchat_enabled = StaffSystem.configYML.getBoolean("staffchat.enabled");

        String thru_name = StaffSystem.configYML.getString("thru.item-name");
        boolean thru_enabled = StaffSystem.configYML.getBoolean("thru.enabled");

        String teleport_menu_name = StaffSystem.configYML.getString("teleport-menu.item-name");
        boolean teleport_menu_enabled = StaffSystem.configYML.getBoolean("teleport-menu.enabled");
        boolean speed_enabled = StaffSystem.configYML.getBoolean("speed.enabled");

        if(args.length == 1 && args[0].equalsIgnoreCase("reload") && p.hasPermission(StaffSystem.configYML.getString("staffmode.reload-permission"))) {
            boolean reloadSuccess;
            try {
                StaffSystem.config.reloadConfig();
                StaffSystem.configYML = StaffSystem.config.getConfig();

                reloadSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
                reloadSuccess = false;
            }
            if (reloadSuccess) {
                p.sendMessage(Chat.colored("&aReload successfully completed"));
            } else {
                p.sendMessage(Chat.colored("&cAn error occurred. Please check the console."));
            }
            return true;
        }


        if(!staffmode_players.contains(p)) {

            p.sendMessage(Chat.colored(staffmode_prefix + staffmode_enabled));

            staffmode_players.add(p);
            InventoryManager.saveInventory(p);
            p.getInventory().clear();
            p.getInventory().setItem(0, GUI.createItemStack(fly_enabled, new ItemStack(Material.FEATHER), fly_name, "&7", "&7Højre klik for at &fflyve&7!"));
            p.getInventory().setItem(1, GUI.createItemStack(ride_enabled, new ItemStack(Material.SADDLE), ride_name, "&7", "&7Højre klik for at &fride&7 på en person!"));

            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);

            ItemStack splashPotion = new ItemStack(Material.POTION, 1, (short) 16421);
            PotionMeta potionMeta = (PotionMeta) splashPotion.getItemMeta();
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1), true);
            splashPotion.setItemMeta(potionMeta);

            p.getInventory().setItem(3, GUI.createItemStack(vanish_enabled, splashPotion, vanish_name, "&7", "&7Højre klik for at gå i &fvanish!"));
            p.getInventory().setItem(4, GUI.createItemStack(staffchat_enabled, new ItemStack(Material.PAPER), staffchat_name, "&7", "&7Højre klik for at aktivere &fstaffchat&7!"));
            p.getInventory().setItem(5, GUI.createItemStack(thru_enabled, new ItemStack(Material.ENDER_PEARL), thru_name, "&7", "&7Højre klik for at &fteleportere &7igennem væge!"));
            p.getInventory().setItem(7, GUI.createItemStack(teleport_menu_enabled, new ItemStack(Material.COMPASS), teleport_menu_name, "&7", "&7Højre klik for at få en menu op med &falle spillere&7!"));

            //CONFIG
            String speed_name = StaffSystem.configYML.getString("speed.item-name");

            //SPLIT
            Float speed_amount = p.getWalkSpeed();
            String final_speed;
            if (speed_amount == 1.0F) {
                final_speed = "10";
            } else {
                String[] speed_split = String.valueOf(speed_amount).split("\\.");
                final_speed = String.valueOf(speed_split[1].charAt(0));
            }


            //REPLACE
            speed_name = speed_name.replace("%speed%", final_speed);


            p.getInventory().setItem(8, GUI.createItemStack(speed_enabled, new ItemStack(Material.PRISMARINE_SHARD), speed_name, "&7", "&7Højre klik for at &fforhøje din speed&7!"));

        } else {
            p.sendMessage(Chat.colored(staffmode_prefix + staffmode_disabled));
            staffmode_players.remove(p);
            p.getInventory().clear();
            InventoryManager.restoreInventory(p);
            Vanish.showPlayer(p, false);
            if(p.getGameMode() != GameMode.CREATIVE) {
                p.setFlying(false);
                p.setAllowFlight(false);
            }
            p.setWalkSpeed(0.2F);
            p.setFlySpeed(0.2F);
        }

        return true;
    }
}
