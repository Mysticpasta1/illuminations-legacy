package ladysnake.illuminations.client.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

import ladysnake.illuminations.client.enums.*;
import net.minecraftforge.fml.loading.FMLPaths;

public class Config {
    public static final Path PROPERTIES_PATH;
    private static final Properties config;
    private static HalloweenFeatures halloweenFeatures;
    private static EyesInTheDarkSpawnRate eyesInTheDarkSpawnRate;
    private static WillOWispsSpawnRate willOWispsSpawnRate;
    private static FireflySpawnRate fireflySpawnRate;
    private static GlowwormSpawnRate glowwormSpawnRate;
    private static PlanktonSpawnRate planktonSpawnRate;
    private static int chorusPetalsSpawnMultiplier;
    private static int density;
    private static boolean fireflySpawnAlways;
    private static boolean fireflySpawnUnderground;
    private static int fireflyWhiteAlpha;
    private static boolean fireflyRainbow;
    private static boolean viewAurasFP;
    private static boolean debugMode;
    private static boolean displayCosmetics;

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
            parseProperty("fireflies-spawn-rate", Config::setFireflySpawnRate, DefaultConfig.FIREFLY_SPAWN_RATE);
            parseProperty("glowworm-spawn-rate", Config::setGlowwormSpawnRate, DefaultConfig.GLOWWORM_SPAWN_RATE);
            parseProperty("plankton-spawn-rate", Config::setPlanktonSpawnRate, DefaultConfig.PLANKTON_SPAWN_RATE);
            parseProperty("chorus-petal-spawn-multiplier", Config::setChorusPetalsSpawnMultiplier, 1);
            parseProperty("density", Config::setDensity, 100);
            parseProperty("firefly-spawn-always", Config::setFireflySpawnAlways, false);
            parseProperty("firefly-spawn-underground", Config::setFireflySpawnUnderground, false);
            parseProperty("firefly-white-alpha", Config::setFireflyWhiteAlpha, 100);
            parseProperty("firefly-rainbow", Config::setFireflyRainbow, false);
            parseProperty("display-cosmetics", Config::setDisplayCosmetics, true);
            parseProperty("debug-mode", Config::setDebugMode, false);
            parseProperty("view-auras-fp", Config::setViewAurasFP, false);
            save();
        } else {
            setHalloweenFeatures(DefaultConfig.HALLOWEEN_FEATURES);
            setEyesInTheDarkSpawnRate(DefaultConfig.EYES_IN_THE_DARK_SPAWN_RATE);
            setWillOWispsSpawnRate(DefaultConfig.WILL_O_WISPS_SPAWN_RATE);
            setFireflySpawnRate(DefaultConfig.FIREFLY_SPAWN_RATE);
            setGlowwormSpawnRate(DefaultConfig.GLOWWORM_SPAWN_RATE);
            setPlanktonSpawnRate(DefaultConfig.PLANKTON_SPAWN_RATE);
            setChorusPetalsSpawnMultiplier(1);
            setDensity(100);
            setFireflySpawnAlways(false);
            setFireflySpawnUnderground(false);
            setFireflyWhiteAlpha(100);
            setDisplayCosmetics(true);
            setViewAurasFP(false);
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

    public static FireflySpawnRate getFireflySpawnRate() {
        return fireflySpawnRate;
    }

    public static void setFireflySpawnRate(FireflySpawnRate value) {
        fireflySpawnRate = value;
        config.setProperty("fireflies-spawn-rate", value.name());
    }

    public static GlowwormSpawnRate getGlowwormSpawnRate() {
        return glowwormSpawnRate;
    }

    public static void setGlowwormSpawnRate(GlowwormSpawnRate value) {
        glowwormSpawnRate = value;
        config.setProperty("glowworms-spawn-rate", value.name());
    }

    public static PlanktonSpawnRate getPlanktonSpawnRate() {
        return planktonSpawnRate;
    }

    public static void setPlanktonSpawnRate(PlanktonSpawnRate value) {
        planktonSpawnRate = value;
        config.setProperty("planktons-spawn-rate", value.name());
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

    static {
        PROPERTIES_PATH = FMLPaths.CONFIGDIR.get().resolve("illuminations.properties");
        config = new Properties();
    }
}
