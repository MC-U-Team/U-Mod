package info.u_team.u_mod.config;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre.GenerationType;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid = UConstants.MODID, name = UConstants.MODID + "/generation")
public class UConfigWorldGeneration {
	
	@Comment("If world generation is enabled or not")
	public static boolean enabled = true;
	
	@Comment("Ore generation aluminium")
	public static GeneratableOre oreAluminium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation beryllium")
	public static GeneratableOre oreBeryllium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation cadmium")
	public static GeneratableOre oreCadmium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation chromium")
	public static GeneratableOre oreChromium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation cobalt")
	public static GeneratableOre oreCobalt = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation copper")
	public static GeneratableOre oreCopper = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation gallium")
	public static GeneratableOre oreGallium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation graphite")
	public static GeneratableOre oreGraphite = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation iridium")
	public static GeneratableOre oreIridium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation lead")
	public static GeneratableOre oreLead = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation magnesium")
	public static GeneratableOre oreMagnesium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation molybdenum")
	public static GeneratableOre oreMolybdenum = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation nickel")
	public static GeneratableOre oreNickel = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation palladium" )
	public static GeneratableOre orePalladium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation platinum")
	public static GeneratableOre orePlatinum = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation plutonium")
	public static GeneratableOre orePlutonium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation potassium")
	public static GeneratableOre orePotassium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation silver")
	public static GeneratableOre oreSilver = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation sodium")
	public static GeneratableOre oreSodium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation tantalum")
	public static GeneratableOre oreTantalum = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation tin")
	public static GeneratableOre oreTin = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation tungsten")
	public static GeneratableOre oreTungsten = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation uranium")
	public static GeneratableOre oreUranium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation vanadium")
	public static GeneratableOre oreVanadium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation zinc")
	public static GeneratableOre oreZinc = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	@Comment("Ore generation zirconium")
	public static GeneratableOre oreZirconium = new GeneratableOre(6, 1, 2, GenerationType.MINMAX, 16, 64);
	
	public static class GeneratableOre {
		
		@Comment("If this ore can be generated")
		public boolean enabled;
		
		@Comment("How many times the generation will try to take place per chunk")
		@RangeInt(min = 1, max = 100)
		public int count;
		
		@Comment("How likly is it to generate an ore. Value 1 indicates it will generate an ore at 100%")
		@RangeDouble(min = 0, max = 1)
		public double chance;
		
		@Comment("Average vein size")
		@RangeInt(min = 1, max = 500)
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
