package dk.iskold.staffsystem.tasks;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.utils.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Through {
    private static final int SEARCH_DISTANCE = 20;

    public static void teleport(Player player) {

        boolean enabled = StaffSystem.configYML.getBoolean("thru.enabled", true);
        String prefix = Chat.colored(StaffSystem.configYML.getString("thru.prefix"));
        String thruSuccessMessage = Chat.colored(StaffSystem.configYML.getString("thru.messages.thru-success"));
        String thruFailMessage = Chat.colored(StaffSystem.configYML.getString("thru.messages.thru-fail"));

        if(!enabled) {
            player.sendMessage(Chat.colored("&cDette er disabled!"));
            return;
        }

        BlockIterator iter = new BlockIterator(player, 10);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }

        Location startLoc = lastBlock.getLocation();
        Vector playerDirection = player.getLocation().getDirection();
        Location addedLocation = startLoc;

        // Tjekker om der er 2x air blocks, så spilleren kan blive teleported
        for (int i = 1; i <= SEARCH_DISTANCE; i++) {
            Location setDirection = startLoc.setDirection(playerDirection);
            Vector direction = setDirection.getDirection();

            addedLocation.add(direction.multiply(1));
            Block addedBlock = addedLocation.getBlock();
            Block targetBlock = addedBlock.getRelative(0, 1, 0);
            Block blockAboveTarget = addedBlock.getRelative(0, 2, 0);
            boolean check = targetBlock.getType() == Material.AIR && blockAboveTarget.getType() == Material.AIR;

            if (check) {
                player.teleport(targetBlock.getLocation().setDirection(playerDirection).add(0.5, 0, 0.5));
                player.sendMessage(prefix + thruSuccessMessage);
                return;
            }
        }

        // Hvis der ikke blev fundet et sted spillere kunne teleport til
        player.sendMessage(prefix + thruFailMessage);
    }
}
