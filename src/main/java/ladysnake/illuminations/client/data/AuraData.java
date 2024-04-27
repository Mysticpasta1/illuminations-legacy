package ladysnake.illuminations.client.data;

import java.util.function.Supplier;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public record AuraData(SimpleParticleType particle, Supplier<AuraSettings> auraSettingsSupplier) {
    public AuraData(SimpleParticleType particle, Supplier<AuraSettings> auraSettingsSupplier) {
        this.particle = particle;
        this.auraSettingsSupplier = auraSettingsSupplier;
    }

    public boolean shouldAddParticle(RandomSource random, int age) {
        AuraSettings settings = (AuraSettings)this.auraSettingsSupplier().get();
        if (settings.spawnRate() == 0.0F) {
            return false;
        } else {
            float rand = random.nextFloat();
            return rand <= settings.spawnRate() && age % settings.delay() == 0;
        }
    }

    public SimpleParticleType particle() {
        return this.particle;
    }

    public Supplier<AuraSettings> auraSettingsSupplier() {
        return this.auraSettingsSupplier;
    }
}
