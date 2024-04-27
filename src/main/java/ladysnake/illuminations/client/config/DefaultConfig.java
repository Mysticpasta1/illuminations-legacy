package ladysnake.illuminations.client.config;

import com.google.common.collect.ImmutableMap;
import ladysnake.illuminations.client.data.AuraSettings;
import ladysnake.illuminations.client.data.BiomeSettings;
import ladysnake.illuminations.client.enums.EyesInTheDarkSpawnRate;
import ladysnake.illuminations.client.enums.FireflySpawnRate;
import ladysnake.illuminations.client.enums.GlowwormSpawnRate;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import ladysnake.illuminations.client.enums.PlanktonSpawnRate;
import ladysnake.illuminations.client.enums.WillOWispsSpawnRate;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.HashMap;

public final class DefaultConfig {
    public static HalloweenFeatures HALLOWEEN_FEATURES;
    public static EyesInTheDarkSpawnRate EYES_IN_THE_DARK_SPAWN_RATE;
    public static WillOWispsSpawnRate WILL_O_WISPS_SPAWN_RATE;
    public static FireflySpawnRate FIREFLY_SPAWN_RATE;
    public static GlowwormSpawnRate GLOWWORM_SPAWN_RATE;
    public static PlanktonSpawnRate PLANKTON_SPAWN_RATE;
    public static final int CHORUS_PETALS_SPAWN_MULTIPLIER = 1;
    public static final int DENSITY = 100;
    public static final boolean FIREFLY_SPAWN_ALWAYS = false;
    public static final boolean FIREFLY_SPAWN_UNDERGROUND = false;
    public static final int FIREFLY_WHITE_ALPHA = 100;
    public static final boolean FIREFLY_RAINBOW = false;
    public static final boolean DISPLAY_COSMETICS = true;
    public static final boolean DEBUG_MODE = false;
    public static final boolean VIEW_AURAS_FP = false;
    public static ImmutableMap<String, AuraSettings> AURA_SETTINGS = null;

    private DefaultConfig() {
        throw new UnsupportedOperationException();
    }

    public static AuraSettings getAuraSettings(String aura) {
        return (AuraSettings)AURA_SETTINGS.get(aura);
    }

    static {
        HALLOWEEN_FEATURES = HalloweenFeatures.ENABLE;
        EYES_IN_THE_DARK_SPAWN_RATE = EyesInTheDarkSpawnRate.MEDIUM;
        WILL_O_WISPS_SPAWN_RATE = WillOWispsSpawnRate.MEDIUM;
        FIREFLY_SPAWN_RATE = FireflySpawnRate.HIGH;
        GLOWWORM_SPAWN_RATE = GlowwormSpawnRate.HIGH;
        PLANKTON_SPAWN_RATE = PlanktonSpawnRate.MEDIUM;
        AURA_SETTINGS = ImmutableMap.<String, AuraSettings>builder()
                .put("twilight", new AuraSettings(0.1F, 1))
                .put("ghostly", new AuraSettings(0.1F, 1))
                .put("chorus", new AuraSettings(0.1F, 1))
.put("autumn_leaves", new AuraSettings(0.3F, 1))
.put("sculk_tendrils", new AuraSettings(0.1F, 1))
.put("shadowbringer_soul", new AuraSettings(0.1F, 1))
.put("goldenrod", new AuraSettings(0.4F, 1))
.put("confetti", new AuraSettings(0.1F, 1))
.put("prismatic_confetti", new AuraSettings(0.1F, 1))
.put("prismarine", new AuraSettings(0.1F, 1)).build();
    }
}
