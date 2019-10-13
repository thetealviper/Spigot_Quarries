package me.TheTealViper.viperfusion.outsidespawners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import me.TheTealViper.viperfusion.PluginFile;
import me.TheTealViper.viperfusion.ViperFusion;

public class Marker implements Listener{
	public static Map<Location, Marker> DATABASE = new HashMap<Location, Marker>();
	public static PluginFile PLUGINFILE = new PluginFile(ViperFusion.plugin, "data/markers");
	public static void onEnable() {
		if(PLUGINFILE.contains("locs")) {
			for(String s : PLUGINFILE.getConfigurationSection("locs").getKeys(false)) {
				new Marker(ViperFusion.parseLoc(s), UUID.fromString(PLUGINFILE.getString("locs." + s)), false);
			}
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ViperFusion.plugin, new Runnable() {public void run() {
			for(Marker marker : DATABASE.values()) {
				marker.loc.getWorld().spawnParticle(Particle.REDSTONE, marker.loc.clone().add(.5, .75, .5), 0, new DustOptions(Color.AQUA, 1));
			}
		}}, 0, 5);
		
		ViperFusion.plugin.getServer().getPluginManager().registerEvents(new Marker_Events(), ViperFusion.plugin);
	}
	public static void onDisable() {
		PLUGINFILE.set("locs", null);
		for(Marker marker : DATABASE.values()) {
			PLUGINFILE.set("locs." + ViperFusion.locToString(marker.loc), marker.uuid.toString());
		}
		PLUGINFILE.save();
	}
	public static Marker getMarker(Location loc) {
		return DATABASE.get(loc);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Location loc = null;
	public UUID uuid = null;
	
	public Marker(Location loc, UUID uuid, boolean generateNew) {
		this.loc = loc;
		this.uuid = uuid;
		DATABASE.put(loc, this);
		
		if(generateNew)
			this.uuid = ViperFusion.createOutsideSpawner(loc.getBlock(), Material.REDSTONE_TORCH, ViperFusion.TEXID_MARKER);
	}
	
	public void breakMarker() {
		Entity e = Bukkit.getEntity(uuid);
		if(e != null) e.remove();
		DATABASE.remove(loc);
		loc.getBlock().setType(Material.AIR);
		
		loc = null;
		uuid = null;
	}
	
}
