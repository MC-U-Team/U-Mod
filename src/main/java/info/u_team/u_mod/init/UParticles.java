package info.u_team.u_mod.init;

import info.u_team.u_mod.particle.*;
import info.u_team.u_mod.util.EnumHelperParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;

public class UParticles {
	
	public static final EnumParticleTypes oredigging = EnumHelperParticle.addParticle("oredigging", 20500, false, 1);
	public static final EnumParticleTypes oredust = EnumHelperParticle.addParticle("oredust", 20501, false, 1);
	
	public static void init() {
		Minecraft minecraft = Minecraft.getMinecraft();
		minecraft.effectRenderer.registerParticle(oredigging.getParticleID(), new ParticleOreDigging.Factory());
		minecraft.effectRenderer.registerParticle(oredust.getParticleID(), new ParticleOreDust.Factory());
	}
	
}
