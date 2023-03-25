package dk.iskold.staffsystem.events;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.tasks.TeleportMenu;
import dk.iskold.staffsystem.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static dk.iskold.staffsystem.commands.Staff.staffmode_players;
import static org.bukkit.Sound.NOTE_PLING;

public class InventoryListener implements Listener {

    StaffSystem plugin;
    public InventoryListener(StaffSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(e.getClickedInventory() == null) return;

        if(staffmode_players.contains((Player) e.getWhoClicked())) {
            e.setCancelled(true);
        }

        String teleport_message = Chat.colored(StaffSystem.configYML.getString("teleport-menu.messages.teleport-message"));
        String inv_name = Chat.colored(StaffSystem.configYML.getString("teleport-menu.prefix"));
        if(!Objects.equals(e.getClickedInventory().getName(), inv_name)) return;
        if(e.getCurrentItem().getType() == Material.AIR) return;

        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();

        if(e.getSlot() >= 45 && e.getSlot() <= 53) {

            if(e.getSlot() == 53) {
                ArrayList<Player> allPlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
                allPlayers.remove(player);
                if(allPlayers.size() == 0) {
                    player.sendMessage(inv_name + Chat.colored("&cDer er ikke nogle spillere online!"));
                    return;
                }
                int random = new Random().nextInt(allPlayers.size());
                Player picked = allPlayers.get(random);

                player.closeInventory();
                player.teleport(picked);
                player.playSound(player.getLocation(), NOTE_PLING, 1, 5);
                return;
            }

            if(e.getSlot() == 50 || e.getSlot() == 48) {
                ItemStack item = e.getCurrentItem();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                int newPage = Integer.parseInt(lore.get(0).replace(Chat.colored("&7"), ""));

                if(e.getSlot() == 50 && e.getCurrentItem().getType() != Material.AIR) {
                    TeleportMenu.openMenu(player, newPage);
                }
                if(e.getSlot() == 48 && e.getCurrentItem().getType() != Material.AIR) {
                    TeleportMenu.openMenu(player, newPage);
                }
            }
            return;
        }

        String item_name = e.getCurrentItem().getItemMeta().getDisplayName();
        String[] item_split = item_name.split(Chat.colored("&l"));
        Player clickedPlayer = Bukkit.getPlayer(item_split[1]);
        if(clickedPlayer != null && clickedPlayer.isOnline()) {
            teleport_message = teleport_message.replace("%player%", clickedPlayer.getName());
            player.closeInventory();
            player.teleport(clickedPlayer);
            player.playSound(player.getLocation(), NOTE_PLING, 1, 5);
            player.sendMessage(inv_name + teleport_message);
        } else {
            e.getClickedInventory().clear(e.getSlot());
            player.sendMessage(Chat.colored(inv_name + "&cDenne spillere er ikke online mere!"));
        }


    }
}
