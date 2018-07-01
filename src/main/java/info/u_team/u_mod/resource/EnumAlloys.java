
package info.u_team.u_mod.resource;

import java.util.*;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.util.NonNullListCustom;
import net.minecraft.util.NonNullList;

public enum EnumAlloys {
	
	ALNICO(BlockType.BLOCK1, "alnico"),
	BERYLLIUMCOPPER(BlockType.BLOCK1, "berylliumcopper"),
	BRASS(BlockType.BLOCK1, "brass"),
	BRONZE(BlockType.BLOCK1, "bronze"),
	CONSTANTAN(BlockType.BLOCK1, "constantan"),
	ELECTRUM(BlockType.BLOCK1, "electrum"),
	FERNICO(BlockType.BLOCK1, "fernico"),
	FERRO(BlockType.BLOCK1, "ferro"),
	INVAR(BlockType.BLOCK1, "invar"),
	NICHROME(BlockType.BLOCK1, "nichrome"),
	PIGIRON(BlockType.BLOCK1, "pigiron"),
	STEEL(BlockType.BLOCK1, "steel");
	
	private BlockType blocktype;
	private String name;
	
	private EnumAlloys(BlockType blocktype, String name) {
		this.blocktype = blocktype;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public BlockType getBlockType() {
		return blocktype;
	}
	
	public static NonNullList<IUMetaType> createBlockList(BlockType type) {
		List<IUMetaType> list = new ArrayList<>();
		for (EnumAlloys entry : values()) {
			if (entry.getBlockType() == type) {
				list.add(new IUMetaType() {
					
					@Override
					public String getName() {
						return entry.getName();
					}
				});
			}
		}
		return new NonNullListCustom<>(list, null);
	}
	
	public static NonNullList<IUMetaType> createItemList() {
		List<IUMetaType> list = new ArrayList<>();
		for (EnumAlloys entry : values()) {
			list.add(new IUMetaType() {
				
				@Override
				public String getName() {
					return entry.getName();
				}
			});
		}
		return new NonNullListCustom<>(list, null);
	}
	
	public static enum BlockType {
		BLOCK1;
	}
	
}
