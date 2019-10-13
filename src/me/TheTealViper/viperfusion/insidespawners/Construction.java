package me.TheTealViper.viperfusion.insidespawners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.TheTealViper.viperfusion.PluginFile;
import me.TheTealViper.viperfusion.ViperFusion;

public class Construction {
	public static Map<Location, Construction> DATABASE = new HashMap<Location, Construction>();
	public static PluginFile PLUGINFILE = new PluginFile(ViperFusion.plugin, "data/constructions");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			ConfigurationSection sec = PLUGINFILE.getConfigurationSection("locs");
			if(sec != null && sec.getKeys(false) != null) {
				for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
					new Construction(ViperFusion.parseLoc(s), false);
				}
			}
		}
		
		ViperFusion.plugin.getServer().getPluginManager().registerEvents(new Construction_Events(), ViperFusion.plugin);
	}
	public static void onDisable() {
		List<String> stringList = new ArrayList<String>();
		for(Construction i : DATABASE.values()) {
			stringList.add(ViperFusion.locToString(i.loc));
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
			ViperFusion.createInsideSpawner(loc.getBlock(), ViperFusion.TEXID_CONSTRUCTION);
	}
	
	public void breakConstruction() {
		DATABASE.remove(loc);
		loc.getBlock().setType(Material.AIR);
		
		loc = null;
	}
}
