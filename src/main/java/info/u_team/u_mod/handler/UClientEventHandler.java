package info.u_team.u_mod.handler;

import info.u_team.u_mod.UConstants;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class UClientEventHandler {
	
	@SubscribeEvent
	public void onConfigChangedEvent(OnConfigChangedEvent event) {
		if (event.getModID().equals(UConstants.MODID)) {
			ConfigManager.sync(UConstants.MODID, Type.INSTANCE);
		}
	}
	
}
