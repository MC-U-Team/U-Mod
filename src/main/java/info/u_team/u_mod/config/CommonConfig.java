package info.u_team.u_mod.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class CommonConfig {
	
	public static final ForgeConfigSpec config;
	private static final CommonConfig instance;
	
	static {
		Pair<CommonConfig, ForgeConfigSpec> pair = new Builder().configure(CommonConfig::new);
		config = pair.getRight();
		instance = pair.getLeft();
	}
	
	public static CommonConfig getInstance() {
		return instance;
	}
	
	public final BooleanValue discordRichPresence;
	
	private CommonConfig(Builder builder) {
		builder.comment("Client configuration settings").push("client");
		discordRichPresence = builder.comment("If you have discord installed it will show your some details about your game as rich presence").translation("uteamcore:configgui.discordRichPresence").define("discordRichPresence", true);
		builder.pop();
	}
	
}
