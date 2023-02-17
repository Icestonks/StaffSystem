package dk.iskold.staffsystem.tasks;

import dk.iskold.staffsystem.StaffSystem;
import dk.iskold.staffsystem.utils.Chat;
import dk.iskold.staffsystem.utils.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeleportMenu {

    static String name = Chat.colored(StaffSystem.configYML.getString("teleport-menu.prefix"));
    static String middle = "http://textures.minecraft.net/texture/e825419e429afc040c9e68b10523b917d7b8087d63e7648b10807da8b768ee";
    static String arrow_left = "http://textures.minecraft.net/texture/b76230a0ac52af11e4bc84009c6890a4029472f3947b4f465b5b5722881aacc7";
    static String arrow_right = "http://textures.minecraft.net/texture/dbf8b6277cd36266283cb5a9e6943953c783e6ff7d6a2d59d15ad0697e91d43c";
    static String help_head = "http://textures.minecraft.net/texture/797955462e4e576664499ac4a1c572f6143f19ad2d6194776198f8d136fdb2";

    public static void openMenu(Player player, int page) {
        Inventory inv = Bukkit.createInventory(null, 9*6, name);

        int size = Bukkit.getOnlinePlayers().size();
        int n = 0;
        int page_start = 45*page;
        int n2 = 1;

        for(Player loopPlayer : Bukkit.getOnlinePlayers()) {
            if(!player.equals(loopPlayer)) {
                if(n2 >= page_start) {
                    String loopPlayerName = loopPlayer.getName();
                    ItemStack head = GUI.getPlayerSkull(loopPlayerName);
                    inv.setItem(n, GUI.createItemStack(true, head, Chat.colored("&f&l" + loopPlayerName), "&7", "&7Tryk for at &fteleport&7!"));
                    n++;

                    if(n >= 45) {
                        break;
                    }
                }
            }
        }

        inv.setItem(49, GUI.createItemStack(true, GUI.getSkull(middle), Chat.colored("&f&lSide"), "&fSide: &70/" + Math.round((float) size / (9 * 6))));
        inv.setItem(53, GUI.createItemStack(true, GUI.getSkull(help_head), Chat.colored("&f&lRANDOM TP"), "&7", "&7Tryk for at &fteleportere", "&7til en &frandom spiller&7!"));

        if(size > page_start + 45) {
            inv.setItem(50, GUI.createItemStack(true, GUI.getSkull(arrow_right), Chat.colored("&f&lNæste Side"), "&7" + (page + 1)));
        }
        if(page > 0) {
            inv.setItem(48, GUI.createItemStack(true, GUI.getSkull(arrow_left), Chat.colored("&f&lForrige Side"), "&7" + (page - 1)));
        }

        player.openInventory(inv);

    }
}
