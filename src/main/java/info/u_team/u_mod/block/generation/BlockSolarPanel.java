package info.u_team.u_mod.block.generation;

import info.u_team.u_mod.api.IColored;
import info.u_team.u_mod.block.BlockEnergyGui;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.item.generation.ItemBlockSolarPanel;
import info.u_team.u_mod.tilentity.generation.TileEntitySolarPanel;
import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class BlockSolarPanel extends BlockEnergyGui {
	
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
	
	public BlockSolarPanel(String name) {
		super(name, TileEntitySolarPanel.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(null);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(null, id);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		for (EnumType type : EnumType.values()) {
			setModel(getItem(), type.getMetadata(), getRegistryName());
		}
	}
	
	@Override
	public UItemBlock getItemBlock() {
		return new ItemBlockSolarPanel(this);
	}
	
	/**
	 * From here we'll need to fix some things
	 */
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, EnumType.byMetadata(meta));
	}
	
	// Byte conversion is WRONG! This needs to be fixed.
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return (super.getMetaFromState(state) << 2) + (state.getValue(TYPE).getMetadata() << 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta >> 2).withProperty(TYPE, EnumType.byMetadata(meta % 4));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, TYPE);
	}
	
	public static enum EnumType implements IStringSerializable, IColored {
		TIER1(0, "tier1", 16, 0x00FFFF),
		TIER2(1, "tier2", 256, 0x0000FF),
		TIER3(2, "tier3", 4096, 0x00FF00),
		TIER4(3, "tier4", 65536, 0xFF00FF);
		
		private final int meta;
		private final String name;
		private int energy;
		private int color;
		
		private EnumType(int meta, String name, int energy, int color) {
			this.meta = meta;
			this.name = name;
			this.energy = energy;
			this.color = color;
		}
		
		public int getMetadata() {
			return this.meta;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public int getEnergy() {
			return energy;
		}
		
		public int getColor() {
			return color;
		}
		
		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		
		public static EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		static {
			for (EnumType enumtype : values()) {
				META_LOOKUP[enumtype.getMetadata()] = enumtype;
			}
		}
	}
}
