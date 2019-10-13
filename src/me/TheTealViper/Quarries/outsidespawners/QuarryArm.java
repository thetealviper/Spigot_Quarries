package me.TheTealViper.Quarries.outsidespawners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import me.TheTealViper.Quarries.PluginFile;
import me.TheTealViper.Quarries.Quarries;

public class QuarryArm {
	
	public static Map<Location, QuarryArm> DATABASE = new HashMap<Location, QuarryArm>();
	public static PluginFile PLUGINFILE = new PluginFile(Quarries.plugin, "data/quarryarms");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
				new QuarryArm(Quarries.parseLoc(s), UUID.fromString(PLUGINFILE.getString("locs." + s)), false);
			}
		}
		
		Quarries.plugin.getServer().getPluginManager().registerEvents(new QuarryArm_Events(), Quarries.plugin);
	}
	public static void onDisable() {
		PLUGINFILE.set("locs", null);
		for(QuarryArm i : DATABASE.values()) {
			PLUGINFILE.set("locs." + Quarries.locToString(i.loc), i.uuid.toString());
		}
		PLUGINFILE.save();
	}
	public static QuarryArm getQuarryArm(Location loc) {
		return DATABASE.get(loc);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Location loc = null;
	public UUID uuid = null;
	
	public QuarryArm(Location loc, UUID uuid, boolean generateNew) {
		this.loc = loc;
		this.uuid = uuid;
		DATABASE.put(loc, this);
		
		if(generateNew)
			this.uuid = Quarries.createOutsideSpawner(loc.getBlock(), Material.ACACIA_FENCE, Quarries.TEXID_QUARRYARM);
	}
	
	public void breakQuarryArm() {
		Entity e = Bukkit.getEntity(uuid);
		if(e != null) e.remove();
		DATABASE.remove(loc);
		loc.getBlock().setType(Material.AIR);
		
		loc = null;
		uuid = null;
	}
	
}