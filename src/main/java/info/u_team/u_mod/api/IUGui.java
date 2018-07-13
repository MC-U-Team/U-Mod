package info.u_team.u_mod.api;

import info.u_team.u_mod.container.ContainerBase;

public interface IUGui {
	
	default ContainerBase getContainer() {
		return null;
	}
	
}
