package ladysnake.illuminations.client.particle.aura;

import java.util.concurrent.ThreadLocalRandom;
import ladysnake.illuminations.client.particle.PrismarineCrystalParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PrismarineAuraParticle extends PrismarineCrystalParticle {
    public PrismarineAuraParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.setPos(this.x + TwilightFireflyParticle.getWanderingDistance(this.random), this.y + (double)this.random.nextFloat() * 2.0, this.z + TwilightFireflyParticle.getWanderingDistance(this.random));
        this.lifetime = ThreadLocalRandom.current().nextInt(100, 400);
    }

    public void tick() {
        if (this.age++ < this.lifetime) {
            this.alpha = Math.min(1.0F, this.alpha + 0.1F);
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.move(this.xd, this.yd, this.zd);
        if (this.age >= this.lifetime) {
            this.alpha = Math.max(0.0F, this.alpha - 0.1F);
            if (this.alpha <= 0.0F) {
                this.remove();
            }
        }

        this.rCol = 0.8F + (float)Math.sin((double)((float)this.age / 10.0F)) * 0.2F;
        this.oRoll = this.roll;
        if (this.onGround) {
            this.xd = 0.0;
            this.yd = 0.0;
            this.zd = 0.0;
        }

        if (this.yd != 0.0) {
            this.roll = (float)((double)this.roll + Math.PI * Math.sin((double)(this.rotationFactor * (float)this.age)) / 2.0);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new PrismarineAuraParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
