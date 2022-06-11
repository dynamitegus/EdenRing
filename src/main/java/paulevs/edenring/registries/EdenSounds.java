package paulevs.edenring.registries;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import paulevs.edenring.EdenRing;

public class EdenSounds {
	public static final SoundEvent BLOCK_ELECTRIC = register("block", "electric");
	public static final SoundEvent MUSIC_COMMON = register("music", "common");
	
	public static final SoundEvent AMBIENCE_BRAINSTORM = register("ambience", "brainstorm");
	public static final SoundEvent AMBIENCE_GOLDEN_FOREST = register("ambience", "golden_forest");
	public static final SoundEvent AMBIENCE_LAKESIDE_DESSERT = register("ambience", "lakeside_dessert");
	public static final SoundEvent AMBIENCE_MYCOTIC_FOREST = register("ambience", "mycotic_forest");
	public static final SoundEvent AMBIENCE_PULSE_FOREST = register("ambience", "pulse_forest");
	public static final SoundEvent AMBIENCE_WIND_VALLEY = register("ambience", "wind_valley");
	
	public static final SoundEvent DISKWING_AMBIENT = register("entity", "diskwing", "ambient");
	public static final SoundEvent DISKWING_DAMAGE = register("entity", "diskwing", "damage");
	
	private static SoundEvent register(String... path) {
		StringBuilder builder = new StringBuilder(EdenRing.MOD_ID);
		for (String part: path) {
			builder.append('.');
			builder.append(part);
		}
		String id = builder.toString();
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(EdenRing.makeID(id)));
	}
	
	public static void init() {}
}
