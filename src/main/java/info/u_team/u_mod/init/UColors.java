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
		// Ore
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumResources1.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.ore1);
		
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumResources2.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.ore2);
		
		// Block
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumResources1.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.block1);
		
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			return EnumResources2.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
		}, UBlocks.block2);
		
	}
	
	private static void item() {
		// Ore
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources1.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.ore1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources2.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.ore2);
		
		// Block
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources1.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.block1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources2.byMetadata(stack.getMetadata()).getColor();
		}, UBlocks.block2);
		
		// Dust
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources1.byMetadata(stack.getMetadata()).getColor();
		}, UItems.dust1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources2.byMetadata(stack.getMetadata()).getColor();
		}, UItems.dust2);
		
		// Nugget
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources1.byMetadata(stack.getMetadata()).getColor();
		}, UItems.nugget1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources2.byMetadata(stack.getMetadata()).getColor();
		}, UItems.nugget2);
		
		// Ingot
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources1.byMetadata(stack.getMetadata()).getColor();
		}, UItems.ingot1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			return EnumResources2.byMetadata(stack.getMetadata()).getColor();
		}, UItems.ingot2);
		
	}
	
}
