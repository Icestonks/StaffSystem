package dk.iskold.staffsystem.commands;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permission = StaffSystem.configYML.getString("staffmode.permission");
        String prefix = Chat.colored(StaffSystem.configYML.getString("staffchat.prefix"));
        boolean enabled = StaffSystem.configYML.getBoolean("staffchat.enabled");
        String disabledstaffmode = Chat.colored(StaffSystem.configYML.getString("staffchat.disabled-staffmode"));
        String enabledstaffmode = Chat.colored(StaffSystem.configYML.getString("staffchat.enabled-staffchat"));
        String staff_chat_message = Chat.colored(StaffSystem.configYML.getString("staffchat.staff-chat-message"));



        Player player = (Player) sender;
        if(!enabled) {
            player.sendMessage("&cDette er disabled!");
            return true;
        }

        if(!player.hasPermission(permission)) {
            player.sendMessage(Chat.colored("&cDette har du ikke adgang til!"));
            return true;
        }

        //Hvis man er i data.yml, så har man slået det fra.
        if (args.length == 0) {
            if(!StaffSystem.dataYML.contains("data.staffchat."+player.getUniqueId().toString())) {

                player.sendMessage(prefix + disabledstaffmode);
                StaffSystem.dataYML.set("data.staffchat." + player.getUniqueId().toString(), player.getUniqueId().toString());
            } else {
                player.sendMessage(prefix + enabledstaffmode);
                StaffSystem.dataYML.set("data.staffchat."+player.getUniqueId().toString(), null);
            }
            StaffSystem.data.saveConfig();
            return true;
        }


        StaffSystem.dataYML.set("data.staffchat."+player.getUniqueId().toString(), null);
        StaffSystem.data.saveConfig();


        player.sendMessage(prefix + staff_chat_message.replace("%player%", player.getName()).replace("%message%", String.join(" ", args)));
        ArrayList<Player> allPlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        allPlayers.remove(player);

        for(Player player1 : allPlayers) {
            if (player1.hasPermission(permission)) {
                if(!StaffSystem.dataYML.contains("data.staffchat."+player1.getUniqueId().toString())) {

                    player1.sendMessage(prefix + staff_chat_message.replace("%player%", player.getName()).replace("%message%", String.join(" ", args)));

                    return true;
                }
            }
        }




        return false;
    }
}
