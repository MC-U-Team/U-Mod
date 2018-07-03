package info.u_team.u_mod.init;

import info.u_team.u_mod.resource.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class UColors {
	
	private static BlockColors blockcolors;
	private static ItemColors itemcolors;
	
	public static void init() {
		Minecraft minecraft = Minecraft.getMinecraft();
		blockcolors = minecraft.getBlockColors();
		itemcolors = minecraft.getItemColors();
		item();
		block();
	}
	
	private static void block() {
		// Resources
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumResources1.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.resource_ore1, UBlocks.resource_block1);
		
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumResources2.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.resource_ore2, UBlocks.resource_block2);
		
		// Alloys
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumAlloys1.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.alloy_block1);
		
	}
	
	private static void item() {
		// Resources
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources1.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.resource_ore1.getItem(), UBlocks.resource_block1.getItem(), UItems.resource_dust1, UItems.resource_nugget1, UItems.resource_ingot1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources2.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.resource_ore2.getItem(), UBlocks.resource_block2.getItem(), UItems.resource_dust2, UItems.resource_nugget2, UItems.resource_ingot2);
		
		// Alloys
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumAlloys1.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.alloy_block1.getItem(), UItems.alloy_nugget1, UItems.alloy_ingot1);
	}
	
}
