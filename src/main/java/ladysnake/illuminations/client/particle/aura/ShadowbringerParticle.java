package ladysnake.illuminations.client.particle.aura;

import ladysnake.illuminations.client.particle.ChorusPetalParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShadowbringerParticle extends ChorusPetalParticle {
    private final SpriteSet spriteProvider;
    private final float randEffect;
    boolean negateX;
    boolean negateZ;

    public ShadowbringerParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.randEffect = this.random.nextFloat() + 0.5F;
        this.negateX = this.random.nextBoolean();
        this.negateZ = this.random.nextBoolean();
        this.lifetime = 40 + this.random.nextInt(40);
        this.yd = (0.2 + (double)this.random.nextFloat()) / 10.0;
        this.xd = this.negateX ? -this.random.nextGaussian() / 50.0 : this.random.nextGaussian() / 50.0;
        this.zd = this.negateZ ? -this.random.nextGaussian() / 50.0 : this.random.nextGaussian() / 50.0;
        this.quadSize = (float)((double)this.quadSize + this.random.nextGaussian() / 12.0);
        this.spriteProvider = spriteProvider;
        this.setSprite(spriteProvider.get(0, 3));
        this.alpha = 0.0F;
        this.setPos(this.x + TwilightFireflyParticle.getWanderingDistance(this.random), this.y + (double)this.random.nextFloat() * 1.5, this.z + TwilightFireflyParticle.getWanderingDistance(this.random));
    }

    public void tick() {
        if (this.age++ < this.lifetime) {
            this.alpha = Math.min(1.0F, this.alpha + 0.045F);
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.xd = this.xd * 0.85 + (this.negateX ? -(Math.sin((double)this.age / (2.0 + (double)this.randEffect)) / 20.0) : Math.sin((double)this.age / 3.0) / 20.0);
        this.zd = this.zd * 0.85 + (this.negateZ ? -(Math.sin((double)this.age / (2.0 + (double)this.randEffect)) / 20.0) : Math.sin((double)this.age / 3.0) / 20.0);
        this.move(this.xd, this.yd, this.zd);
        if (this.age > 0 && this.lifetime > 0) {
            float agePercent = (float)((double)((float)this.age / (float)this.lifetime) * 1.5);
            this.setSprite(this.spriteProvider.get(Math.min(3, (int)(agePercent * 4.0F)), 3));
        }

        if (this.age >= this.lifetime) {
            this.alpha = Math.max(0.0F, this.alpha - 0.015F);
            if (this.alpha <= 0.0F) {
                this.remove();
            }
        }

        this.oRoll = this.roll;
        if (this.onGround || this.level.getFluidState(new BlockPos(this.x, this.y, this.z)).getType() != Fluids.EMPTY) {
            this.yd *= 0.95;
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
            return new ShadowbringerParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
