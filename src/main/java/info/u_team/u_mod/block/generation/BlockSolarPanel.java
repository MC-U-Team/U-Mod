package info.u_team.u_mod.block.generation;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.item.generation.ItemBlockSolarPanel;
import info.u_team.u_mod.tilentity.generation.TileEntitySolarPanel;
import info.u_team.u_team_core.item.UItemBlock;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import info.u_team.u_team_core.util.CustomResourceLocation;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class BlockSolarPanel extends BlockGeneration {
	
	// private int gui;
	
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
	
	public BlockSolarPanel(String name) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "solar_panel_tile"), true, TileEntitySolarPanel.class));
		// gui = UGuis.addGui(Gui.class, Container.class);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		// playerIn.openGui(UConstants.MODID, gui, worldIn, pos.getX(), pos.getY(),
		// pos.getZ());
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
		worldIn.updateComparatorOutputLevel(pos, this);
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public UItemBlock getItemBlock() {
		return new ItemBlockSolarPanel(this);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		for (EnumType enumtype : EnumType.values()) {
			setModel(getItem(), enumtype.getMetadata(), new CustomResourceLocation(getRegistryName(), "_" + enumtype.getName()));
		}
	}
	
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
	
	public static enum EnumType implements IStringSerializable {
		TIER1(0, "tier1", 16),
		TIER2(1, "tier2", 256),
		TIER3(2, "tier3", 4096),
		TIER4(3, "tier4", 65536);
		
		private final int meta;
		private final String name;
		private int energy;
		
		private EnumType(int meta, String name, int energy) {
			this.meta = meta;
			this.name = name;
			this.energy = energy;
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
