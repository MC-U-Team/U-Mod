package info.u_team.u_mod.config;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre.GenerationType;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid = UConstants.MODID, name = UConstants.MODID + "/generation")
public class UConfigWorldGeneration {
	
	@Comment("If world generation is enabled or not")
	public static boolean enabled = true;
	
	@Comment("Generation in overworld")
	public static GenerationOverworld overworld = new GenerationOverworld();
	
	public static class GenerationOverworld {
		
		@Comment("If world generation in overworld is enabled or not")
		public boolean enabled = true;
		
		@Comment("Ore generation aluminium")
		public GeneratableOre oreAluminium = new GeneratableOre(25, 0.5, 5, GenerationType.MINMAX, 24, 128);
		@Comment("Ore generation beryllium")
		public GeneratableOre oreBeryllium = new GeneratableOre(6, 0.8, 8, GenerationType.CENTERSPREAD, 30, 10);
		@Comment("Ore generation cadmium")
		public GeneratableOre oreCadmium = new GeneratableOre(1, 0.75, 6, GenerationType.MINMAX, 2, 20);
		@Comment("Ore generation chromium")
		public GeneratableOre oreChromium = new GeneratableOre(8, 1, 5, GenerationType.MINMAX, 0, 48);
		@Comment("Ore generation cobalt")
		public GeneratableOre oreCobalt = new GeneratableOre(2, 0.75, 7, GenerationType.MINMAX, 2, 30);
		@Comment("Ore generation copper")
		public GeneratableOre oreCopper = new GeneratableOre(35, 0.75, 10, GenerationType.MINMAX, 0, 128);
		@Comment("Ore generation gallium")
		public GeneratableOre oreGallium = new GeneratableOre(2, 0.75, 8, GenerationType.MINMAX, 3, 30);
		@Comment("Ore generation graphite")
		public GeneratableOre oreGraphite = new GeneratableOre(8, 1, 20, GenerationType.MINMAX, 0, 128);
		@Comment("Ore generation iridium")
		public GeneratableOre oreIridium = new GeneratableOre(1, 0.5, 15, GenerationType.CENTERSPREAD, 8, 5);
		@Comment("Ore generation lead")
		public GeneratableOre oreLead = new GeneratableOre(15, 1, 6, GenerationType.MINMAX, 0, 48);
		@Comment("Ore generation magnesium")
		public GeneratableOre oreMagnesium = new GeneratableOre(6, 1, 6, GenerationType.MINMAX, 0, 64);
		@Comment("Ore generation molybdenum")
		public GeneratableOre oreMolybdenum = new GeneratableOre(3, 1, 6, GenerationType.MINMAX, 2, 30);
		@Comment("Ore generation nickel")
		public GeneratableOre oreNickel = new GeneratableOre(6, 0.5, 6, GenerationType.MINMAX, 1, 10);
		@Comment("Ore generation palladium")
		public GeneratableOre orePalladium = new GeneratableOre(2, 1, 5, GenerationType.MINMAX, 32, 64);
		@Comment("Ore generation platinum")
		public GeneratableOre orePlatinum = new GeneratableOre(1, 0.95, 6, GenerationType.CENTERSPREAD, 8, 6);
		@Comment("Ore generation plutonium")
		public GeneratableOre orePlutonium = new GeneratableOre(1, 0.25, 5, GenerationType.MINMAX, 5, 50);
		@Comment("Ore generation potassium")
		public GeneratableOre orePotassium = new GeneratableOre(12, 1, 8, GenerationType.MINMAX, 0, 64);
		@Comment("Ore generation silver")
		public GeneratableOre oreSilver = new GeneratableOre(4, 0.85, 9, GenerationType.MINMAX, 0, 32);
		@Comment("Ore generation sodium")
		public GeneratableOre oreSodium = new GeneratableOre(12, 1, 6, GenerationType.MINMAX, 16, 64);
		@Comment("Ore generation tantalum")
		public GeneratableOre oreTantalum = new GeneratableOre(4, 0.75, 8, GenerationType.CENTERSPREAD, 16, 8);
		@Comment("Ore generation tin")
		public GeneratableOre oreTin = new GeneratableOre(30, 0.75, 10, GenerationType.MINMAX, 0, 128);
		@Comment("Ore generation tungsten")
		public GeneratableOre oreTungsten = new GeneratableOre(1, 0.8, 8, GenerationType.MINMAX, 0, 20);
		@Comment("Ore generation uranium")
		public GeneratableOre oreUranium = new GeneratableOre(8, 0.75, 6, GenerationType.MINMAX, 0, 32);
		@Comment("Ore generation vanadium")
		public GeneratableOre oreVanadium = new GeneratableOre(8, 1, 5, GenerationType.MINMAX, 0, 48);
		@Comment("Ore generation zinc")
		public GeneratableOre oreZinc = new GeneratableOre(12, 0.75, 8, GenerationType.MINMAX, 0, 64);
		@Comment("Ore generation zirconium")
		public GeneratableOre oreZirconium = new GeneratableOre(8, 1, 5, GenerationType.MINMAX, 0, 48);
	}
	
	public static class GeneratableOre {
		
		@Comment("If this ore can be generated")
		public boolean enabled;
		
		@Comment("How many times the generation will try to take place per chunk")
		@RangeInt(min = 1, max = 100)
		public int count;
		
		@Comment("How likly is it to generate an ore. Value 1 indicates it will generate an ore at 100%")
		@RangeDouble(min = 0, max = 1)
		public double chance;
		
		@Comment("Maximum vein size (need at least to be 4 cause WorldGenMinable won't generate something under that. Why?)")
		@RangeInt(min = 4, max = 500)
		public int veinsize;
		
		@Comment("The type of the generation")
		public GenerationType type;
		
		@Comment("If type is MINMAX this is the minimal height of generation, else it indicates the center height")
		@RangeInt(min = 0, max = 256)
		public int height1;
		
		@Comment("If type is MINMAX this is the maximum height of generation, else it idicates the spread around the center")
		@RangeInt(min = 0, max = 256)
		public int height2;
		
		public GeneratableOre(int count, double chance, int veinsize, GenerationType type, int height1, int height2) {
			this.enabled = true;
			this.count = count;
			this.chance = chance;
			this.veinsize = veinsize;
			this.type = type;
			this.height1 = height1;
			this.height2 = height2;
		}
		
		public static enum GenerationType {
			MINMAX,
			CENTERSPREAD;
		}
		
	}
	
}
