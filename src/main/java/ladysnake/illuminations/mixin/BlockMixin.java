package ladysnake.illuminations.mixin;

import ladysnake.illuminations.client.Illuminations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Block.class})
public abstract class BlockMixin {
    public BlockMixin() {
    }

    @Shadow
    public abstract BlockState defaultBlockState();

    @Inject(
        method = {"animateTick"},
        at = {@At("RETURN")}
    )
    protected void illuminations$randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (this.defaultBlockState().getBlock() == Blocks.SEA_LANTERN) {
            for(int i = 0; i < 10; ++i) {
                BlockPos blockPos = new BlockPos((double)pos.getX() + 0.5 + random.nextGaussian() * 15.0, (double)pos.getY() + 0.5 + random.nextGaussian() * 15.0, (double)pos.getZ() + 0.5 + random.nextGaussian() * 15.0);
                if (world.getBlockState(blockPos).getBlock() == Blocks.WATER && random.nextInt(1 + world.getMaxLocalRawBrightness(blockPos)) == 0 && Illuminations.PRISMARINE_CRYSTAL.isPresent()) {
                    world.addParticle((ParticleOptions)Illuminations.PRISMARINE_CRYSTAL.get(), true, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }

    }
}
