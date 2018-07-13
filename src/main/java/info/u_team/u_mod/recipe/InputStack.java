package info.u_team.u_mod.recipe;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InputStack {
	
	private ArrayList<ItemStack> stacks = new ArrayList<>();
	
	public InputStack(ItemStack stack) {
		stacks.add(stack);
	}
	
	public InputStack(String oredict) {
		stacks.addAll(OreDictionary.getOres(oredict));
	}
	
	public ArrayList<ItemStack> getStacks() {
		return stacks;
	}
	
	public boolean contains(ItemStack input) {
		return stacks.stream().anyMatch(stack -> input.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(input, stack));
	}
	
	public int getCount() {
		return 1; // TODO new things
	}
	
}
