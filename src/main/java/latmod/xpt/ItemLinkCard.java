package latmod.xpt;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.*;

public class ItemLinkCard extends Item
{
	public static final String NBT_TAG = "Coords";
	
	public ItemLinkCard()
	{
		setMaxStackSize(16);
	}
	
	public void loadRecipes()
	{
		if(XPTConfig.enable_crafting)
		GameRegistry.addRecipe(new ShapelessOreRecipe(this, "gemDiamond", "gemEmerald", Items.paper, Items.ender_pearl));
	}
	
	public static boolean hasData(ItemStack is)
	{ return is.hasTagCompound() && is.stackTagCompound.hasKey(NBT_TAG); }
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack is, int pass)
	{ return hasData(is); }
	
	public int getItemStackLimit(ItemStack is)
	{ return hasData(is) ? 1 : 16; }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && ep.isSneaking() && hasData(is))
		{
			is.stackTagCompound.removeTag(NBT_TAG);
			if(is.stackTagCompound.hasNoTags())
				is.setTagCompound(null);
		}
		
		return is;
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List l, boolean b)
	{
		if(hasData(is))
		{
			int[] coords = is.stackTagCompound.getIntArray(NBT_TAG);
			StringBuilder sb = new StringBuilder();
			sb.append("Linked to: ");
			sb.append(coords[0]);
			sb.append(", ");
			sb.append(coords[1]);
			sb.append(", ");
			sb.append(coords[2]);
			sb.append(" @ ");
			
			if(coords[3] == 0)
				sb.append("Overworld");
			else if(coords[3] == 1)
				sb.append("The End");
			else if(coords[3] == -1)
				sb.append("Nether");
			else
			{
				World w = DimensionManager.getWorld(coords[3]);
				if(w != null) sb.append(w.provider.getDimensionName());
				else sb.append("DIM" + coords[3]);
			}
			
			l.add(sb.toString());
		}
	}
}