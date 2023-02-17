package dk.iskold.staffsystem.events;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.tasks.Vanish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import static dk.iskold.staffsystem.commands.Staff.staffmode_players;

public class ConsumeListener implements Listener {

    StaffSystem plugin;
    public ConsumeListener(StaffSystem plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onConsumeListener(PlayerItemConsumeEvent e) {
        if(staffmode_players.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
