package info.u_team.u_mod.type;

import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_mod.block.CrateBlock;
import info.u_team.u_mod.tileentity.CrateTileEntity;
import info.u_team.u_mod.util.UExtendedTileEntityType.UExtendedBuilder;
import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import net.minecraft.block.Block;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntityType;

public enum Crate {
	
	SMALL("small", Rarity.COMMON, 5, 3, 44, 24, 8, 82, 176, 164);
	
	private final String name;
	private final Rarity rarity;
	private final int slotWidth, slotHeight;
	private final int slotX, slotY;
	private final int slotPlayerX, slotPlayerY;
	private final int textureSizeX, textureSizeY;
	
	private Crate(String name, Rarity rarity, int slotWidth, int slotHeight, int slotX, int slotY, int slotPlayerX, int slotPlayerY, int textureSizeX, int textureSizeY) {
		this.name = name;
		this.rarity = rarity;
		this.slotWidth = slotWidth;
		this.slotHeight = slotHeight;
		this.slotX = slotX;
		this.slotY = slotY;
		this.slotPlayerX = slotPlayerX;
		this.slotPlayerY = slotPlayerY;
		this.textureSizeX = textureSizeX;
		this.textureSizeY = textureSizeY;
	}
	
	public String getName() {
		return name;
	}
	
	public Rarity getRarity() {
		return rarity;
	}
	
	public int getSlotWidth() {
		return slotWidth;
	}
	
	public int getSlotHeight() {
		return slotHeight;
	}
	
	public int getInventorySize() {
		return slotWidth * slotHeight;
	}
	
	public int getSlotX() {
		return slotX;
	}
	
	public int getSlotY() {
		return slotY;
	}
	
	public int getSlotPlayerX() {
		return slotPlayerX;
	}
	
	public int getSlotPlayerY() {
		return slotPlayerY;
	}
	
	public int getTextureSizeX() {
		return textureSizeX;
	}
	
	public int getTextureSizeY() {
		return textureSizeY;
	}
	
	public static class Crates {
		
		public static final Crates CRATES = new Crates("crate");
		
		private final Map<Crate, Pair<CrateBlock, TileEntityType<CrateTileEntity>>> crates = new HashMap<>();
		
		private final Blocks blocks;
		private final TileEntityTypes tileEntityTypes;
		
		@SuppressWarnings("unchecked")
		public Crates(String name) {
			Stream.of(Crate.values()).forEach(crate -> {
				final TileEntityType<CrateTileEntity>[] array = new TileEntityType[1];
				final CrateBlock block = new CrateBlock(crate, name, () -> array[0]);
				final TileEntityType<CrateTileEntity> tileEntityType = UExtendedBuilder.<CrateTileEntity> create("crate", type -> new CrateTileEntity(type, crate), block).build();
				array[0] = tileEntityType;
				
				crates.put(crate, Pair.of(block, tileEntityType));
			});
			blocks = new Blocks();
			tileEntityTypes = new TileEntityTypes();
		}
		
		public Blocks getBlocks() {
			return blocks;
		}
		
		public TileEntityTypes getTileEntityTypes() {
			return tileEntityTypes;
		}
		
		public class Blocks implements IUArrayRegistryType<Block> {
			
			private final Block[] blocks;
			
			private Blocks() {
				blocks = crates.values().stream().map(Pair::getLeft).toArray(Block[]::new);
			}
			
			@Override
			public Block[] getArray() {
				return blocks;
			}
			
		}
		
		public class TileEntityTypes implements IUArrayRegistryType<TileEntityType<CrateTileEntity>> {
			
			private final TileEntityType<CrateTileEntity>[] tileEntityTypes;
			
			@SuppressWarnings("unchecked")
			private TileEntityTypes() {
				tileEntityTypes = crates.values().stream().map(Pair::getValue).toArray(TileEntityType[]::new);
			}
			
			@Override
			public TileEntityType<CrateTileEntity>[] getArray() {
				return tileEntityTypes;
			}
		}
	}
}
