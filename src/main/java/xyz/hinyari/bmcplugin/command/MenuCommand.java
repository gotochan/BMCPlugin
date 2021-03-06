package xyz.hinyari.bmcplugin.command;

import java.util.ArrayList;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuCommand extends SubCommandAbst implements Listener
{

    private static final String COMMAND_NAME = "menu";

    public ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();
    public ArrayList<ItemStack> apanel = new ArrayList<ItemStack>();

    private BMCPlugin bmc;

    public MenuCommand(BMCPlugin bmc)
    {
        this.bmc = bmc;
    }

    @Override
    public String getCommandName()
    {
        return COMMAND_NAME;
    }

    @Override
    public boolean runCommand(BMCPlayer player, String label, String[] args)
    {
        if (player.hasPermission("bmc.rankmenu"))
            player.getPlayer().openInventory(bmc.rankGUIMenu.getMainMenu(player));
        return false;
    }
}
