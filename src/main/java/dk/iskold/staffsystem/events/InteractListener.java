package dk.iskold.staffsystem.events;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.commands.Staff;
import dk.iskold.staffsystem.tasks.TeleportMenu;
import dk.iskold.staffsystem.tasks.Vanish;
import dk.iskold.staffsystem.utils.Chat;
import dk.iskold.staffsystem.utils.GUI;
import dk.iskold.staffsystem.tasks.Through;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InteractListener implements Listener {

    StaffSystem plugin;
    public InteractListener(StaffSystem plugin) {
        this.plugin = plugin;
    }

    //TeleportMenu teleportMenu = new TeleportMenu();

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = e.getPlayer();
            if(!Staff.staffmode_players.contains(player)) return;
            if(player.getItemInHand().getType() == Material.AIR) return;
            if(player.getItemInHand().getType() == Material.INK_SACK) {
                e.setCancelled(true);
                player.sendMessage(Chat.colored("&cDette er disablet!"));
                return;
            }

            e.setCancelled(true);

            String fly_prefix = Chat.colored(StaffSystem.configYML.getString("fly.prefix"));
            String fly_enabled = Chat.colored(StaffSystem.configYML.getString("fly.messages.enabled-fly"));
            String fly_disabled = Chat.colored(StaffSystem.configYML.getString("fly.messages.disable-fly"));


            String prefix = Chat.colored(StaffSystem.configYML.getString("staffchat.prefix"));
            String disabledstaffmode = Chat.colored(StaffSystem.configYML.getString("staffchat.disabled-staffmode"));
            String enabledstaffmode = Chat.colored(StaffSystem.configYML.getString("staffchat.enabled-staffchat"));
            String staff_chat_message = Chat.colored(StaffSystem.configYML.getString("staffchat.staff-chat-message"));

            String speed_prefix = StaffSystem.configYML.getString("speed.prefix");

            //THRU
            if(player.getItemInHand().getType() == Material.ENDER_PEARL) {
                player.getInventory().setItem(5, GUI.createItemStack(true, new ItemStack(Material.ENDER_PEARL), StaffSystem.configYML.getString("thru.item-name"), "&7", "&7Højre klik for at &fteleportere &7igennem væge!"));
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Through.teleport(player);
                }
            }

            //FLY
            if(player.getItemInHand().getType() == Material.FEATHER) {
                if(player.isFlying()) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.sendMessage(fly_prefix + fly_disabled);
                } else {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage(fly_prefix + fly_enabled);
                }
            }

            //VANSIH
            if(player.getItemInHand().getType() == Material.POTION) {

                ItemStack splashPotion = new ItemStack(Material.POTION, 1, (short) 16421);
                PotionMeta potionMeta = (PotionMeta) splashPotion.getItemMeta();
                potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1), true);
                splashPotion.setItemMeta(potionMeta);

                player.getInventory().setItem(3, GUI.createItemStack(true, splashPotion, StaffSystem.configYML.getString("vanish.item-name"), "&7", "&7Højre klik for at gå i &fvanish!"));
                if(!Vanish.vanishedPlayers.contains(player)) {
                    Vanish.hidePlayer(player);
                } else {
                    Vanish.showPlayer(player, true);
                }
            }


            //STAFF CHAT
            if(player.getItemInHand().getType() == Material.PAPER) {
                if(!StaffSystem.dataYML.contains("data.staffchat."+player.getUniqueId().toString())) {

                    player.sendMessage(prefix + disabledstaffmode);
                    StaffSystem.dataYML.set("data.staffchat." + player.getUniqueId().toString(), player.getUniqueId().toString());
                } else {
                    player.sendMessage(prefix + enabledstaffmode);
                    StaffSystem.dataYML.set("data.staffchat."+player.getUniqueId().toString(), null);
                }
                StaffSystem.data.saveConfig();
            }

            //TELPORT MENU
            if(player.getItemInHand().getType() == Material.COMPASS) {
                TeleportMenu.openMenu(player, 0);
            }

            //SPEED
            if(player.getItemInHand().getType() == Material.PRISMARINE_SHARD) {

                float new_speed = 0;
                if(player.getWalkSpeed() >= 0.9F) {
                    if(player.getWalkSpeed() >= 1.0F) {
                        new_speed = 0.1F;
                    } else {
                        new_speed = 1.0F;
                    }
                } else {
                    new_speed = player.getWalkSpeed() + 0.1F;
                }

                player.setWalkSpeed(new_speed);
                player.setFlySpeed(new_speed);

                //CONFIG
                String speed_name = StaffSystem.configYML.getString("speed.item-name");
                String new_speed_message = StaffSystem.configYML.getString("speed.messages.new-speed");

                //SPLIT
                Float speed_amount = player.getWalkSpeed();
                String final_speed;
                if (speed_amount == 1.0F) {
                    final_speed = "10";
                } else {
                    String[] speed_split = String.valueOf(speed_amount).split("\\.");
                    final_speed = String.valueOf(speed_split[1].charAt(0));
                }

                //REPLACE
                new_speed_message = new_speed_message.replace("%new-speed%", final_speed);
                speed_name = speed_name.replace("%speed%", final_speed);

                //SEND AND DELIVER
                player.sendMessage(Chat.colored(speed_prefix + new_speed_message));
                player.getInventory().setItem(8, GUI.createItemStack(true, new ItemStack(Material.PRISMARINE_SHARD), speed_name, "&7", "&7Højre klik for at &fforhøje din speed&7!"));
            }

        }
    }
}
