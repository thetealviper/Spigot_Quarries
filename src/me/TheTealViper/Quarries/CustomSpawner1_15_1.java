package me.TheTealViper.Quarries;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import net.minecraft.server.v1_15_R1.TileEntityMobSpawner;

public class CustomSpawner1_15_1 {
	
	public static void createInsideSpawner(Block b, int textureid) {
		b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 1);
		b.setType(Material.SPAWNER);
		CreatureSpawner cs = (CreatureSpawner) b.getState();
		cs.setRequiredPlayerRange(0);
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) ((CraftWorld) b.getLocation().getWorld()).getHandle().getTileEntity(new BlockPosition(b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ()));
		NBTTagCompound spawnerTag = spawner.b();
		
		spawnerTag.setShort("RequiredPlayerRange", (short)0);
		
		NBTTagCompound spawnData = (NBTTagCompound) spawnerTag.get("SpawnData");
		spawnData.setString("id", "armor_stand");
		spawnData.setInt("Invisible", 1);
		spawnData.setInt("Small", 0);
		spawnData.setInt("NoBasePlate", 0);
		spawnData.setInt("ShowArms", 1);
		spawnData.setInt("Marker", 1);
		
		NBTTagList handList = new NBTTagList();
		NBTTagList armorList = new NBTTagList();
		NBTTagCompound mainHand = new NBTTagCompound();
		NBTTagCompound offHand = new NBTTagCompound();
		handList.add(mainHand);
		handList.add(offHand);
		NBTTagCompound helmet = CustomItems1_15_1.getItemNBT(textureid);
		NBTTagCompound chestplate = new NBTTagCompound();
		NBTTagCompound leggings = new NBTTagCompound();
		NBTTagCompound boots = new NBTTagCompound();
		armorList.add(boots);
		armorList.add(leggings);
		armorList.add(chestplate);
		armorList.add(helmet);
		spawnData.set("ArmorItems", armorList);
		
		spawnerTag.set("SpawnData", spawnData);
		spawner.load(spawnerTag);
		b.getState().update();
	}
	
	public static UUID createOutsideSpawner(Block b, Material replacementBlock, int textureid) {
		b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 1);
		b.setType(replacementBlock);
		ArmorStand e = (ArmorStand) b.getWorld().spawnEntity(b.getLocation().clone().add(.5, 0, .5), EntityType.ARMOR_STAND);
		e.setSmall(true);
		e.setArms(false);
		e.setBasePlate(false);
		e.setGravity(false);
		e.setCollidable(false);
		e.setVisible(false);
		e.setMarker(true);
		e.setHelmet(CustomItems1_15_1.getItem(textureid));
		return e.getUniqueId();
	}
	
	public static void big(Player p) {
		Block b = p.getTargetBlock(null, 100);
		b.setType(Material.SPAWNER);
		CreatureSpawner cs = (CreatureSpawner) b.getState();
		cs.setRequiredPlayerRange(0);
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) ((CraftWorld) b.getLocation().getWorld()).getHandle().getTileEntity(new BlockPosition(b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ()));
		NBTTagCompound spawnerTag = spawner.b();
		
		spawnerTag.setShort("RequiredPlayerRange", (short)0);
		
		NBTTagCompound spawnData = (NBTTagCompound) spawnerTag.get("SpawnData");
		spawnData.setString("id", "armor_stand");
		spawnData.setInt("Invisible", 1);
		spawnData.setInt("Small", 0);
		spawnData.setInt("NoBasePlate", 0);
		spawnData.setInt("ShowArms", 1);
		spawnData.setInt("Marker", 1);
		
		NBTTagList handList = new NBTTagList();
		NBTTagList armorList = new NBTTagList();
		NBTTagCompound mainHand = new NBTTagCompound();
		NBTTagCompound offHand = new NBTTagCompound();
		handList.add(mainHand);
		handList.add(offHand);
		NBTTagCompound helmet = CustomItems1_15_1.getItemNBT(Quarries.TEXID_MARKER);
		NBTTagCompound chestplate = new NBTTagCompound();
		NBTTagCompound leggings = new NBTTagCompound();
		NBTTagCompound boots = new NBTTagCompound();
		armorList.add(boots);
		armorList.add(leggings);
		armorList.add(chestplate);
		armorList.add(helmet);
		spawnData.set("ArmorItems", armorList);
		
		spawnerTag.set("SpawnData", spawnData);
		spawner.load(spawnerTag);
		b.getState().update();
	}
	public static void small(Player p) {
		Block b = p.getTargetBlock(null, 100);
		b.setType(Material.SPAWNER);
		CreatureSpawner cs = (CreatureSpawner) b.getState();
		cs.setRequiredPlayerRange(0);
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) ((CraftWorld) b.getLocation().getWorld()).getHandle().getTileEntity(new BlockPosition(b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ()));
		NBTTagCompound spawnerTag = spawner.b();
		
		spawnerTag.setShort("RequiredPlayerRange", (short)0);
		
		NBTTagCompound spawnData = (NBTTagCompound) spawnerTag.get("SpawnData");
		spawnData.setString("id", "armor_stand");
		spawnData.setInt("Invisible", 0);
		spawnData.setInt("Small", 1);
		spawnData.setInt("NoBasePlate", 1);
		spawnData.setInt("ShowArms", 0);
		
		NBTTagList handList = new NBTTagList();
		NBTTagList armorList = new NBTTagList();
		NBTTagCompound mainHand = new NBTTagCompound();
		NBTTagCompound offHand = new NBTTagCompound();
		handList.add(mainHand);
		handList.add(offHand);
		NBTTagCompound helmet = new NBTTagCompound();
		NBTTagCompound chestplate = new NBTTagCompound();
		NBTTagCompound leggings = new NBTTagCompound();
		NBTTagCompound boots = new NBTTagCompound();
		armorList.add(boots);
		armorList.add(leggings);
		armorList.add(chestplate);
		armorList.add(helmet);
		spawnData.set("ArmorItems", armorList);
		
		spawnerTag.set("SpawnData", spawnData);
		spawner.load(spawnerTag);
		b.getState().update();
	}
}
