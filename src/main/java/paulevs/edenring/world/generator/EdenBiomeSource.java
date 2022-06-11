package paulevs.edenring.world.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import org.betterx.bclib.api.v2.generator.BiomePicker;
import org.betterx.bclib.api.v2.generator.map.hex.HexBiomeMap;
import org.betterx.bclib.interfaces.BiomeMap;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBiomes;

import java.util.stream.Collectors;

public class EdenBiomeSource extends BiomeSource {
	public static final Codec<EdenBiomeSource> CODEC = RecordCodecBuilder.create(
		(instance) -> instance.group(
			RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((theEndBiomeSource) -> null)
		).apply(instance, instance.stable(EdenBiomeSource::new))
	);
	
	private final Registry<Biome> biomeRegistry;
	private BiomePicker pickerLand;
	private BiomePicker pickerVoid;
	private BiomePicker pickerCave;
	private BiomeMap mapLand;
	private BiomeMap mapVoid;
	private BiomeMap mapCave;
	
	public EdenBiomeSource(Registry<Biome> biomeRegistry) {
		super(biomeRegistry
			.entrySet()
			.stream()
			.filter(entry -> entry.getKey().location().getNamespace().equals(EdenRing.MOD_ID))
			.map(entry -> biomeRegistry.getOrCreateHolder(entry.getKey()).get().left().get())
			.collect(Collectors.toList()));
		this.biomeRegistry = biomeRegistry;
		
		if (pickerLand == null) {
			pickerLand = new BiomePicker(biomeRegistry);
			EdenBiomes.BIOMES_LAND.forEach(biome -> pickerLand.addBiome(biome));
			pickerLand.rebuild();
			
			pickerVoid = new BiomePicker(biomeRegistry);
			EdenBiomes.BIOMES_VOID.forEach(biome -> pickerVoid.addBiome(biome));
			pickerVoid.rebuild();
			
			pickerCave = new BiomePicker(biomeRegistry);
			EdenBiomes.BIOMES_CAVE.forEach(biome -> pickerCave.addBiome(biome));
			pickerCave.rebuild();
		}
		
		mapLand = new HexBiomeMap(0, GeneratorOptions.biomeSizeLand, pickerLand);
		mapVoid = new HexBiomeMap(0, GeneratorOptions.biomeSizeVoid, pickerVoid);
		mapCave = new HexBiomeMap(0, GeneratorOptions.biomeSizeCave, pickerCave);
	}
	
	@Override
	protected Codec<? extends BiomeSource> codec() {
		return CODEC;
	}
	
	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Sampler sampler) {
		cleanCache(x, z);
		
		int px = (x << 2) | 2;
		int py = (y << 2) | 2;
		int pz = (z << 2) | 2;
		
		float[] data = new float[2];
		TerrainGenerator generator = MultiThreadGenerator.getTerrainGenerator();
		generator.fillTerrainDensity(data, new BlockPos(px, py, pz), 4.0, 8.0);
		
		if (isLand(data)) {
			if (isCave(data)) {
				return mapCave.getBiome(px, 0, pz).biome;
			}
			return mapLand.getBiome(px, 0, pz).biome;
		}
		return mapVoid.getBiome(px, 0, pz).biome;
	}
	
	public void setSeed(long seed) {
		mapLand = new HexBiomeMap(seed, GeneratorOptions.biomeSizeLand, pickerLand);
		mapVoid = new HexBiomeMap(seed, GeneratorOptions.biomeSizeVoid, pickerVoid);
		mapCave = new HexBiomeMap(seed, GeneratorOptions.biomeSizeCave, pickerCave);
	}
	
	private void cleanCache(int x, int z) {
		if ((x & 63) == 0 && (z & 63) == 0) {
			mapLand.clearCache();
			mapVoid.clearCache();
			mapCave.clearCache();
		}
	}
	
	private boolean isLand(float[] data) {
		for (byte py = 0; py < data.length; py++) {
			if (data[py] > -0.3F) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCave(float[] data) {
		for (byte i = 0; i < data.length; i++) {
			if (data[i] < 0) return false;
		}
		return true;
	}
}
