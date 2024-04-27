package ladysnake.illuminations.mixin;

import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChorusFlowerBlock.class})
public abstract class ChorusFlowerBlockMixin extends BlockMixin {
    public ChorusFlowerBlockMixin() {
    }

    protected void illuminations$randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        for(int i = 0; i < (6 - (Integer)state.getValue(ChorusFlowerBlock.AGE)) * Config.getChorusPetalsSpawnMultiplier(); ++i) {
            if (Illuminations.CHORUS_PETAL.isPresent()) {
                world.addParticle((ParticleOptions)Illuminations.CHORUS_PETAL.get(), true, (double)pos.getX() + 0.5 + random.nextGaussian() * 5.0, (double)pos.getY() + 0.5 + random.nextGaussian() * 5.0, (double)pos.getZ() + 0.5 + random.nextGaussian() * 5.0, 0.0, 0.0, 0.0);
            }
        }

    }
}
