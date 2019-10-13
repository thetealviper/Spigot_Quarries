package me.TheTealViper.Quarries.insidespawners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;

import me.TheTealViper.Quarries.PluginFile;
import me.TheTealViper.Quarries.Quarries;
import me.TheTealViper.Quarries.systems.QuarrySystem;
import me.TheTealViper.Quarries.outsidespawners.Marker;

public class Quarry {
	public static Map<Location, Quarry> DATABASE = new HashMap<Location, Quarry>();
	public static PluginFile PLUGINFILE = new PluginFile(Quarries.plugin, "data/quarrys");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			ConfigurationSection sec = PLUGINFILE.getConfigurationSection("locs");
			if(sec != null && sec.getKeys(false) != null) {
				for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
					new Quarry(Quarries.parseLoc(s), null, false);
				}
			}
		}

		Quarries.plugin.getServer().getPluginManager().registerEvents(new Quarry_Events(), Quarries.plugin);
	}
	public static void onDisable() {
		List<String> stringList = new ArrayList<String>();
		for(Quarry i : DATABASE.values()) {
			stringList.add(Quarries.locToString(i.loc));
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
			Quarries.createInsideSpawner(loc.getBlock(), Integer.parseInt(String.valueOf(Quarries.TEXID_QUARRY) + Quarries.facingToAddedInt(face)));
			
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
