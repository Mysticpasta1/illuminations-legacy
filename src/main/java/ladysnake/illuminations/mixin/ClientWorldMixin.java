package ladysnake.illuminations.mixin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.function.Supplier;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.data.IlluminationData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientLevel.class})
public abstract class ClientWorldMixin extends Level {
    @Shadow public abstract void addParticle(ParticleOptions arg, double d, double e, double f, double g, double h, double i);

    protected ClientWorldMixin(WritableLevelData properties, ResourceKey<Level> registryRef, Holder<DimensionType> dimension, Supplier<ProfilerFiller> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(
        method = {"doAnimateTick"},
        slice = {@Slice(
    from = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/world/level/biome/Biome;getAmbientParticle()Ljava/util/Optional;"
)
)},
        at = {@At(
    value = "INVOKE",
    target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V",
    ordinal = 0,
    shift = Shift.AFTER
)}
    )
    private void randomBlockDisplayTick(int centerX, int centerY, int centerZ, int radius, RandomSource random, @Coerce Object blockParticle, BlockPos.MutableBlockPos blockPos, CallbackInfo ci) {
        BlockPos.MutableBlockPos pos = blockPos.offset(this.random.nextGaussian() * 50.0, this.random.nextGaussian() * 25.0, this.random.nextGaussian() * 50.0).mutable();
        Holder<Biome> b = this.getBiome(pos);
        ResourceLocation biome = this.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey((Biome)b.value());
        this.spawnParticles(pos, (ImmutableSet)Illuminations.ILLUMINATIONS_BIOME_CATEGORIES.get(biome));
        if (Illuminations.ILLUMINATIONS_BIOMES.containsKey(biome)) {
            ImmutableSet<IlluminationData> illuminationDataSet = (ImmutableSet)Illuminations.ILLUMINATIONS_BIOMES.get(biome);
            this.spawnParticles(pos, illuminationDataSet);
        }

        if(Illuminations.FIREFLY_LOCATION_PREDICATE.test(this, pos) && random.nextFloat() <= Config.getFireflySpawnRate().spawnRate && Illuminations.FIREFLY.isPresent()) {
            this.addParticle((ParticleOptions) Illuminations.FIREFLY.get(), (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

        if(Illuminations.GLOWWORM_LOCATION_PREDICATE.test(this, pos) && random.nextFloat() <= Config.getGlowwormSpawnRate().spawnRate && Illuminations.GLOWWORM.isPresent()) {
            this.addParticle((ParticleOptions) Illuminations.GLOWWORM.get(), (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

        if(Illuminations.PLANKTON_LOCATION_PREDICATE.test(this, pos) && random.nextFloat() <= Config.getPlanktonSpawnRate().spawnRate && Illuminations.PLANKTON.isPresent()) {
            this.addParticle((ParticleOptions) Illuminations.PLANKTON.get(), (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

        if (Illuminations.EYES_LOCATION_PREDICATE.test(this, pos) && random.nextFloat() <= Config.getEyesInTheDarkSpawnRate().spawnRate && Illuminations.EYES.isPresent()) {
            this.addParticle((ParticleOptions)Illuminations.EYES.get(), (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

        if (this.getBlockState(pos).getBlock() == Blocks.SOUL_LANTERN && this.getBlockState(pos.offset(0, -1, 0)).is(BlockTags.SOUL_FIRE_BASE_BLOCKS) && random.nextInt(50) == 0 && Illuminations.WILL_O_WISP.isPresent()) {
            this.addParticle((ParticleOptions)Illuminations.WILL_O_WISP.get(), true, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

    }

    private void spawnParticles(BlockPos.MutableBlockPos pos, ImmutableSet<IlluminationData> illuminationDataSet) {
        if (illuminationDataSet != null) {
            UnmodifiableIterator var3 = illuminationDataSet.iterator();

            while(var3.hasNext()) {
                IlluminationData illuminationData = (IlluminationData)var3.next();
                if (illuminationData.locationSpawnPredicate().test(this, pos) && illuminationData.shouldAddParticle(this.random)) {
                    this.addParticle(illuminationData.illuminationType(), (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }

    }

    @Inject(
        method = {"addPlayer"},
        at = {@At("RETURN")}
    )
    public void addPlayer(int id, AbstractClientPlayer player, CallbackInfo ci) {
        Illuminations.loadPlayerCosmetics();
    }
}
