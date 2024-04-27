package ladysnake.illuminations.client;

import ladysnake.illuminations.client.particle.ChorusPetalParticle;
import ladysnake.illuminations.client.particle.EyesParticle;
import ladysnake.illuminations.client.particle.FireflyParticle;
import ladysnake.illuminations.client.particle.GlowwormParticle;
import ladysnake.illuminations.client.particle.PlanktonParticle;
import ladysnake.illuminations.client.particle.PoltergeistParticle;
import ladysnake.illuminations.client.particle.PrismarineCrystalParticle;
import ladysnake.illuminations.client.particle.PumpkinSpiritParticle;
import ladysnake.illuminations.client.particle.WillOWispParticle;
import ladysnake.illuminations.client.particle.WispTrailParticle;
import ladysnake.illuminations.client.particle.aura.AutumnLeavesParticle;
import ladysnake.illuminations.client.particle.aura.ChorusAuraParticle;
import ladysnake.illuminations.client.particle.aura.ConfettiParticle;
import ladysnake.illuminations.client.particle.aura.GhostlyAuraParticle;
import ladysnake.illuminations.client.particle.aura.GoldenrodAuraParticle;
import ladysnake.illuminations.client.particle.aura.PrismarineAuraParticle;
import ladysnake.illuminations.client.particle.aura.PrismaticConfettiParticle;
import ladysnake.illuminations.client.particle.aura.SculkTendrilParticle;
import ladysnake.illuminations.client.particle.aura.ShadowbringerParticle;
import ladysnake.illuminations.client.particle.aura.TwilightFireflyParticle;
import ladysnake.illuminations.client.particle.pet.JackoParticle;
import ladysnake.illuminations.client.particle.pet.PetParticle;
import ladysnake.illuminations.client.particle.pet.PlayerLanternParticle;
import ladysnake.illuminations.client.particle.pet.PlayerWispParticle;
import ladysnake.illuminations.client.particle.pet.PrideHeartParticle;
import ladysnake.illuminations.client.render.entity.model.hat.CrownModel;
import ladysnake.illuminations.client.render.entity.model.hat.HaloModel;
import ladysnake.illuminations.client.render.entity.model.hat.HornsModel;
import ladysnake.illuminations.client.render.entity.model.hat.TiaraModel;
import ladysnake.illuminations.client.render.entity.model.hat.VoidheartTiaraModel;
import ladysnake.illuminations.client.render.entity.model.hat.WreathModel;
import ladysnake.illuminations.client.render.entity.model.pet.LanternModel;
import ladysnake.illuminations.client.render.entity.model.pet.PrideHeartModel;
import ladysnake.illuminations.client.render.entity.model.pet.WillOWispModel;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
    value = {Dist.CLIENT},
    modid = "illuminations",
    bus = Bus.MOD
)
public class EventHandler {
    public EventHandler() {
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CrownModel.MODEL_LAYER, CrownModel::getTexturedModelData);
        event.registerLayerDefinition(HornsModel.MODEL_LAYER, HornsModel::getTexturedModelData);
        event.registerLayerDefinition(HaloModel.MODEL_LAYER, HaloModel::getTexturedModelData);
        event.registerLayerDefinition(TiaraModel.MODEL_LAYER, TiaraModel::getTexturedModelData);
        event.registerLayerDefinition(VoidheartTiaraModel.MODEL_LAYER, VoidheartTiaraModel::getTexturedModelData);
        event.registerLayerDefinition(WreathModel.MODEL_LAYER, WreathModel::getTexturedModelData);
        event.registerLayerDefinition(WillOWispModel.MODEL_LAYER, WillOWispModel::getTexturedModelData);
        event.registerLayerDefinition(LanternModel.MODEL_LAYER, LanternModel::getTexturedModelData);
        event.registerLayerDefinition(PrideHeartModel.MODEL_LAYER, PrideHeartModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.register((ParticleType)Illuminations.FIREFLY.get(), FireflyParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.GLOWWORM.get(), GlowwormParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.PLANKTON.get(), PlanktonParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.EYES.get(), EyesParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.CHORUS_PETAL.get(), ChorusPetalParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.WILL_O_WISP.get(), (fabricSpriteProvider) -> {
            return new WillOWispParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/will_o_wisp.png"), 1.0F, 1.0F, 1.0F, -0.1F, -0.01F, 0.0F);
        });
        event.register((ParticleType)Illuminations.WISP_TRAIL.get(), WispTrailParticle.Factory::new);
        event.register((ParticleType)Illuminations.PUMPKIN_SPIRIT.get(), (fabricSpriteProvider) -> {
            return new PumpkinSpiritParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/pumpkin_spirit.png"), 1.0F, 0.95F, 0.0F, 0.0F, -0.03F, 0.0F);
        });
        event.register((ParticleType)Illuminations.POLTERGEIST.get(), (fabricSpriteProvider) -> {
            return new PoltergeistParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/poltergeist.png"), 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        });
        event.register((ParticleType)Illuminations.PRISMARINE_CRYSTAL.get(), PrismarineCrystalParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.TWILIGHT_AURA.get(), TwilightFireflyParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.GHOSTLY_AURA.get(), GhostlyAuraParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.CHORUS_AURA.get(), ChorusAuraParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.AUTUMN_LEAVES_AURA.get(), AutumnLeavesParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.SCULK_TENDRIL_AURA.get(), SculkTendrilParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.SHADOWBRINGER_AURA.get(), ShadowbringerParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.GOLDENROD_AURA.get(), GoldenrodAuraParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.CONFETTI_AURA.get(), ConfettiParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.PRISMATIC_CONFETTI_AURA.get(), PrismaticConfettiParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.PRISMARINE_AURA.get(), PrismarineAuraParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.GAY_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/gay_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.TRANS_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/trans_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.LESBIAN_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/lesbian_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.BI_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/bi_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.ACE_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/ace_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.NB_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/nb_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.INTERSEX_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/intersex_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.ARO_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/aro_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.PAN_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/pan_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.AGENDER_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/agender_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.GENDERFLUID_PRIDE_PET.get(), (fabricSpriteProvider) -> {
            return new PrideHeartParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/genderfluid_pride_heart.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.WILL_O_WISP_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerWispParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/will_o_wisp.png"), 1.0F, 1.0F, 1.0F, -0.1F, -0.01F, 0.0F);
        });
        event.register((ParticleType)Illuminations.GOLDEN_WILL_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerWispParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/golden_will.png"), 1.0F, 0.3F, 1.0F, -0.05F, -0.01F, 0.0F);
        });
        event.register((ParticleType)Illuminations.FOUNDING_SKULL_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerWispParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/founding_skull.png"), 1.0F, 0.0F, 0.25F, -0.03F, 0.0F, -0.01F);
        });
        event.register((ParticleType)Illuminations.DISSOLUTION_WISP_PET.get(), PetParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.JACKO_PET.get(), JackoParticle.DefaultFactory::new);
        event.register((ParticleType)Illuminations.PUMPKIN_SPIRIT_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerWispParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/pumpkin_spirit.png"), 1.0F, 0.95F, 0.0F, 0.0F, -0.03F, 0.0F);
        });
        event.register((ParticleType)Illuminations.POLTERGEIST_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerWispParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/poltergeist.png"), 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        });
        event.register((ParticleType)Illuminations.LANTERN_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerLanternParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/lantern.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.SOUL_LANTERN_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerLanternParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/soul_lantern.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.CRYING_LANTERN_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerLanternParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/crying_lantern.png"), 1.0F, 1.0F, 1.0F);
        });
        event.register((ParticleType)Illuminations.SOOTHING_LANTERN_PET.get(), (fabricSpriteProvider) -> {
            return new PlayerLanternParticle.DefaultFactory(fabricSpriteProvider, new ResourceLocation("illuminations", "textures/entity/soothing_lantern.png"), 1.0F, 1.0F, 1.0F);
        });
    }
}
