package info.u_team.u_mod.api;

import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_team_core.container.UContainer;

public interface IUGui {
	
	default ContainerBase getContainer() {
		return null;
	}
	
}
