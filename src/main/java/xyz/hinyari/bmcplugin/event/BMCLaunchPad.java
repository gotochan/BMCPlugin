package xyz.hinyari.bmcplugin.event;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BMCLaunchPad implements Listener
{

    static long LIMIT = 3000L;
    private Map<UUID, Long> launchTimes = new HashMap<>();

    @EventHandler
    public void onPlayerStep(PlayerInteractEvent e)
    {
        if (e.getAction().equals(Action.PHYSICAL))
        {
            Player p = e.getPlayer();
            if ((e.getClickedBlock().getType().equals(Material.STONE_PLATE)) || (e.getClickedBlock()
                    .getType()
                    .equals(Material.IRON_PLATE)) || (e.getClickedBlock().getType().equals(Material.GOLD_PLATE)))
            {
                Block b = e.getClickedBlock().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
                if ((b != null) && (b.getType().equals(Material.REDSTONE_BLOCK)))
                {
                    Location l = p.getLocation().add(0.0D, 1.0D, 0.0D);
                    p.teleport(l);
                    p.setVelocity(p.getVelocity().add(p.getLocation().getDirection().multiply(3)).setY(0));
                    this.launchTimes.put(p.getUniqueId(), System.currentTimeMillis());
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, LIMIT, (float) 2.0);
                }
            }
        }
    }


    @EventHandler
    public void anotherEvent(EntityDamageEvent event)
    {
        if ((event.getEntity() instanceof Player))
        {
            Player player = (Player) event.getEntity();
            Long time = this.launchTimes.get(player.getUniqueId());
            if (time != null)
            {
                if (System.currentTimeMillis() - time.longValue() < LIMIT)
                {
                    event.setCancelled(true);
                } else
                {
                    this.launchTimes.remove(player.getName());
                }
            }
        }
    }
}
