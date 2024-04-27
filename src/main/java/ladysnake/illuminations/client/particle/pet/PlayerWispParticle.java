package ladysnake.illuminations.client.particle.pet;

import ladysnake.illuminations.client.particle.WillOWispParticle;
import ladysnake.illuminations.client.particle.WispTrailParticleEffect;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PlayerWispParticle extends WillOWispParticle {
    protected Player owner;

    protected PlayerWispParticle(ClientLevel world, double x, double y, double z, ResourceLocation texture, float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
        super(world, x, y, z, texture, red, green, blue, redEvolution, greenEvolution, blueEvolution);
        this.lifetime = 35;
        this.owner = world.getNearestPlayer(TargetingConditions.forNonCombat().range(1.0), this.x, this.y, this.z);
        if (this.owner == null) {
            this.remove();
        }

        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.alpha = 0.0F;
    }

    public void tick() {
        if (this.age > 10) {
            this.alpha = 1.0F;

            for(int i = 0; i < 1; ++i) {
                this.level.addParticle(new WispTrailParticleEffect(this.rCol, this.gCol, this.bCol, this.redEvolution, this.greenEvolution, this.blueEvolution), this.x + this.random.nextGaussian() / 15.0, this.y + this.random.nextGaussian() / 15.0, this.z + this.random.nextGaussian() / 15.0, 0.0, 0.0, 0.0);
            }
        } else {
            this.alpha = 0.0F;
        }

        if (this.owner != null) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ >= this.lifetime) {
                this.remove();
            }

            this.setPos(this.owner.getX() + Math.cos((double)(this.owner.yBodyRot / 50.0F)) * 0.5, this.owner.getY() + (double)this.owner.getBbHeight() + 0.5 + Math.sin((double)((float)this.owner.tickCount / 12.0F)) / 12.0, this.owner.getZ() - Math.cos((double)(this.owner.yBodyRot / 50.0F)) * 0.5);
            this.pitch = -this.owner.getXRot();
            this.prevPitch = -this.owner.xRotO;
            this.yaw = -this.owner.getYRot();
            this.prevYaw = -this.owner.yRotO;
        } else {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final ResourceLocation texture;
        private final float red;
        private final float green;
        private final float blue;
        private final float redEvolution;
        private final float greenEvolution;
        private final float blueEvolution;

        public DefaultFactory(SpriteSet spriteProvider, ResourceLocation texture, float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
            this.texture = texture;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.redEvolution = redEvolution;
            this.greenEvolution = greenEvolution;
            this.blueEvolution = blueEvolution;
        }

        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new PlayerWispParticle(world, x, y, z, this.texture, this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution);
        }
    }
}
