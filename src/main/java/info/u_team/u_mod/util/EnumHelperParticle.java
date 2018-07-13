package info.u_team.u_mod.util;

import javax.annotation.Nullable;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;

public class EnumHelperParticle extends EnumHelper {
	
	private static Class<?>[][] clientTypes = { { EnumParticleTypes.class, String.class, int.class, boolean.class, int.class } };
	
	@Nullable
	public static EnumParticleTypes addParticle(String name, int id, boolean ignoreRange) {
		return addEnum(EnumParticleTypes.class, name, name, id, ignoreRange, 0);
	}
	
	@Nullable
	public static EnumParticleTypes addParticle(String name, int id, boolean ignoreRange, int arguments) {
		return addEnum(EnumParticleTypes.class, name, name, id, ignoreRange, arguments);
	}
	
	private static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Object... paramValues) {
		return addEnum(clientTypes, enumType, enumName, paramValues);
	}
	
}
