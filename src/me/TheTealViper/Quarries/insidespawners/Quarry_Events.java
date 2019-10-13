package me.TheTealViper.Quarries.insidespawners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.TheTealViper.Quarries.VersionType;
import me.TheTealViper.Quarries.Quarries;

public class Quarry_Events implements Listener{

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		List<Quarry> dummy = new ArrayList<Quarry>(Quarry.DATABASE.values());
		for(Quarry q : dummy) {
			if(q.loc.equals(e.getBlock().getLocation())) {
				q.breakQuarry();
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.isCancelled())
			return;
		
		ItemStack item = e.getItemInHand();
		if(item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
			if(item.getItemMeta().getCustomModelData() == Quarries.TEXID_QUARRY) {
//				e.setCancelled(true);
				if(Quarries.version == VersionType.v1_14_R1) {
					Block b = e.getBlockPlaced();
					new Quarry(b.getLocation(), Quarries.getFacing(e.getPlayer()), true);
				}
			}
		}
	}
	
}
