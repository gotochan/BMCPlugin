package com.github.gotochan.kit.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.gotochan.Utils.Utils;
import com.github.gotochan.kit.BMCKit;
import com.github.gotochan.kit.IconPackage;
import com.github.gotochan.kit.KitUtils;

public class Scout extends BMCKit
{
	
	private static final ItemStack _GRAPPLE = KitUtils.addSoulBound(new ItemStack(Material.FISHING_ROD));
	private static final ItemStack GRAPPLE = Utils.addItemName(_GRAPPLE, "§6Grapple");
	
	
	@Override
	public void Initialize()
	{
	}
	
	@Override
	public String getName()
	{
		return "Scout";
	}
	
	@Override
	public IconPackage getIconPackage()
	{
		return new IconPackage(new ItemStack(Material.FISHING_ROD),
				new String[] {
						""
		}
				);
	}
	
	@Override
	public boolean canSelect(Player p_player)
	{
		return true;
	}
	
	@Override
	public void onPlayerSpawn(Player p_player)
	{
		p_player.getInventory().addItem(new ItemStack[] { GRAPPLE } );
	}
	
	@Override
	public void cleanup(Player p_player)
	{
	}
	
}