package paulevs.edenring.registries;

import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.biomes.StoneGardenBiome;
import paulevs.edenring.world.structures.EdenFeatures;
import ru.bclib.api.BiomeAPI;
import ru.bclib.config.EntryConfig;
import ru.bclib.config.IdConfig;
import ru.bclib.world.biomes.BCLBiome;
import ru.bclib.world.biomes.BCLBiomeDef;

public class EdenBiomes {
	private static final IdConfig CONFIG = new EntryConfig(EdenRing.MOD_ID, "biomes");
	
	private static final SurfaceBuilderBaseConfiguration CONFIG_GRASS = new SurfaceBuilderBaseConfiguration(
		EdenBlocks.EDEN_GRASS_BLOCK.defaultBlockState(),
		Blocks.DIRT.defaultBlockState(),
		Blocks.DIRT.defaultBlockState()
	);
	private static final ConfiguredSurfaceBuilder DEFAULT_BUILDER = SurfaceBuilder.DEFAULT.configured(CONFIG_GRASS);
	
	public static final BCLBiome STONE_GARDEN = register(new StoneGardenBiome(CONFIG, DEFAULT_BUILDER));
	
	public static void init() {
		CONFIG.saveChanges();
	}
	
	private static BCLBiome register(BCLBiome biome) {
		return BiomeAPI.registerBiome(biome);
	}
	
	public static int correctColor(int color) {
		color *= 1.33F;
		return color > 255 ? 255 : color;
	}
	
	public static BCLBiomeDef addDefaultFeatures(BCLBiomeDef def) {
		def.addFeature(EdenFeatures.SLATE_LAYER);
		def.addFeature(EdenFeatures.CALCITE_LAYER);
		def.addFeature(EdenFeatures.TUFF_LAYER);
		def.addFeature(Decoration.UNDERGROUND_DECORATION, Features.ORE_GRANITE);
		def.addFeature(Decoration.UNDERGROUND_DECORATION, Features.ORE_ANDESITE);
		def.addFeature(Decoration.UNDERGROUND_DECORATION, Features.ORE_DIORITE);
		def.addFeature(EdenFeatures.ORE_MOSSY_COBBLE);
		def.addFeature(EdenFeatures.ORE_COBBLE);
		return def;
	}
}
