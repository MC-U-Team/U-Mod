package info.u_team.u_mod.resource;

public enum EnumCraftedResources {
	
	COALCHUNK("coalchunk"),
	CARBON("carbon");
	
	private String name;
	
	private EnumCraftedResources(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
