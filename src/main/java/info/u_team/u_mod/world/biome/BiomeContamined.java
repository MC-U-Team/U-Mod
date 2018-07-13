package info.u_team.u_mod.world.biome;

import info.u_team.u_mod.world.biome.decorator.BiomeDecoratorContamined;
import info.u_team.u_team_core.biome.UBiome;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeContamined extends UBiome {
	
	public BiomeContamined(String name) {
		super(name, new BiomeProperties("Radioactively Contaminated").setBaseHeight(0.2F).setHeightVariation(0.4F).setRainDisabled().setSnowEnabled().setWaterColor(0x19C619));
		// Add custom grass and dirt
		// Add trees
		// Add dimension portal there
		// Add structure
		// Add mobs
		// In dimension more biomes maybe
		// We'll see
	}
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x15D68F;
	}
	
	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new BiomeDecoratorContamined();
	}
	
}
