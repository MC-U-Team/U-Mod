package info.u_team.u_mod.recipe;

import java.util.*;

import info.u_team.u_mod.api.IMachineRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RecipePulverizer implements IMachineRecipe {
	
	public List<List<ItemStack>> inputs;
	public List<ItemStack> outputs;
	
	private IngredientItemStack input;
	private OutputItemStack firstoutput, secondoutput, thirdoutput;
	
	public RecipePulverizer(IngredientItemStack input, OutputItemStack firstoutput) {
		this(input, firstoutput, new OutputItemStack(ItemStack.EMPTY), new OutputItemStack(ItemStack.EMPTY));
	}
	
	public RecipePulverizer(IngredientItemStack input, OutputItemStack firstoutput, OutputItemStack secondoutput) {
		this(input, firstoutput, secondoutput, new OutputItemStack(ItemStack.EMPTY));
	}
	
	public RecipePulverizer(IngredientItemStack input, OutputItemStack firstoutput, OutputItemStack secondoutput, OutputItemStack thirdoutput) {
		this.input = input;
		this.firstoutput = firstoutput;
		this.secondoutput = secondoutput;
		this.thirdoutput = thirdoutput;
		
		inputs = new ArrayList<>();
		inputs.add(input.getItemStack());
		
		outputs = new ArrayList<>();
		outputs.add(firstoutput.getItemStack());
		outputs.add(secondoutput.getItemStack());
		outputs.add(thirdoutput.getItemStack());
	}
	
	@Override
	public boolean areIngredientsMatching(IInventory inventory) {
		ItemStack stack = inventory.getStackInSlot(0);
		return !stack.isEmpty() && input.containsStackCountMatchOrHigher(stack);
	}
	
	@Override
	public boolean areOutputsMatching(IInventory inventory) {
		ItemStack first = firstoutput.getItemStack();
		ItemStack second = secondoutput.getItemStack();
		ItemStack third = thirdoutput.getItemStack();
		
		ItemStack stack1 = inventory.getStackInSlot(1);
		ItemStack stack2 = inventory.getStackInSlot(2);
		ItemStack stack3 = inventory.getStackInSlot(3);
		
		return ((stack1.isEmpty() || matchStack(first, stack1) && stack1.isStackable() && stack1.getCount() + first.getCount() <= stack1.getMaxStackSize()) && (stack2.isEmpty() || matchStack(second, stack2) && stack2.isStackable() && stack2.getCount() + second.getCount() <= stack2.getMaxStackSize()) && (stack3.isEmpty() || matchStack(third, stack3) && stack3.isStackable() && stack3.getCount() + third.getCount() <= stack3.getMaxStackSize()));
	}
	
	@Override
	public void executeRecipe(IInventory inventory) {
		ItemStack first = firstoutput.getItemStack();
		ItemStack second = secondoutput.getItemStack();
		ItemStack third = thirdoutput.getItemStack();
		
		ItemStack stack0 = inventory.getStackInSlot(0);
		ItemStack stack1 = inventory.getStackInSlot(1);
		ItemStack stack2 = inventory.getStackInSlot(2);
		ItemStack stack3 = inventory.getStackInSlot(3);
		
		if (!stack1.isEmpty()) {
			stack1.grow(first.getCount());
		} else {
			inventory.setInventorySlotContents(1, first.copy());
		}
		
		if (!stack2.isEmpty()) {
			stack2.grow(second.getCount());
		} else {
			inventory.setInventorySlotContents(2, second.copy());
		}
		
		if (!stack3.isEmpty()) {
			stack3.grow(third.getCount());
		} else {
			inventory.setInventorySlotContents(3, third.copy());
		}
		
		if (stack0.getCount() == input.getCount()) {
			inventory.setInventorySlotContents(0, ItemStack.EMPTY);
		} else {
			stack0.shrink(input.getCount());
		}
	}
	
	private boolean matchStack(ItemStack stackA, ItemStack stackB) {
		return ItemStack.areItemsEqual(stackA, stackB) && ItemStack.areItemStackTagsEqual(stackA, stackB);
	}
	
}
