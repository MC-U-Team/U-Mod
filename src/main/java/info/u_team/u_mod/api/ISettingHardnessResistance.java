package info.u_team.u_mod.api;

public interface ISettingHardnessResistance {
	
	public float getHardness();
	
	public default float getResistance() {
		return getHardness();
	}
	
}
