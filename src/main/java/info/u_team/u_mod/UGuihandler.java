package info.u_team.u_mod;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import info.u_team.u_mod.container.UContainer;
import info.u_team.u_mod.gui.IUGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class UGuihandler implements IGuiHandler {
	
	private static final ArrayList<Class<? extends IUGui>> _gui_list = new ArrayList<Class<? extends IUGui>>();
	private static final ArrayList<Class<? extends UContainer>> _container_list = new ArrayList<Class<? extends UContainer>>();
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		try {
			return _container_list.get(ID).getConstructor(EntityPlayer.class, World.class, BlockPos.class).newInstance(player, world, new BlockPos(x, y, z));
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
			return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		try {
			Object container = this.getServerGuiElement(ID, player, world, x, y, z);
			if (container == null) {
				return _gui_list.get(ID).getConstructor(EntityPlayer.class, World.class, BlockPos.class).newInstance(player, world, new BlockPos(x, y, z));
			} else {
				return _gui_list.get(ID).getConstructor(UContainer.class).newInstance(container);
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
			return null;
		}
	}
	
	public static int addGui(Class<? extends IUGui> gui, @Nullable Class<? extends UContainer> container) {
		int id = _gui_list.size();
		_gui_list.add(gui);
		_container_list.add(container);
		return id;
	}
	
}
