package ladysnake.illuminations.client;

import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.config.DefaultConfig;
import ladysnake.illuminations.client.enums.EyesInTheDarkSpawnRate;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import ladysnake.illuminations.client.enums.WillOWispsSpawnRate;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class IlluminationsModMenuIntegration {
    private ConfigBuilder builder;
    private ConfigEntryBuilder entryBuilder;

    public IlluminationsModMenuIntegration() {
    }

    public Screen getModConfigScreenFactory(Screen parent) {
        Config.load();
        this.builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("title.illuminations.config"));
        this.builder.setSavingRunnable(Config::save);
        this.entryBuilder = this.builder.entryBuilder();
        this.GenerateGeneralSettings();
        return this.builder.build();
    }

    private void GenerateGeneralSettings() {
        ConfigCategory general = this.builder.getOrCreateCategory(Component.translatable("category.illuminations.general"));
        general.addEntry(this.entryBuilder.startEnumSelector(Component.translatable("option.illuminations.halloweenFeatures"), HalloweenFeatures.class, Config.getHalloweenFeatures()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.halloweenFeatures"), Component.translatable("option.tooltip.illuminations.halloweenFeatures.default"), Component.translatable("option.tooltip.illuminations.halloweenFeatures.enable"), Component.translatable("option.tooltip.illuminations.halloweenFeatures.disable"), Component.translatable("option.tooltip.illuminations.halloweenFeatures.always")}).setSaveConsumer(Config::setHalloweenFeatures).setDefaultValue(DefaultConfig.HALLOWEEN_FEATURES).build());
        general.addEntry(this.entryBuilder.startEnumSelector(Component.translatable("option.illuminations.eyesInTheDarkSpawnRate"), EyesInTheDarkSpawnRate.class, Config.getEyesInTheDarkSpawnRate()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.eyesInTheDarkSpawnRate"), Component.translatable("option.tooltip.illuminations.eyesInTheDarkSpawnRate.default"), Component.translatable("option.tooltip.illuminations.eyesInTheDarkSpawnRate.low"), Component.translatable("option.tooltip.illuminations.eyesInTheDarkSpawnRate.medium"), Component.translatable("option.tooltip.illuminations.eyesInTheDarkSpawnRate.high")}).setSaveConsumer(Config::setEyesInTheDarkSpawnRate).setDefaultValue(DefaultConfig.EYES_IN_THE_DARK_SPAWN_RATE).build());
        general.addEntry(this.entryBuilder.startEnumSelector(Component.translatable("option.illuminations.willOWispsSpawnRate"), WillOWispsSpawnRate.class, Config.getWillOWispsSpawnRate()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.willOWispsSpawnRate"), Component.translatable("option.tooltip.illuminations.willOWispsSpawnRate.default"), Component.translatable("option.tooltip.illuminations.willOWispsSpawnRate.disable"), Component.translatable("option.tooltip.illuminations.willOWispsSpawnRate.low"), Component.translatable("option.tooltip.illuminations.willOWispsSpawnRate.medium"), Component.translatable("option.tooltip.illuminations.willOWispsSpawnRate.high")}).setSaveConsumer(Config::setWillOWispsSpawnRate).setDefaultValue(DefaultConfig.WILL_O_WISPS_SPAWN_RATE).build());
        general.addEntry(this.entryBuilder.startIntSlider(Component.translatable("option.illuminations.chorusPetalsSpawnMultiplier"), Config.getChorusPetalsSpawnMultiplier(), 0, 10).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.chorusPetalsSpawnMultiplier"), Component.translatable("option.tooltip.illuminations.chorusPetalsSpawnMultiplier.lowest"), Component.translatable("option.tooltip.illuminations.chorusPetalsSpawnMultiplier.highest")}).setSaveConsumer(Config::setChorusPetalsSpawnMultiplier).setDefaultValue(1).build());
        general.addEntry(this.entryBuilder.startIntSlider(Component.translatable("option.illuminations.density"), Config.getDensity(), 0, 1000).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.density"), Component.translatable("option.tooltip.illuminations.density.lowest"), Component.translatable("option.tooltip.illuminations.density.highest")}).setSaveConsumer(Config::setDensity).setDefaultValue(100).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.fireflySpawnAlways"), Config.doesFireflySpawnAlways()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.fireflySpawnAlways")}).setSaveConsumer(Config::setFireflySpawnAlways).setDefaultValue(false).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.fireflySpawnUnderground"), Config.doesFireflySpawnUnderground()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.fireflySpawnUnderground")}).setSaveConsumer(Config::setFireflySpawnUnderground).setDefaultValue(false).build());
        general.addEntry(this.entryBuilder.startIntSlider(Component.translatable("option.illuminations.fireflyWhiteAlpha"), Config.getFireflyWhiteAlpha(), 0, 100).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.fireflyWhiteAlpha")}).setSaveConsumer(Config::setFireflyWhiteAlpha).setDefaultValue(100).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.fireflyRainbow"), Config.getFireflyRainbow()).setSaveConsumer(Config::setFireflyRainbow).setDefaultValue(false).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.displayCosmetics"), Config.shouldDisplayCosmetics()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.displayCosmetics")}).setSaveConsumer(Config::setDisplayCosmetics).setDefaultValue(true).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.viewAurasFP"), Config.getViewAurasFP()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.viewAurasFP")}).setSaveConsumer(Config::setViewAurasFP).setDefaultValue(false).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.displayDonationToast"), Config.shouldDisplayDonationToast()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.displayDonationToast")}).setSaveConsumer(Config::setDisplayDonationToast).setDefaultValue(false).build());
        general.addEntry(this.entryBuilder.startBooleanToggle(Component.translatable("option.illuminations.debugMode"), Config.isDebugMode()).setTooltip(new Component[]{Component.translatable("option.tooltip.illuminations.debugMode")}).setSaveConsumer(Config::setDebugMode).setDefaultValue(false).build());
    }
}
