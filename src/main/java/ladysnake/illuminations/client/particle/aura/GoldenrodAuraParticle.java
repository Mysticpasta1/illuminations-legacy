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

public class GoldenrodAuraParticle extends ChorusPetalParticle {
    private int elevation = 0;

    public GoldenrodAuraParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.yd = 0.0;
        this.xd = 0.0;
        this.zd = 0.0;
        this.quadSize = 0.9F;
        this.setPos(this.x + this.get_rando(), this.y + (double)this.random.nextFloat() + 0.75, this.z + this.get_rando());
    }

    public double get_rando() {
        double rando = ((double)this.random.nextFloat() - 0.5) * 1.4;
        if (rando < 0.3 && rando > 0.0) {
            rando += 0.3;
        } else if (rando < 0.0 && rando > -0.3) {
            rando -= 0.3;
        }

        return rando;
    }

    public void tick() {
        this.age += 2;
        if (this.age < this.lifetime) {
            this.alpha = Math.min(1.0F, this.alpha + 0.1F);
        }

        this.quadSize = (float)((double)this.quadSize * 0.9);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.move(this.xd, this.yd, this.zd);
        if (this.yd == 0.0) {
            int temp_rand = this.random.nextInt(15);
            if (temp_rand == 0 && this.elevation < 1) {
                this.yd = 0.3;
                ++this.elevation;
            } else if (temp_rand == 1 && this.elevation > -2) {
                this.yd = -0.3;
                --this.elevation;
            }
        } else if (Math.abs(this.yd) > 0.08) {
            this.yd *= 0.5;
        } else {
            this.yd = 0.0;
        }

        this.bCol = (float)((double)this.bCol * 0.96);
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

        this.roll = 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new GoldenrodAuraParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
