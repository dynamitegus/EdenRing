package paulevs.edenring.world.biomes;

import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenFeatures;
import ru.bclib.config.IdConfig;
import ru.bclib.world.biomes.BCLBiome;
import ru.bclib.world.biomes.BCLBiomeDef;

public class LakesideDesertBiome extends BCLBiome {
	public LakesideDesertBiome(IdConfig config, ConfiguredSurfaceBuilder surfaceBuilder) {
		super(EdenBiomes.addDefaultFeatures(
			new BCLBiomeDef(EdenRing.makeID("lakeside_desert"))
				.loadConfigValues(config)
				.setCategory(BiomeCategory.DESERT)
				.setSurface(surfaceBuilder)
				.addFeature(EdenFeatures.GRAVEL_FLOOR)
				.addFeature(EdenFeatures.AQUATUS)
				.setGrassColor(246, 222, 173)
				.setFoliageColor(247, 165, 115)
				.setSkyColor(113, 178, 255)
				.setFogColor(237, 235, 203)
				.setFogDensity(2.5F)
		));
	}
}