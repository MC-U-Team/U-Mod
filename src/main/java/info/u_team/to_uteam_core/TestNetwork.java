package info.u_team.to_uteam_core;

import info.u_team.u_mod.UMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TestNetwork {
	
	public static final String PROTOCOL = "1.15.2-1";
	
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(UMod.MODID, "network"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
	
	public static void construct() {
		NETWORK.registerMessage(0, FluidSetAllContainerMessage.class, FluidSetAllContainerMessage::encode, FluidSetAllContainerMessage::decode, FluidSetAllContainerMessage.Handler::handle);
		NETWORK.registerMessage(1, FluidSetSlotContainerMessage.class, FluidSetSlotContainerMessage::encode, FluidSetSlotContainerMessage::decode, FluidSetSlotContainerMessage.Handler::handle);
	}
	
}
