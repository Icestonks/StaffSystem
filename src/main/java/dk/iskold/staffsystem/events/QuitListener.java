package dk.iskold.staffsystem.events;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.tasks.InventoryManager;
import dk.iskold.staffsystem.tasks.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static dk.iskold.staffsystem.commands.Staff.staffmode_players;

public class QuitListener implements Listener {

    StaffSystem plugin;
    public QuitListener(StaffSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuitListener(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if(staffmode_players.contains(player)) {
            staffmode_players.remove(player);
            player.getInventory().clear();
            InventoryManager.restoreInventory(player);
            Vanish.showPlayer(player, false);

            player.setFlying(false);
            player.setAllowFlight(false);
            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.2F);
        }
    }
}
