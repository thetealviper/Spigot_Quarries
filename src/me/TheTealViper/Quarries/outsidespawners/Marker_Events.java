package me.TheTealViper.Quarries.outsidespawners;

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

public class Marker_Events implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		List<Marker> dummy = new ArrayList<Marker>(Marker.DATABASE.values());
		for(Marker marker : dummy) {
			if(e.getBlock().getLocation().equals(marker.loc)) {
				marker.breakMarker();
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.isCancelled())
			return;
		
		ItemStack item = e.getItemInHand();
		if(item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
			if(item.getItemMeta().getCustomModelData() == Quarries.TEXID_MARKER) {
//				e.setCancelled(true);
				if(Quarries.version == VersionType.v1_14_R1
						|| Quarries.version == VersionType.v1_15_R1) {
					Block b = e.getBlockPlaced();
					new Marker(b.getLocation(), null, true);
				}
			}
		}
	}
	
}
