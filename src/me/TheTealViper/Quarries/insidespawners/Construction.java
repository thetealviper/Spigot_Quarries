package me.TheTealViper.Quarries.insidespawners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.TheTealViper.Quarries.PluginFile;
import me.TheTealViper.Quarries.Quarries;

public class Construction {
	public static Map<Location, Construction> DATABASE = new HashMap<Location, Construction>();
	public static PluginFile PLUGINFILE = new PluginFile(Quarries.plugin, "data/constructions");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			ConfigurationSection sec = PLUGINFILE.getConfigurationSection("locs");
			if(sec != null && sec.getKeys(false) != null) {
				for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
					new Construction(Quarries.parseLoc(s), false);
				}
			}
		}
		
		Quarries.plugin.getServer().getPluginManager().registerEvents(new Construction_Events(), Quarries.plugin);
	}
	public static void onDisable() {
		List<String> stringList = new ArrayList<String>();
		for(Construction i : DATABASE.values()) {
			stringList.add(Quarries.locToString(i.loc));
		}
		PLUGINFILE.set("locs", stringList);
		PLUGINFILE.save();
	}
	public static Construction getConstruction(Location loc) {
		return DATABASE.get(loc);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Location loc = null;

	public Construction(Location loc, boolean generateNew) {
		this.loc = loc;
		DATABASE.put(loc, this);
		
		if(generateNew)
			Quarries.createInsideSpawner(loc.getBlock(), Quarries.TEXID_CONSTRUCTION);
	}
	
	public void breakConstruction() {
		DATABASE.remove(loc);
		loc.getBlock().setType(Material.AIR);
		
		loc = null;
	}
}
