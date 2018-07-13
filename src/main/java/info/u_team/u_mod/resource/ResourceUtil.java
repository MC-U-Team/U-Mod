package info.u_team.u_mod.resource;

import java.util.HashMap;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import info.u_team.u_mod.api.IResource;
import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.item.UItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ResourceUtil {
	
	private static final HashMap<IResource, Resource> map = new HashMap<>();
	
	static {
		for (EnumResources1 resource : EnumResources1.VALUES) {
			map.put(resource, new Resource(resource));
		}
		for (EnumResources2 resource : EnumResources2.VALUES) {
			map.put(resource, new Resource(resource));
		}
	}
	
	public static Resource get(IResource resource) {
		return map.get(resource);
	}
	
	public static void iterate(Consumer<Resource> consumer) {
		map.values().forEach(consumer);
	}
	
	public static class Resource {
		
		private IResource resource;
		
		public Resource(IResource resource) {
			this.resource = resource;
		}
		
		public IResource getResource() {
			return resource;
		}
		
		public String getName() {
			return getResource().getName();
		}
		
		public String getCapitalizedName() {
			return StringUtils.capitalize(getName());
		}
		
		public int getMetadata() {
			return getResource().getMetadata();
		}
		
		public UBlock getOreBlock() {
			return getResource().getEnumCount() == 1 ? UBlocks.resource_ore1 : UBlocks.resource_ore2;
		}
		
		public UBlock getBlockBlock() {
			return getResource().getEnumCount() == 1 ? UBlocks.resource_block1 : UBlocks.resource_block2;
		}
		
		public UItem getDustItem() {
			return getResource().getEnumCount() == 1 ? UItems.resource_dust1 : UItems.resource_dust2;
		}
		
		public UItem getNuggetItem() {
			return getResource().getEnumCount() == 1 ? UItems.resource_nugget1 : UItems.resource_nugget2;
		}
		
		public UItem getIngotItem() {
			return getResource().getEnumCount() == 1 ? UItems.resource_ingot1 : UItems.resource_ingot2;
		}
		
		public IBlockState getOreBlockState() {
			return getResource().getBlockState(getOreBlock());
		}
		
		public IBlockState getBlockBlockState() {
			return getResource().getBlockState(getBlockBlock());
		}
		
		public ItemStack getOreItemStack() {
			return getOreItemStack(1);
		}
		
		public ItemStack getOreItemStack(int count) {
			return new ItemStack(getOreBlock(), count, resource.getMetadata());
		}
		
		public ItemStack getBlockItemStack() {
			return getBlockItemStack(1);
		}
		
		public ItemStack getBlockItemStack(int count) {
			return new ItemStack(getBlockBlock(), count, resource.getMetadata());
		}
		
		public ItemStack getDustItemStack() {
			return getDustItemStack(1);
		}
		
		public ItemStack getDustItemStack(int count) {
			return new ItemStack(getDustItem(), count, resource.getMetadata());
		}
		
		public ItemStack getNuggetItemStack() {
			return getNuggetItemStack(1);
		}
		
		public ItemStack getNuggetItemStack(int count) {
			return new ItemStack(getNuggetItem(), count, resource.getMetadata());
		}
		
		public ItemStack getIngotItemStack() {
			return getIngotItemStack(1);
		}
		
		public ItemStack getIngotItemStack(int count) {
			return new ItemStack(getIngotItem(), count, resource.getMetadata());
		}
		
	}
}
