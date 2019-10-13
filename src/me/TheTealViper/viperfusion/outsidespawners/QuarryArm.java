package me.TheTealViper.viperfusion.outsidespawners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import me.TheTealViper.viperfusion.PluginFile;
import me.TheTealViper.viperfusion.ViperFusion;

public class QuarryArm {
	
	public static Map<Location, QuarryArm> DATABASE = new HashMap<Location, QuarryArm>();
	public static PluginFile PLUGINFILE = new PluginFile(ViperFusion.plugin, "data/quarryarms");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
				new QuarryArm(ViperFusion.parseLoc(s), UUID.fromString(PLUGINFILE.getString("locs." + s)), false);
			}
		}
		
		ViperFusion.plugin.getServer().getPluginManager().registerEvents(new QuarryArm_Events(), ViperFusion.plugin);
	}
	public static void onDisable() {
		PLUGINFILE.set("locs", null);
		for(QuarryArm i : DATABASE.values()) {
			PLUGINFILE.set("locs." + ViperFusion.locToString(i.loc), i.uuid.toString());
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
			this.uuid = ViperFusion.createOutsideSpawner(loc.getBlock(), Material.ACACIA_FENCE, ViperFusion.TEXID_QUARRYARM);
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