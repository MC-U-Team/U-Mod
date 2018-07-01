package info.u_team.u_mod.init;

import java.util.*;

import javax.annotation.Nullable;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IUGui;
import info.u_team.u_mod.handler.UGuiHandler;
import info.u_team.u_team_core.container.UContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class UGuis {
	
	private static final List<Class<? extends IUGui>> gui_list = new ArrayList<Class<? extends IUGui>>();
	private static final List<Class<? extends UContainer>> container_list = new ArrayList<Class<? extends UContainer>>();
	
	public static void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(UConstants.MODID, new UGuiHandler());
	}
	
	public static int addGui(Class<? extends IUGui> gui, @Nullable Class<? extends UContainer> container) {
		int id = gui_list.size();
		gui_list.add(gui);
		container_list.add(container);
		return id;
	}
	
	public static Class<? extends IUGui> getGui(int id) {
		return gui_list.get(id);
	}
	
	public static Class<? extends UContainer> getContainer(int id) {
		return container_list.get(id);
	}
	
}
