package dk.iskold.staffsystem.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import static dk.iskold.staffsystem.commands.Staff.staffmode_players;

public class StaffModeListener implements Listener {


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(staffmode_players.contains((Player) event.getDamager())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if(staffmode_players.contains((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(staffmode_players.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if(staffmode_players.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent e) {
        if(staffmode_players.contains((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(staffmode_players.contains(e.getEntity())) {
            e.setKeepInventory(true);
            e.setKeepLevel(true);
        }
    }


}
