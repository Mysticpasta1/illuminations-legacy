package ladysnake.illuminations.client.config;

import com.google.common.base.CaseFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ladysnake.illuminations.client.data.BiomeSettings;
import ladysnake.illuminations.client.enums.EyesInTheDarkSpawnRate;
import ladysnake.illuminations.client.enums.FireflySpawnRate;
import ladysnake.illuminations.client.enums.GlowwormSpawnRate;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import ladysnake.illuminations.client.enums.PlanktonSpawnRate;
import ladysnake.illuminations.client.enums.WillOWispsSpawnRate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

public class Config {
    public static final Path PROPERTIES_PATH;
    private static final Properties config;
    private static HalloweenFeatures halloweenFeatures;
    private static EyesInTheDarkSpawnRate eyesInTheDarkSpawnRate;
    private static WillOWispsSpawnRate willOWispsSpawnRate;
    private static int chorusPetalsSpawnMultiplier;
    private static int density;
    private static boolean fireflySpawnAlways;
    private static boolean fireflySpawnUnderground;
    private static int fireflyWhiteAlpha;
    private static boolean fireflyRainbow;
    private static boolean viewAurasFP;
    private static boolean debugMode;
    private static boolean displayCosmetics;
    private static boolean displayDonationToast;
    private static HashMap<ResourceLocation, BiomeSettings> biomeSettings;

    public Config() {
    }

    public static void load() {
        if (Files.isRegularFile(PROPERTIES_PATH)) {
            try {
                config.load(Files.newBufferedReader(PROPERTIES_PATH));
            } catch (IOException var1) {
                var1.printStackTrace();
            }

            parseProperty("halloween-features", Config::setHalloweenFeatures, DefaultConfig.HALLOWEEN_FEATURES);
            parseProperty("eyes-in-the-dark-spawn-rate", Config::setEyesInTheDarkSpawnRate, DefaultConfig.EYES_IN_THE_DARK_SPAWN_RATE);
            parseProperty("will-o-wisps-spawn-rate", Config::setWillOWispsSpawnRate, DefaultConfig.WILL_O_WISPS_SPAWN_RATE);
            parseProperty("chorus-petal-spawn-multiplier", Config::setChorusPetalsSpawnMultiplier, 1);
            parseProperty("density", Config::setDensity, 100);
            parseProperty("firefly-spawn-always", Config::setFireflySpawnAlways, false);
            parseProperty("firefly-spawn-underground", Config::setFireflySpawnUnderground, false);
            parseProperty("firefly-white-alpha", Config::setFireflyWhiteAlpha, 100);
            parseProperty("firefly-rainbow", Config::setFireflyRainbow, false);
            parseProperty("display-cosmetics", Config::setDisplayCosmetics, true);
            parseProperty("debug-mode", Config::setDebugMode, false);
            parseProperty("view-auras-fp", Config::setViewAurasFP, false);
            parseProperty("display-donation-toast", Config::setDisplayDonationToast, false);
            biomeSettings = new HashMap();
            DefaultConfig.BIOME_SETTINGS.forEach((biome, defaultValue) -> {
                parseProperty(biome.toString(), (x) -> {
                    setBiomeSettings(biome, x);
                }, defaultValue);
            });
            save();
        } else {
            setHalloweenFeatures(DefaultConfig.HALLOWEEN_FEATURES);
            setEyesInTheDarkSpawnRate(DefaultConfig.EYES_IN_THE_DARK_SPAWN_RATE);
            setWillOWispsSpawnRate(DefaultConfig.WILL_O_WISPS_SPAWN_RATE);
            setChorusPetalsSpawnMultiplier(1);
            setDensity(100);
            setFireflySpawnAlways(false);
            setFireflySpawnUnderground(false);
            setFireflyWhiteAlpha(100);
            setDisplayCosmetics(true);
            setViewAurasFP(false);
            setDisplayDonationToast(false);
            biomeSettings = new HashMap();
            DefaultConfig.BIOME_SETTINGS.forEach(Config::setBiomeSettings);
            save();
        }
    }

    private static <T extends Enum<T>> void parseProperty(String property, Consumer<T> setter, T defaultValue) {
        try {
            setter.accept(Enum.valueOf(defaultValue.getDeclaringClass(), config.getProperty(property)));
        } catch (Exception var4) {
            setter.accept(defaultValue);
        }

    }

    private static void parseProperty(String property, Consumer<Boolean> setter, Boolean defaultValue) {
        try {
            setter.accept(Boolean.parseBoolean(config.getProperty(property)));
        } catch (Exception var4) {
            setter.accept(defaultValue);
        }

    }

    private static void parseProperty(String property, Consumer<Integer> setter, Integer defaultValue) {
        try {
            setter.accept(Integer.parseInt(config.getProperty(property)));
        } catch (Exception var4) {
            setter.accept(defaultValue);
        }

    }

    private static void parseProperty(String biome, Consumer<BiomeSettings> setter, BiomeSettings defaultValue) {
        try {
            String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, biome);
            FireflySpawnRate fireflySpawnRate = FireflySpawnRate.valueOf(config.getProperty(name + "-firefly-spawn-rate"));
            GlowwormSpawnRate glowwormSpawnRate = defaultValue.glowwormSpawnRate() == null ? null : GlowwormSpawnRate.valueOf(config.getProperty(name + "-glowworm-spawn-rate"));
            PlanktonSpawnRate planktonSpawnRate = defaultValue.planktonSpawnRate() == null ? null : PlanktonSpawnRate.valueOf(config.getProperty(name + "-plankton-spawn-rate"));
            int fireflyColor = Integer.parseInt(config.getProperty(name + "-firefly-color"), 16);
            setter.accept(new BiomeSettings(fireflySpawnRate, fireflyColor, glowwormSpawnRate, planktonSpawnRate));
        } catch (Exception var8) {
            setter.accept(defaultValue);
        }

    }

    public static void save() {
        try {
            config.store(Files.newBufferedWriter(PROPERTIES_PATH), null);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public static HalloweenFeatures getHalloweenFeatures() {
        return halloweenFeatures;
    }

    public static void setHalloweenFeatures(HalloweenFeatures value) {
        halloweenFeatures = value;
        config.setProperty("halloween-features", value.toString());
    }

    public static EyesInTheDarkSpawnRate getEyesInTheDarkSpawnRate() {
        return eyesInTheDarkSpawnRate;
    }

    public static void setEyesInTheDarkSpawnRate(EyesInTheDarkSpawnRate value) {
        eyesInTheDarkSpawnRate = value;
        config.setProperty("eyes-in-the-dark-spawn-rate", value.name());
    }

    public static WillOWispsSpawnRate getWillOWispsSpawnRate() {
        return willOWispsSpawnRate;
    }

    public static void setWillOWispsSpawnRate(WillOWispsSpawnRate value) {
        willOWispsSpawnRate = value;
        config.setProperty("will-o-wisps-spawn-rate", value.name());
    }

    public static int getChorusPetalsSpawnMultiplier() {
        return chorusPetalsSpawnMultiplier;
    }

    public static void setChorusPetalsSpawnMultiplier(int value) {
        chorusPetalsSpawnMultiplier = value;
        config.setProperty("chorus-petal-spawn-multiplier", Integer.toString(value));
    }

    public static int getDensity() {
        return density;
    }

    public static void setDensity(int value) {
        density = value;
        config.setProperty("density", Integer.toString(value));
    }

    public static boolean doesFireflySpawnAlways() {
        return fireflySpawnAlways;
    }

    public static void setFireflySpawnAlways(boolean value) {
        fireflySpawnAlways = value;
        config.setProperty("firefly-spawn-always", Boolean.toString(value));
    }

    public static boolean doesFireflySpawnUnderground() {
        return fireflySpawnUnderground;
    }

    public static void setFireflySpawnUnderground(boolean value) {
        fireflySpawnUnderground = value;
        config.setProperty("firefly-spawn-underground", Boolean.toString(value));
    }

    public static int getFireflyWhiteAlpha() {
        return fireflyWhiteAlpha;
    }

    public static void setFireflyWhiteAlpha(int value) {
        fireflyWhiteAlpha = value;
        config.setProperty("firefly-white-alpha", Integer.toString(value));
    }

    public static boolean getFireflyRainbow() {
        return fireflyRainbow;
    }

    public static void setFireflyRainbow(boolean value) {
        fireflyRainbow = value;
        config.setProperty("firefly-rainbow", Boolean.toString(value));
    }

    public static boolean getViewAurasFP() {
        return viewAurasFP;
    }

    public static void setViewAurasFP(boolean value) {
        viewAurasFP = value;
        config.setProperty("view-auras-fp", Boolean.toString(value));
    }

    public static boolean shouldDisplayCosmetics() {
        return displayCosmetics;
    }

    public static void setDisplayCosmetics(boolean value) {
        displayCosmetics = value;
        config.setProperty("display-cosmetics", Boolean.toString(value));
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean value) {
        debugMode = value;
        config.setProperty("debug-mode", Boolean.toString(value));
    }

    public static boolean shouldDisplayDonationToast() {
        return displayDonationToast;
    }

    public static void setDisplayDonationToast(boolean value) {
        displayDonationToast = value;
        config.setProperty("display-donation-toast", Boolean.toString(value));
    }

    public static Map<ResourceLocation, BiomeSettings> getBiomeSettings() {
        return biomeSettings;
    }

    public static BiomeSettings getBiomeSettings(ResourceLocation biome) {
        return biomeSettings.get(biome);
    }

    public static void setBiomeSettings(ResourceLocation biome, BiomeSettings settings) {
        biomeSettings.put(biome, settings);
        String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, biome.toString());
        config.setProperty(name + "-firefly-spawn-rate", settings.fireflySpawnRate().name());
        config.setProperty(name + "-firefly-color", Integer.toString(settings.fireflyColor(), 16));
        if (settings.glowwormSpawnRate() != null) {
            config.setProperty(name + "-glowworm-spawn-rate", settings.glowwormSpawnRate().name());
        }

        if (settings.planktonSpawnRate() != null) {
            config.setProperty(name + "-plankton-spawn-rate", settings.planktonSpawnRate().name());
        }

    }

    public static void setFireflySettings(ResourceLocation biome, FireflySpawnRate value) {
        BiomeSettings settings = biomeSettings.get(biome);
        biomeSettings.put(biome, settings.withFireflySpawnRate(value));
        String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, biome.toString());
        config.setProperty(name + "-firefly-spawn-rate", value.name());
    }

    public static void setFireflyColorSettings(ResourceLocation biome, int color) {
        BiomeSettings settings = biomeSettings.get(biome);
        biomeSettings.put(biome, settings.withFireflyColor(color));
        String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, biome.toString());
        config.setProperty(name + "-firefly-color", Integer.toString(color, 16));
    }

    public static void setGlowwormSettings(ResourceLocation biome, GlowwormSpawnRate value) {
        BiomeSettings settings = biomeSettings.get(biome);
        biomeSettings.put(biome, settings.withGlowwormSpawnRate(value));
        String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, biome.toString());
        config.setProperty(name + "-glowworm-spawn-rate", value.name());
    }

    public static void setPlanktonSettings(ResourceLocation biome, PlanktonSpawnRate value) {
        BiomeSettings settings = biomeSettings.get(biome);
        biomeSettings.put(biome, settings.withPlanktonSpawnRate(value));
        String name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, biome.toString());
        config.setProperty(name + "-plankton-spawn-rate", value.name());
    }

    static {
        PROPERTIES_PATH = FMLPaths.CONFIGDIR.get().resolve("illuminations.properties");
        config = new Properties();
    }
}
