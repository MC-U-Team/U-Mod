package info.u_team.u_mod.loot;

import com.google.gson.*;

import info.u_team.u_mod.UMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class SetTileEntityNBT extends LootFunction {
	
	private SetTileEntityNBT(ILootCondition[] conditions) {
		super(conditions);
	}
	
	public ItemStack doApply(ItemStack stack, LootContext context) {
		if (context.has(LootParameters.BLOCK_ENTITY)) {
			final CompoundNBT compound = stack.getOrCreateChildTag("BlockEntityTag");
			final TileEntity tileEntity = context.get(LootParameters.BLOCK_ENTITY);
			if (tileEntity instanceof UTileEntity) {
				((UTileEntity) tileEntity).writeNBT(compound);
			} else {
				tileEntity.write(compound);
			}
		}
		return stack;
	}
	
	public static LootFunction.Builder<?> builder() {
		return builder((conditions) -> new SetTileEntityNBT(conditions));
	}
	
	public static class Serializer extends LootFunction.Serializer<SetTileEntityNBT> {
		
		public Serializer() {
			super(new ResourceLocation(UMod.MODID, "set_tileentity_nbt"), SetTileEntityNBT.class);
		}
		
		@Override
		public SetTileEntityNBT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditions) {
			return new SetTileEntityNBT(conditions);
		}
	}
}