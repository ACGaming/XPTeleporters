package latmod.xpt;
import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class XPTConfig // XPT
{
	public static Configuration config;
	public static int levels_for_1000_blocks;
	public static int levels_for_crossdim;
	public static int cooldown_seconds;
	public static boolean enable_crafting;
	public static boolean unlink_broken;
	
	public static void load(FMLPreInitializationEvent e)
	{
		config = new Configuration(new File(e.getModConfigurationDirectory(), "/LatMod/XPT.cfg"));
		
		levels_for_1000_blocks = config.getInt("levels_for_1000_blocks", "general", 20, 0, 200, "Levels required to teleport in same dimension");
		levels_for_crossdim = config.getInt("levels_for_crossdim", "general", 30, 0, 200, "Levels required to teleport to another dimension");
		cooldown_seconds = config.getInt("cooldown_seconds", "general", 3, 1, 3600, "Teleporter cooldown");
		enable_crafting = config.getBoolean("enable_crafting", "general", true, "Enable crafting recipes");
		unlink_broken = config.getBoolean("unlink_broken", "general", false, "Unlink teleporters when one is broken");
		
		if(config.hasChanged()) config.save();
	}
}