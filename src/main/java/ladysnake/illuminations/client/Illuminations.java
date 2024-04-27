//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ladysnake.illuminations.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.config.DefaultConfig;
import ladysnake.illuminations.client.data.AuraData;
import ladysnake.illuminations.client.data.IlluminationData;
import ladysnake.illuminations.client.data.OverheadData;
import ladysnake.illuminations.client.data.PlayerCosmeticData;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import ladysnake.illuminations.client.particle.WispTrailParticleEffect;
import ladysnake.illuminations.client.render.entity.model.hat.CrownModel;
import ladysnake.illuminations.client.render.entity.model.hat.HaloModel;
import ladysnake.illuminations.client.render.entity.model.hat.HornsModel;
import ladysnake.illuminations.client.render.entity.model.hat.TiaraModel;
import ladysnake.illuminations.client.render.entity.model.hat.VoidheartTiaraModel;
import ladysnake.illuminations.client.render.entity.model.hat.WreathModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

@Mod("illuminations")
public class Illuminations {
    public static final String MODID = "illuminations";
    public static final Logger logger = LogManager.getLogger("Illuminations");
    public static final int EYES_VANISHING_DISTANCE = 5;
    public static final Gson COSMETICS_GSON = (new GsonBuilder()).registerTypeAdapter(PlayerCosmeticData.class, new PlayerCosmeticDataParser()).create();
    public static final BiPredicate<Level, BlockPos> FIREFLY_LOCATION_PREDICATE = (world, blockPos) -> {
        Block block = world.getBlockState(blockPos).getBlock();
        return world.dimensionType().hasFixedTime() ? block == Blocks.AIR || block == Blocks.VOID_AIR : block == Blocks.AIR && (Config.doesFireflySpawnAlways() || isNightTime(world)) && (Config.doesFireflySpawnUnderground() || world.getBlockState(blockPos).is(Blocks.AIR));
    };
    public static final BiPredicate<Level, BlockPos> GLOWWORM_LOCATION_PREDICATE = (world, blockPos) -> {
        return world.getBlockState(blockPos).getBlock() == Blocks.CAVE_AIR;
    };
    public static final BiPredicate<Level, BlockPos> PLANKTON_LOCATION_PREDICATE = (world, blockPos) -> {
        return world.getBlockState(blockPos).getFluidState().is(FluidTags.WATER) && world.getMaxLocalRawBrightness(blockPos) < 2;
    };
    public static final BiPredicate<Level, BlockPos> EYES_LOCATION_PREDICATE = (world, blockPos) -> {
        return (Config.getHalloweenFeatures() == HalloweenFeatures.ENABLE && LocalDate.now().getMonth() == Month.OCTOBER || Config.getHalloweenFeatures() == HalloweenFeatures.ALWAYS) && (world.getBlockState(blockPos).getBlock() == Blocks.AIR || world.getBlockState(blockPos).getBlock() == Blocks.CAVE_AIR) && world.getMaxLocalRawBrightness(blockPos) <= 0 && world.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5.0, false) == null;
    };
    public static final BiPredicate<Level, BlockPos> WISP_LOCATION_PREDICATE = (world, blockPos) -> {
        return world.getBlockState(blockPos).is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
    };
    static final Type COSMETIC_SELECT_TYPE = (new TypeToken<Map<UUID, PlayerCosmeticData>>() {
    }).getType();
    private static final String COSMETICS_URL = "https://doctor4t.uuid.gg/illuminations-data";
    public static Map<String, AuraData> AURAS_DATA;
    public static Map<String, SimpleParticleType> PETS_DATA;
    public static Map<String, OverheadData> OVERHEADS_DATA;
    public static RegistryObject<SimpleParticleType> FIREFLY;
    public static RegistryObject<SimpleParticleType> GLOWWORM;
    public static RegistryObject<SimpleParticleType> PLANKTON;
    public static RegistryObject<SimpleParticleType> EYES;
    public static RegistryObject<SimpleParticleType> CHORUS_PETAL;
    public static RegistryObject<SimpleParticleType> WILL_O_WISP;
    public static RegistryObject<ParticleType<WispTrailParticleEffect>> WISP_TRAIL;
    public static RegistryObject<SimpleParticleType> PUMPKIN_SPIRIT;
    public static RegistryObject<SimpleParticleType> POLTERGEIST;
    public static RegistryObject<SimpleParticleType> PRISMARINE_CRYSTAL;
    public static RegistryObject<SimpleParticleType> TWILIGHT_AURA;
    public static RegistryObject<SimpleParticleType> GHOSTLY_AURA;
    public static RegistryObject<SimpleParticleType> CHORUS_AURA;
    public static RegistryObject<SimpleParticleType> AUTUMN_LEAVES_AURA;
    public static RegistryObject<SimpleParticleType> SCULK_TENDRIL_AURA;
    public static RegistryObject<SimpleParticleType> SHADOWBRINGER_AURA;
    public static RegistryObject<SimpleParticleType> GOLDENROD_AURA;
    public static RegistryObject<SimpleParticleType> CONFETTI_AURA;
    public static RegistryObject<SimpleParticleType> PRISMATIC_CONFETTI_AURA;
    public static RegistryObject<SimpleParticleType> PRISMARINE_AURA;
    public static RegistryObject<SimpleParticleType> PRIDE_PET;
    public static RegistryObject<SimpleParticleType> GAY_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> TRANS_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> JACKO_PET;
    public static RegistryObject<SimpleParticleType> LESBIAN_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> BI_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> ACE_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> NB_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> INTERSEX_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> ARO_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> PAN_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> AGENDER_PRIDE_PET;
    public static RegistryObject<SimpleParticleType> WILL_O_WISP_PET;
    public static RegistryObject<SimpleParticleType> GOLDEN_WILL_PET;
    public static RegistryObject<SimpleParticleType> FOUNDING_SKULL_PET;
    public static RegistryObject<SimpleParticleType> DISSOLUTION_WISP_PET;
    public static RegistryObject<SimpleParticleType> PUMPKIN_SPIRIT_PET;
    public static RegistryObject<SimpleParticleType> POLTERGEIST_PET;
    public static RegistryObject<SimpleParticleType> LANTERN_PET;
    public static RegistryObject<SimpleParticleType> SOUL_LANTERN_PET;
    public static RegistryObject<SimpleParticleType> CRYING_LANTERN_PET;
    public static RegistryObject<SimpleParticleType> SOOTHING_LANTERN_PET;
    public static RegistryObject<SimpleParticleType> GENDERFLUID_PRIDE_PET;
    public static Map<ResourceLocation, Set<IlluminationData>> ILLUMINATIONS_BIOME_CATEGORIES;
    public static Map<ResourceLocation, Set<IlluminationData>> ILLUMINATIONS_BIOMES;
    private static Map<UUID, PlayerCosmeticData> PLAYER_COSMETICS = Collections.emptyMap();

    public static @Nullable PlayerCosmeticData getCosmeticData(Player player) {
        return PLAYER_COSMETICS.get(player.getUUID());
    }

    public static @Nullable PlayerCosmeticData getCosmeticData(UUID uuid) {
        return PLAYER_COSMETICS.get(uuid);
    }

    public static void loadPlayerCosmetics() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Reader reader = new InputStreamReader((new URL("https://doctor4t.uuid.gg/illuminations-data")).openStream());

                Map var1;
                try {
                    if (Config.isDebugMode()) {
                        logger.log(org.apache.logging.log4j.Level.INFO, "Retrieving Illuminations cosmetics from the dashboard...");
                    }

                    var1 = COSMETICS_GSON.fromJson(reader, COSMETIC_SELECT_TYPE);
                } catch (Throwable var4) {
                    try {
                        reader.close();
                    } catch (Throwable var3) {
                        var4.addSuppressed(var3);
                    }

                    throw var4;
                }

                reader.close();
                return var1;
            } catch (MalformedURLException var5) {
                if (Config.isDebugMode()) {
                    logger.log(org.apache.logging.log4j.Level.ERROR, "Could not get player cosmetics because of malformed URL: " + var5.getMessage());
                }
            } catch (IOException var6) {
                if (Config.isDebugMode()) {
                    logger.log(org.apache.logging.log4j.Level.ERROR, "Could not get player cosmetics because of I/O Error: " + var6.getMessage());
                }
            }

            return null;
        }).exceptionally((throwable) -> {
            if (Config.isDebugMode()) {
                logger.log(org.apache.logging.log4j.Level.ERROR, "Could not get player cosmetics because wtf is happening", throwable);
            }

            return null;
        }).thenAcceptAsync((playerData) -> {
            if (playerData != null) {
                PLAYER_COSMETICS = playerData;
                if (Config.isDebugMode()) {
                    logger.log(org.apache.logging.log4j.Level.INFO, "Player cosmetics successfully registered");
                }
            } else {
                PLAYER_COSMETICS = Collections.emptyMap();
                if (Config.isDebugMode()) {
                    logger.log(org.apache.logging.log4j.Level.WARN, "Player cosmetics could not registered, cosmetics will be ignored");
                }
            }

        });
    }

    public static boolean isNightTime(Level world) {
        return (double)world.getTimeOfDay((float)world.getDayTime()) >= 0.25965086 && (double)world.getTimeOfDay((float)world.getDayTime()) <= 0.7403491;
    }

    public Illuminations() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onInitializeClient);
        DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "illuminations");
        FIREFLY = PARTICLE_TYPES.register("firefly", () -> {
            return new SimpleParticleType(true);
        });
        GLOWWORM = PARTICLE_TYPES.register("glowworm", () -> {
            return new SimpleParticleType(true);
        });
        PLANKTON = PARTICLE_TYPES.register("plankton", () -> {
            return new SimpleParticleType(true);
        });
        EYES = PARTICLE_TYPES.register("eyes", () -> {
            return new SimpleParticleType(true);
        });
        CHORUS_PETAL = PARTICLE_TYPES.register("chorus_petal", () -> {
            return new SimpleParticleType(true);
        });
        WILL_O_WISP = PARTICLE_TYPES.register("will_o_wisp", () -> {
            return new SimpleParticleType(true);
        });
        WISP_TRAIL = PARTICLE_TYPES.register("wisp_trail", () -> new ParticleType<WispTrailParticleEffect>(true, WispTrailParticleEffect.PARAMETERS_FACTORY) {
            public Codec<WispTrailParticleEffect> codec() {
                return WispTrailParticleEffect.CODEC;
            }
        });
        PUMPKIN_SPIRIT = PARTICLE_TYPES.register("pumpkin_spirit", () -> {
            return new SimpleParticleType(true);
        });
        POLTERGEIST = PARTICLE_TYPES.register("poltergeist", () -> {
            return new SimpleParticleType(true);
        });
        PRISMARINE_CRYSTAL = PARTICLE_TYPES.register("prismarine_crystal", () -> {
            return new SimpleParticleType(true);
        });
        TWILIGHT_AURA = PARTICLE_TYPES.register("twilight_aura", () -> {
            return new SimpleParticleType(true);
        });
        GHOSTLY_AURA = PARTICLE_TYPES.register("ghostly_aura", () -> {
            return new SimpleParticleType(true);
        });
        CHORUS_AURA = PARTICLE_TYPES.register("chorus_aura", () -> {
            return new SimpleParticleType(true);
        });
        AUTUMN_LEAVES_AURA = PARTICLE_TYPES.register("autumn_leaves", () -> {
            return new SimpleParticleType(true);
        });
        SCULK_TENDRIL_AURA = PARTICLE_TYPES.register("sculk_tendril", () -> {
            return new SimpleParticleType(true);
        });
        SHADOWBRINGER_AURA = PARTICLE_TYPES.register("shadowbringer_aura", () -> {
            return new SimpleParticleType(true);
        });
        GOLDENROD_AURA = PARTICLE_TYPES.register("goldenrod_aura", () -> {
            return new SimpleParticleType(true);
        });
        CONFETTI_AURA = PARTICLE_TYPES.register("confetti", () -> {
            return new SimpleParticleType(true);
        });
        PRISMATIC_CONFETTI_AURA = PARTICLE_TYPES.register("prismatic_confetti", () -> {
            return new SimpleParticleType(true);
        });
        PRISMARINE_AURA = PARTICLE_TYPES.register("prismarine_aura", () -> {
            return new SimpleParticleType(true);
        });
        PRIDE_PET = PARTICLE_TYPES.register("pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        GAY_PRIDE_PET = PARTICLE_TYPES.register("gay_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        TRANS_PRIDE_PET = PARTICLE_TYPES.register("trans_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        LESBIAN_PRIDE_PET = PARTICLE_TYPES.register("lesbian_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        BI_PRIDE_PET = PARTICLE_TYPES.register("bi_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        ACE_PRIDE_PET = PARTICLE_TYPES.register("ace_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        NB_PRIDE_PET = PARTICLE_TYPES.register("nb_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        INTERSEX_PRIDE_PET = PARTICLE_TYPES.register("intersex_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        ARO_PRIDE_PET = PARTICLE_TYPES.register("aro_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        PAN_PRIDE_PET = PARTICLE_TYPES.register("pan_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        AGENDER_PRIDE_PET = PARTICLE_TYPES.register("agender_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        GENDERFLUID_PRIDE_PET = PARTICLE_TYPES.register("genderfluid_pride_pet", () -> {
            return new SimpleParticleType(true);
        });
        WILL_O_WISP_PET = PARTICLE_TYPES.register("will_o_wisp_pet", () -> {
            return new SimpleParticleType(true);
        });
        GOLDEN_WILL_PET = PARTICLE_TYPES.register("golden_will_pet", () -> {
            return new SimpleParticleType(true);
        });
        FOUNDING_SKULL_PET = PARTICLE_TYPES.register("founding_skull_pet", () -> {
            return new SimpleParticleType(true);
        });
        DISSOLUTION_WISP_PET = PARTICLE_TYPES.register("dissolution_wisp_pet", () -> {
            return new SimpleParticleType(true);
        });
        JACKO_PET = PARTICLE_TYPES.register("jacko_pet", () -> {
            return new SimpleParticleType(true);
        });
        PUMPKIN_SPIRIT_PET = PARTICLE_TYPES.register("pumpkin_spirit_pet", () -> {
            return new SimpleParticleType(true);
        });
        POLTERGEIST_PET = PARTICLE_TYPES.register("poltergeist_pet", () -> {
            return new SimpleParticleType(true);
        });
        LANTERN_PET = PARTICLE_TYPES.register("lantern_pet", () -> {
            return new SimpleParticleType(true);
        });
        SOUL_LANTERN_PET = PARTICLE_TYPES.register("soul_lantern_pet", () -> {
            return new SimpleParticleType(true);
        });
        CRYING_LANTERN_PET = PARTICLE_TYPES.register("crying_lantern_pet", () -> {
            return new SimpleParticleType(true);
        });
        SOOTHING_LANTERN_PET = PARTICLE_TYPES.register("soothing_lantern_pet", () -> {
            return new SimpleParticleType(true);
        });
        PARTICLE_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onInitializeClient(FMLClientSetupEvent event) {
        Config.load();
        loadPlayerCosmetics();
        ClientHelper.load();
        ImmutableMap.Builder<ResourceLocation, Set<IlluminationData>> biomeBuilder = ImmutableMap.builder();
        Config.getBiomeSettings().forEach((biome, settings) -> {
            ImmutableSet.Builder<IlluminationData> illuminationDataBuilder = ImmutableSet.builder();
            illuminationDataBuilder.add(new IlluminationData(FIREFLY.get(), FIREFLY_LOCATION_PREDICATE, () -> {
                return Config.getBiomeSettings(biome).fireflySpawnRate().spawnRate;
            }));
            if (settings.glowwormSpawnRate() != null) {
                illuminationDataBuilder.add(new IlluminationData(GLOWWORM.get(), GLOWWORM_LOCATION_PREDICATE, () -> {
                    return Config.getBiomeSettings(biome).glowwormSpawnRate().spawnRate;
                }));
            }

            if (settings.planktonSpawnRate() != null) {
                illuminationDataBuilder.add(new IlluminationData(PLANKTON.get(), PLANKTON_LOCATION_PREDICATE, () -> {
                    return Config.getBiomeSettings(biome).planktonSpawnRate().spawnRate;
                }));
            }

            biomeBuilder.put(biome, illuminationDataBuilder.build());
        });
        ILLUMINATIONS_BIOME_CATEGORIES = biomeBuilder.build();
        ILLUMINATIONS_BIOMES = ImmutableMap.<ResourceLocation, Set<IlluminationData>>builder().put(new ResourceLocation("minecraft:soul_sand_valley"), ImmutableSet.of(new IlluminationData(WILL_O_WISP.get(), WISP_LOCATION_PREDICATE, () -> {
            return Config.getWillOWispsSpawnRate().spawnRate;
        }))).build();
        AURAS_DATA = ImmutableMap.<String, AuraData>builder().put("twilight", new AuraData(TWILIGHT_AURA.get(), () -> DefaultConfig.getAuraSettings("twilight"))).put("ghostly", new AuraData(GHOSTLY_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("ghostly");
        })).put("chorus", new AuraData(CHORUS_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("chorus");
        })).put("autumn_leaves", new AuraData(AUTUMN_LEAVES_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("autumn_leaves");
        })).put("sculk_tendrils", new AuraData(SCULK_TENDRIL_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("sculk_tendrils");
        })).put("shadowbringer_soul", new AuraData(SHADOWBRINGER_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("shadowbringer_soul");
        })).put("goldenrod", new AuraData(GOLDENROD_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("goldenrod");
        })).put("confetti", new AuraData(CONFETTI_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("confetti");
        })).put("prismatic_confetti", new AuraData(PRISMATIC_CONFETTI_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("prismatic_confetti");
        })).put("prismarine", new AuraData(PRISMARINE_AURA.get(), () -> {
            return DefaultConfig.getAuraSettings("prismarine");
        })).build();
        OVERHEADS_DATA = ImmutableMap.<String, OverheadData>builder().put("solar_crown", new OverheadData(CrownModel::new, "solar_crown")).put("frost_crown", new OverheadData(CrownModel::new, "frost_crown")).put("pyro_crown", new OverheadData(CrownModel::new, "pyro_crown")).put("chorus_crown", new OverheadData(CrownModel::new, "chorus_crown")).put("bloodfiend_crown", new OverheadData(CrownModel::new, "bloodfiend_crown")).put("dreadlich_crown", new OverheadData(CrownModel::new, "dreadlich_crown")).put("mooncult_crown", new OverheadData(CrownModel::new, "mooncult_crown")).put("deepsculk_horns", new OverheadData(HornsModel::new, "deepsculk_horns")).put("springfae_horns", new OverheadData(HornsModel::new, "springfae_horns")).put("voidheart_tiara", new OverheadData(VoidheartTiaraModel::new, "voidheart_tiara")).put("worldweaver_halo", new OverheadData(HaloModel::new, "worldweaver_halo")).put("summerbreeze_wreath", new OverheadData(WreathModel::new, "summerbreeze_wreath")).put("glowsquid_cult_crown", new OverheadData(TiaraModel::new, "glowsquid_cult_crown")).put("timeaspect_cult_crown", new OverheadData(TiaraModel::new, "timeaspect_cult_crown")).put("prismarine_crown", new OverheadData(CrownModel::new, "prismarine_crown")).build();
        PETS_DATA = ImmutableMap.<String, SimpleParticleType>builder().put("pride", PRIDE_PET.get()).put("gay_pride", GAY_PRIDE_PET.get()).put("trans_pride", TRANS_PRIDE_PET.get()).put("lesbian_pride", LESBIAN_PRIDE_PET.get()).put("bi_pride", BI_PRIDE_PET.get()).put("ace_pride", ACE_PRIDE_PET.get()).put("nb_pride", NB_PRIDE_PET.get()).put("intersex_pride", INTERSEX_PRIDE_PET.get()).put("aro_pride", ARO_PRIDE_PET.get()).put("pan_pride", PAN_PRIDE_PET.get()).put("agender_pride", AGENDER_PRIDE_PET.get()).put("genderfluid_pride", GENDERFLUID_PRIDE_PET.get()).put("jacko", JACKO_PET.get()).put("will_o_wisp", WILL_O_WISP_PET.get()).put("golden_will", GOLDEN_WILL_PET.get()).put("founding_skull", FOUNDING_SKULL_PET.get()).put("dissolution_wisp", DISSOLUTION_WISP_PET.get()).put("pumpkin_spirit", PUMPKIN_SPIRIT_PET.get()).put("poltergeist", POLTERGEIST_PET.get()).put("lantern", LANTERN_PET.get()).put("soul_lantern", SOUL_LANTERN_PET.get()).put("crying_lantern", CRYING_LANTERN_PET.get()).put("soothing_lantern", SOOTHING_LANTERN_PET.get()).build();
    }

    private static class PlayerCosmeticDataParser implements JsonDeserializer<PlayerCosmeticData> {
        private PlayerCosmeticDataParser() {
        }

        public PlayerCosmeticData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            return new PlayerCosmeticData(jsonObject.get("aura"), jsonObject.get("color"), jsonObject.get("overhead"), jsonObject.get("pet"));
        }
    }
}
