package dk.iskold.staffsystem.events;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.tasks.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    StaffSystem plugin;
    public JoinListener(StaffSystem plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onJoinListener(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        for(Player vanishedPlayers : Vanish.vanishedPlayers) {
            player.hidePlayer(vanishedPlayers);
        }

    }
}
