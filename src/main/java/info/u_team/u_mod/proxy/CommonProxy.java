package info.u_team.u_mod.proxy;

import info.u_team.u_mod.init.UModItemGroups;
import info.u_team.u_team_core.api.IModProxy;

public class CommonProxy implements IModProxy {
	
	@Override
	public void construct() {
	}
	
	@Override
	public void setup() {
	}
	
	@Override
	public void complete() {
		UModItemGroups.complete();
	}
	
}
