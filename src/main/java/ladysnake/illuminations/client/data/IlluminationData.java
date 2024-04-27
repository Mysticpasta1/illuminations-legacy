package ladysnake.illuminations.client.data;

import java.util.function.BiPredicate;
import java.util.function.Supplier;
import ladysnake.illuminations.client.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public record IlluminationData(SimpleParticleType illuminationType, BiPredicate<Level, BlockPos> locationSpawnPredicate, Supplier<Float> chanceSupplier) {
    public IlluminationData(SimpleParticleType illuminationType, BiPredicate<Level, BlockPos> locationSpawnPredicate, Supplier<Float> chanceSupplier) {
        this.illuminationType = illuminationType;
        this.locationSpawnPredicate = locationSpawnPredicate;
        this.chanceSupplier = chanceSupplier;
    }

    public boolean shouldAddParticle(RandomSource random) {
        float chance = (Float)this.chanceSupplier.get();
        if (chance <= 0.0F) {
            return false;
        } else {
            float density = (float)Config.getDensity() / 100.0F;
            return random.nextFloat() <= chance * density;
        }
    }

    public SimpleParticleType illuminationType() {
        return this.illuminationType;
    }

    public BiPredicate<Level, BlockPos> locationSpawnPredicate() {
        return this.locationSpawnPredicate;
    }

    public Supplier<Float> chanceSupplier() {
        return this.chanceSupplier;
    }
}
