package dk.iskold.staffsystem.tasks;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.commands.Staff;
import dk.iskold.staffsystem.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class Vanish {

    public static List<Player> vanishedPlayers = new ArrayList<Player>();
    public static void hidePlayer(Player player) {

        String vanish_prefix = Chat.colored(StaffSystem.configYML.getString("vanish.prefix"));
        String vanish_enabled = Chat.colored(StaffSystem.configYML.getString("vanish.enabled-vanish"));

        List<String> show_always = StaffSystem.configYML.getStringList("vanish.show-always");

        vanishedPlayers.add(player);
        //player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {

            for (String permission : show_always) {
                if(!loopPlayer.hasPermission(permission)) {
                    loopPlayer.hidePlayer(player);
                } else {
                    loopPlayer.showPlayer(player);
                }
            }
        }
        player.sendMessage(vanish_prefix + vanish_enabled);
    }

    public static void showPlayer(Player player, boolean sendMessage) {
        vanishedPlayers.remove(player);
        //player.removePotionEffect(PotionEffectType.INVISIBILITY);
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.showPlayer(player);
        }

        if(sendMessage) {
            String vanish_prefix = Chat.colored(StaffSystem.configYML.getString("vanish.prefix"));
            String vanish_disabled = Chat.colored(StaffSystem.configYML.getString("vanish.disabled-vanish"));
            player.sendMessage(vanish_prefix + vanish_disabled);
        }
    }
}
