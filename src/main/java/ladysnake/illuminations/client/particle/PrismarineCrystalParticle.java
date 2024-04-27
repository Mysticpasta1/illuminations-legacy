package ladysnake.illuminations.client.particle;

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

public class PrismarineCrystalParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    protected final float rotationFactor;
    private final int variant;
    private final SpriteSet spriteProvider;
    private final float groundOffset;

    public PrismarineCrystalParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.variant = RANDOM.nextInt(3);
        this.spriteProvider = spriteProvider;
        this.quadSize *= 1.0F + RANDOM.nextFloat();
        this.lifetime = ThreadLocalRandom.current().nextInt(400, 1201);
        this.hasPhysics = true;
        this.setSprite(spriteProvider.get(this.variant, 2));
        if (velocityY == 0.0 && velocityX == 0.0 && velocityZ == 0.0) {
            this.alpha = 0.0F;
        }

        this.xd = (double)this.random.nextFloat() * 0.01;
        this.yd = (double)(-this.random.nextFloat()) * 0.01;
        this.zd = (double)this.random.nextFloat() * 0.01;
        this.groundOffset = RANDOM.nextFloat() / 100.0F + 0.001F;
        this.rotationFactor = ((float)Math.random() - 0.5F) * 0.01F;
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
            float i = Mth.lerp(tickDelta, this.oRoll, this.roll);
            quaternion2.mul(Vector3f.ZP.rotationDegrees(i));
        }

        Vector3f Vec3f = new Vector3f(-1.0F, -1.0F, 0.0F);
        Vec3f.transform(quaternion2);
        Vector3f[] Vec3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float j = this.getQuadSize(tickDelta);

        for(int k = 0; k < 4; ++k) {
            Vector3f Vec3f2 = Vec3fs[k];
            if (this.onGround) {
                Vec3f2.transform(new Quaternion(90.0F, 0.0F, quaternion2.k(), true));
            } else {
                Vec3f2.transform(quaternion2);
            }

            Vec3f2.mul(j);
            Vec3f2.add(f, g + this.groundOffset, h);
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
        if (this.age++ < this.lifetime) {
            this.alpha = Math.min(1.0F, this.alpha + 0.01F);
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.level.getFluidState(new BlockPos(this.x, this.y, this.z)).is(FluidTags.WATER)) {
            this.move(this.xd, this.yd, this.zd);
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.9;
            this.yd = -0.9;
            this.zd *= 0.9;
        }

        if (this.age >= this.lifetime) {
            this.alpha = Math.max(0.0F, this.alpha - 0.01F);
            if (this.alpha <= 0.0F) {
                this.remove();
            }
        }

        this.rCol = 0.8F + (float)Math.sin((double)((float)this.age / 100.0F)) * 0.2F;
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
            return new PrismarineCrystalParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
