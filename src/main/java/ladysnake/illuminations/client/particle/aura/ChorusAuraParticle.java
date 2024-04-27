package ladysnake.illuminations.client.particle.aura;

import ladysnake.illuminations.client.particle.ChorusPetalParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChorusAuraParticle extends ChorusPetalParticle {
    public ChorusAuraParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.yd = -0.01 - (double)(this.random.nextFloat() / 10.0F);
        this.xd = this.random.nextGaussian() / 50.0;
        this.zd = this.random.nextGaussian() / 50.0;
        this.setPos(this.x + TwilightFireflyParticle.getWanderingDistance(this.random), this.y + (double)this.random.nextFloat() * 2.0, this.z + TwilightFireflyParticle.getWanderingDistance(this.random));
    }

    public void tick() {
        if (this.age++ < this.lifetime) {
            this.alpha = Math.min(1.0F, this.alpha + 0.1F);
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.99;
        this.yd *= 0.99;
        this.zd *= 0.99;
        this.rCol = (float)((double)this.rCol * 0.99);
        this.gCol = (float)((double)this.gCol * 0.98);
        if (this.age >= this.lifetime) {
            this.alpha = Math.max(0.0F, this.alpha - 0.1F);
            if (this.alpha <= 0.0F) {
                this.remove();
            }
        }

        this.oRoll = this.roll;
        if (this.onGround || this.level.getFluidState(new BlockPos(this.x, this.y, this.z)).is(FluidTags.WATER)) {
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
            return new ChorusAuraParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
