package info.u_team.u_mod.api;


public interface ICable {
	
	boolean isOutput();
	
	boolean isInput();
	
	void setID(int id);
	
	int getID();
	
	int rate();
	
}
