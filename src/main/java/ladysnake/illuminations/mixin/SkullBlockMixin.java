package ladysnake.illuminations.mixin;

import java.time.LocalDate;
import java.time.Month;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SkullBlock.class})
public abstract class SkullBlockMixin extends BlockMixin {
    public SkullBlockMixin() {
    }

    protected void illuminations$randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if ((state.getBlock() == Blocks.SKELETON_SKULL || state.getBlock() == Blocks.SKELETON_WALL_SKULL) && Illuminations.isNightTime(world) && random.nextInt(100) == 0 && (Config.getHalloweenFeatures() == HalloweenFeatures.ENABLE && LocalDate.now().getMonth() == Month.OCTOBER || Config.getHalloweenFeatures() == HalloweenFeatures.ALWAYS) && Illuminations.POLTERGEIST.isPresent()) {
            world.addParticle((ParticleOptions)Illuminations.POLTERGEIST.get(), true, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

    }
}
