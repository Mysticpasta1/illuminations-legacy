package ladysnake.illuminations.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EyesParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final SpriteSet spriteProvider;
    protected float alpha = 1.0F;

    public EyesParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.setSprite(spriteProvider.get(0, 3));
        this.quadSize *= 1.0F + (new Random()).nextFloat();
        this.lifetime = ThreadLocalRandom.current().nextInt(400, 1201);
        this.hasPhysics = true;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        if (camera.getEntity() instanceof LivingEntity && ((LivingEntity)camera.getEntity()).hasEffect(MobEffects.NIGHT_VISION) || Config.getHalloweenFeatures() == HalloweenFeatures.DISABLE) {
            this.remove();
        }

        Vec3 vec3d = camera.getPosition();
        float f = (float)(Mth.lerp((double)tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float)(Mth.lerp((double)tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float)(Mth.lerp((double)tickDelta, this.zo, this.z) - vec3d.z());
        Quaternion quaternion2;
        if (this.roll == 0.0F) {
            quaternion2 = camera.rotation();
        } else {
            quaternion2 = new Quaternion(camera.rotation());
            float i = Mth.lerp(tickDelta, this.oRoll, this.roll);
            quaternion2.mul(Vector3f.ZP.rotation(i));
        }

        Vector3f Vec3f = new Vector3f(-1.0F, -1.0F, 0.0F);
        Vec3f.transform(quaternion2);
        Vector3f[] Vec3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float j = this.getQuadSize(tickDelta);

        for(int k = 0; k < 4; ++k) {
            Vector3f Vec3f2 = Vec3fs[k];
            Vec3f2.transform(quaternion2);
            Vec3f2.mul(j);
            Vec3f2.add(f, g, h);
        }

        float minU = this.getU0();
        float maxU = this.getU1();
        float minV = this.getV0();
        float maxV = this.getV1();
        int l = 15728880;
        float a = Math.min(1.0F, Math.max(0.0F, this.alpha));
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        if (this.age++ < this.lifetime) {
            if (this.age < 1) {
                this.setSprite(this.spriteProvider.get(0, 3));
            } else if (this.age < 2) {
                this.setSprite(this.spriteProvider.get(1, 3));
            } else if (this.age < 3) {
                this.setSprite(this.spriteProvider.get(2, 3));
            } else {
                this.setSprite(this.spriteProvider.get(3, 3));
            }
        } else if (this.age < this.lifetime + 1) {
            this.setSprite(this.spriteProvider.get(2, 3));
        } else if (this.age < this.lifetime + 2) {
            this.setSprite(this.spriteProvider.get(1, 3));
        } else if (this.age < this.lifetime + 3) {
            this.setSprite(this.spriteProvider.get(0, 3));
        } else {
            this.remove();
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime > this.age && (this.level.getMaxLocalRawBrightness(new BlockPos(this.x, this.y, this.z)) > 0 || this.level.getNearestPlayer(this.x, this.y, this.z, 5.0, false) != null)) {
            this.lifetime = this.age;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new EyesParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
