package info.u_team.u_mod.util;

public class MathUtil {
	
	public static float valueInRange(float min, float max, float value) {
		return Math.min(max, Math.max(min, value));
	}
	
}
