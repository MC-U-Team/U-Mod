package info.u_team.u_mod.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UGuiScreen extends GuiScreen implements IUGui {
	
	protected EntityPlayer player;
	protected World world;
	protected BlockPos pos;
	
	public UGuiScreen(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.world = world;
		this.pos = pos;
	}
	
}
