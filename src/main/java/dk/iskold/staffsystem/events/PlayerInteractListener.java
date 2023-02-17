package dk.iskold.staffsystem.events;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.utils.Chat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static dk.iskold.staffsystem.commands.Staff.staffmode_players;

public class PlayerInteractListener implements Listener {

    StaffSystem plugin;
    public PlayerInteractListener(StaffSystem plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if(!staffmode_players.contains(player)) return;

        if(e.getRightClicked() instanceof Player) {
            Player vehicle = (Player) e.getRightClicked();

            String ride_prefix = Chat.colored(StaffSystem.configYML.getString("ride.prefix"));
            String passenger_message = Chat.colored(StaffSystem.configYML.getString("ride.messages.passenger-message"));
            String vehicle_message = Chat.colored(StaffSystem.configYML.getString("ride.messages.vehicle-message"));

            passenger_message = passenger_message.replace("%vehicle%", vehicle.getName());
            vehicle_message = vehicle_message.replace("%passenger%", player.getName());

            if(player.getItemInHand().getType() == Material.SADDLE) {
                vehicle.setPassenger(player);
                player.sendMessage(ride_prefix + passenger_message);
                vehicle.sendMessage(ride_prefix + vehicle_message);
            }
        }
    }
}
