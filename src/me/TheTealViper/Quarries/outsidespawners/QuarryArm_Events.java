package me.TheTealViper.Quarries.outsidespawners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class QuarryArm_Events implements Listener{

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		List<QuarryArm> dummy = new ArrayList<QuarryArm>(QuarryArm.DATABASE.values());
		for(QuarryArm arm : dummy) {
			if(e.getBlock().getLocation().equals(arm.loc)) {
				arm.breakQuarryArm();
			}
		}
	}
	
}
