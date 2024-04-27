package ladysnake.illuminations.client.particle.aura;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ConfettiParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final double rotationXmod;
    private final double rotationYmod;
    private final double rotationZmod;
    private final float groundOffset;
    private float rotationX;
    private float rotationY;
    private float rotationZ;

    public ConfettiParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.quadSize *= 0.1F + (new Random()).nextFloat() * 0.5F;
        this.hasPhysics = true;
        this.setSpriteFromAge(spriteProvider);
        this.alpha = 1.0F;
        this.lifetime = ThreadLocalRandom.current().nextInt(400, 420);
        this.rCol = RANDOM.nextFloat();
        this.bCol = RANDOM.nextFloat();
        this.gCol = RANDOM.nextFloat();
        this.gravity = 0.1F;
        this.xd = velocityX * 10.0;
        this.yd = velocityY * 10.0;
        this.zd = velocityZ * 10.0;
        this.friction = 0.5F;
        this.rotationX = RANDOM.nextFloat() * 360.0F;
        this.rotationY = RANDOM.nextFloat() * 360.0F;
        this.rotationZ = RANDOM.nextFloat() * 360.0F;
        this.rotationXmod = (double)(RANDOM.nextFloat() * 10.0F * (float)(this.random.nextBoolean() ? -1 : 1));
        this.rotationYmod = (double)(RANDOM.nextFloat() * 10.0F * (float)(this.random.nextBoolean() ? -1 : 1));
        this.rotationZmod = (double)(RANDOM.nextFloat() * 10.0F * (float)(this.random.nextBoolean() ? -1 : 1));
        this.groundOffset = RANDOM.nextFloat() / 100.0F + 0.001F;
        this.setPos(this.x + TwilightFireflyParticle.getWanderingDistance(this.random), this.y + (double)this.random.nextFloat() * 2.0, this.z + TwilightFireflyParticle.getWanderingDistance(this.random));
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 vec3d = camera.getPosition();
        float f = (float)(Mth.lerp((double)tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float)(Mth.lerp((double)tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float)(Mth.lerp((double)tickDelta, this.zo, this.z) - vec3d.z());
        Vector3f[] Vec3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float j = this.getQuadSize(tickDelta);
        int k;
        Vector3f Vec3f2;
        if (!this.onGround) {
            this.rotationX = (float)((double)this.rotationX + this.rotationXmod);
            this.rotationY = (float)((double)this.rotationY + this.rotationYmod);
            this.rotationZ = (float)((double)this.rotationZ + this.rotationZmod);

            for(k = 0; k < 4; ++k) {
                Vec3f2 = Vec3fs[k];
                Vec3f2.transform(new Quaternion(this.rotationX, this.rotationY, this.rotationZ, true));
                Vec3f2.mul(j);
                Vec3f2.add(f, g, h);
            }
        } else {
            this.rotationX = 90.0F;
            this.rotationY = 0.0F;

            for(k = 0; k < 4; ++k) {
                Vec3f2 = Vec3fs[k];
                Vec3f2.transform(new Quaternion(this.rotationX, this.rotationY, this.rotationZ, true));
                Vec3f2.mul(j);
                Vec3f2.add(f, g + this.groundOffset, h);
            }
        }

        float minU = this.getU0();
        float maxU = this.getU1();
        float minV = this.getV0();
        float maxV = this.getV1();
        int l = 15728880;
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else if (this.level.getFluidState(new BlockPos(this.x, this.y + 0.2, this.z)).isEmpty()) {
            if (this.level.getFluidState(new BlockPos(this.x, this.y - 0.01, this.z)).is(FluidTags.WATER)) {
                this.onGround = true;
                this.yd = 0.0;
            } else {
                this.yd -= 0.04 * (double)this.gravity;
                this.move(this.xd, this.yd, this.zd);
                if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                    this.xd *= 1.1;
                    this.zd *= 1.1;
                }

                this.xd *= (double)this.friction;
                this.yd *= (double)this.friction;
                this.zd *= (double)this.friction;
                this.friction = Math.min(0.98F, this.friction * 1.15F);
                if (this.onGround) {
                    this.xd *= 0.699999988079071;
                    this.zd *= 0.699999988079071;
                }
            }
        } else {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ConfettiParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
