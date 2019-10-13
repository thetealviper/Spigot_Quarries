package me.TheTealViper.viperfusion.insidespawners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;

import me.TheTealViper.viperfusion.PluginFile;
import me.TheTealViper.viperfusion.ViperFusion;
import me.TheTealViper.viperfusion.outsidespawners.Marker;
import me.TheTealViper.viperfusion.systems.QuarrySystem;

public class Quarry {
	public static Map<Location, Quarry> DATABASE = new HashMap<Location, Quarry>();
	public static PluginFile PLUGINFILE = new PluginFile(ViperFusion.plugin, "data/quarrys");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			ConfigurationSection sec = PLUGINFILE.getConfigurationSection("locs");
			if(sec != null && sec.getKeys(false) != null) {
				for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
					new Quarry(ViperFusion.parseLoc(s), null, false);
				}
			}
		}

		ViperFusion.plugin.getServer().getPluginManager().registerEvents(new Quarry_Events(), ViperFusion.plugin);
	}
	public static void onDisable() {
		List<String> stringList = new ArrayList<String>();
		for(Quarry i : DATABASE.values()) {
			stringList.add(ViperFusion.locToString(i.loc));
		}
		PLUGINFILE.set("locs", stringList);
		PLUGINFILE.save();
	}
	public static Quarry getQuarry(Location loc) {
		return DATABASE.get(loc);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Location loc = null;

	public Quarry(Location loc, BlockFace face, boolean generateNew) {
		this.loc = loc;
		DATABASE.put(loc, this);
		
		if(generateNew) {
			ViperFusion.createInsideSpawner(loc.getBlock(), Integer.parseInt(String.valueOf(ViperFusion.TEXID_QUARRY) + ViperFusion.facingToAddedInt(face)));
			
			if(Marker.DATABASE.containsKey(loc.getBlock().getRelative(BlockFace.NORTH).getLocation()))
				QuarrySystem.initCreateQuarrySystem(loc.getBlock(), loc.getBlock().getRelative(BlockFace.NORTH), BlockFace.NORTH);
			else if(Marker.DATABASE.containsKey(loc.getBlock().getRelative(BlockFace.EAST).getLocation()))
				QuarrySystem.initCreateQuarrySystem(loc.getBlock(), loc.getBlock().getRelative(BlockFace.EAST), BlockFace.EAST);
			else if(Marker.DATABASE.containsKey(loc.getBlock().getRelative(BlockFace.SOUTH).getLocation()))
				QuarrySystem.initCreateQuarrySystem(loc.getBlock(), loc.getBlock().getRelative(BlockFace.SOUTH), BlockFace.SOUTH);
			else if(Marker.DATABASE.containsKey(loc.getBlock().getRelative(BlockFace.WEST).getLocation()))
				QuarrySystem.initCreateQuarrySystem(loc.getBlock(), loc.getBlock().getRelative(BlockFace.WEST), BlockFace.WEST);
		}
	}
	
	public void breakQuarry() {
		DATABASE.remove(loc);
		loc.getBlock().setType(Material.AIR);
		
		loc = null;
	}
}
