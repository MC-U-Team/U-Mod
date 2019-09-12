package info.u_team.u_mod.util;

import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;

import info.u_team.u_team_core.tileentitytype.UTileEntityType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.*;

public class UExtendedTileEntityType<T extends TileEntity> extends UTileEntityType<T> {
	
	private final Function<TileEntityType<T>, ? extends T> factory;
	
	protected UExtendedTileEntityType(String name, Function<TileEntityType<T>, ? extends T> factory, Set<Block> validBlocks, Type<?> dataFixerType) {
		super(name, null, validBlocks, dataFixerType);
		this.factory = factory;
	}
	
	@Override
	public T create() {
		return factory.apply(this);
	}
	
	public static class UExtendedBuilder<T extends TileEntity> {
		
		private final String name;
		private final Function<TileEntityType<T>, ? extends T> factory;
		private final Set<Block> blocks;
		private Type<?> dataFixerType;
		
		protected UExtendedBuilder(String name, Function<TileEntityType<T>, ? extends T> factory, Set<Block> validBlocks) {
			this.name = name;
			this.factory = factory;
			this.blocks = validBlocks;
		}
		
		public static <T extends TileEntity> UExtendedBuilder<T> create(String name, Function<TileEntityType<T>, ? extends T> factory, Block... validBlocks) {
			return new UExtendedBuilder<>(name, factory, ImmutableSet.copyOf(validBlocks));
		}
		
		public UExtendedBuilder<T> setDataFixerType(Type<?> dataFixerType) {
			this.dataFixerType = dataFixerType;
			return this;
		}
		
		public UExtendedTileEntityType<T> build() {
			return new UExtendedTileEntityType<>(name, factory, blocks, dataFixerType);
		}
	}
}
