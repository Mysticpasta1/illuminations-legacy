package ladysnake.illuminations.client.particle.aura;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AutumnLeavesParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final int variant;
    private final SpriteSet spriteProvider;
    private final double beginX;
    private final double beginY;
    private final double beginZ;

    public AutumnLeavesParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y + 0.05000000074505806, z, velocityX, velocityY, velocityZ);
        this.variant = RANDOM.nextInt(6);
        this.spriteProvider = spriteProvider;
        this.quadSize *= 0.5F + RANDOM.nextFloat() / 2.0F;
        this.lifetime = 30 + RANDOM.nextInt(60);
        this.hasPhysics = true;
        this.setSprite(spriteProvider.get(this.variant % 3, 2));
        this.gCol = RANDOM.nextFloat() / 2.0F + 0.5F;
        this.bCol = 0.0F;
        this.beginX = x;
        this.beginY = y;
        this.beginZ = z;
        this.roll = this.random.nextFloat() * 360.0F;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 vec3d = camera.getPosition();
        float f = (float)(Mth.lerp((double)tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float)(Mth.lerp((double)tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float)(Mth.lerp((double)tickDelta, this.zo, this.z) - vec3d.z());
        Quaternion quaternion2;
        if (this.roll == 0.0F) {
            quaternion2 = camera.rotation();
        } else {
            quaternion2 = new Quaternion(camera.rotation());
            float i = this.roll;
            quaternion2.mul(Vector3f.ZP.rotationDegrees(i));
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
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        if (this.age++ < this.lifetime - 10) {
            this.alpha = Math.min(1.0F, this.alpha + 0.1F);
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.gCol = (float)((double)this.gCol * 0.98);
        float fraction = (float)this.age / (float)this.lifetime;
        this.x = (double)(Mth.cos((float)this.age / 15.0F + 1.0471973F * ((float)this.variant + 0.5F)) * fraction) + this.beginX;
        this.z = (double)(Mth.sin((float)this.age / 15.0F + 1.0471973F * ((float)this.variant + 0.5F)) * fraction) + this.beginZ;
        this.y = (double)((float)this.age / 34.0F) + this.beginY + 0.05000000074505806;
        if (this.age >= this.lifetime - 10) {
            this.alpha = Math.max(0.0F, this.alpha - 0.1F);
            if (this.alpha <= 0.0F) {
                this.remove();
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new AutumnLeavesParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
