package info.u_team.u_mod.data.provider;

import java.util.function.Consumer;

import info.u_team.u_team_core.data.*;
import net.minecraft.data.IFinishedRecipe;

public class UModRecipesProvider extends CommonRecipesProvider {
	
	public UModRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		// Add resource recipes
	}
	
}
