package ladysnake.illuminations.mixin;

import com.mojang.math.Vector4f;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ParticleEngine.class})
public abstract class ParticleManagerMixin {
    @Shadow
    protected ClientLevel level;
    @Shadow
    @Final
    private RandomSource random;

    public ParticleManagerMixin() {
    }

    @Shadow
    public abstract Particle createParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12);

    @Inject(
        method = {"destroy"},
        at = {@At("RETURN")}
    )
    public void addBlockBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.getBlock() == Blocks.CHORUS_FLOWER) {
            for(int i = 0; i < (6 - (Integer)state.getValue(ChorusFlowerBlock.AGE)) * 10 * Config.getChorusPetalsSpawnMultiplier(); ++i) {
                if (Illuminations.CHORUS_PETAL.isPresent()) {
                    this.createParticle((ParticleOptions)Illuminations.CHORUS_PETAL.get(), (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, this.random.nextGaussian() / 10.0, this.random.nextGaussian() / 10.0, this.random.nextGaussian() / 10.0);
                }
            }
        }

    }
}
