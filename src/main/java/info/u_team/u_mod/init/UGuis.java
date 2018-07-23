package info.u_team.u_mod.init;

import java.util.*;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IUGui;
import info.u_team.u_mod.handler.UGuiHandler;
import info.u_team.u_team_core.container.UContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.*;

public class UGuis {
	
	private static final List<Class<? extends IUGui>> gui_list = new ArrayList<Class<? extends IUGui>>();
	private static final List<Class<? extends UContainer>> container_list = new ArrayList<Class<? extends UContainer>>();
	
	public static void preinit() {
		NetworkRegistry.INSTANCE.registerGuiHandler(UConstants.MODID, new UGuiHandler());
	}
	
	public static int addContainer(Class<? extends UContainer> container) {
		int id = gui_list.size();
		gui_list.add(null);
		container_list.add(container);
		return id;
	}
	
	@SideOnly(Side.CLIENT)
	public static void addGuiContainer(Class<? extends IUGui> gui, int id) {
		gui_list.set(id, gui);
	}
	
	@Deprecated // Dont use cause it will not set the container list on server side. That WILL
				// CAUSE BUGS. We need to enhance that
	@SideOnly(Side.CLIENT)
	public static int addGuiOnly(Class<? extends IUGui> gui) {
		int id = gui_list.size();
		gui_list.add(gui);
		container_list.add(null);
		return id;
	}
	
	@SideOnly(Side.CLIENT)
	public static Class<? extends IUGui> getGui(int id) {
		return gui_list.get(id);
	}
	
	public static Class<? extends UContainer> getContainer(int id) {
		return container_list.get(id);
	}
	
}
