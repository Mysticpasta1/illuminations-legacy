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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SculkTendrilParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final SpriteSet provider;
    private boolean wasOnGround = false;

    public SculkTendrilParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x + (double)(RANDOM.nextFloat() * 2.0F - 1.0F), y, z + (double)(RANDOM.nextFloat() * 2.0F - 1.0F), velocityX, velocityY, velocityZ);
        this.setSprite(spriteProvider.get(0, 1));
        this.provider = spriteProvider;
        this.lifetime = 100;
        this.roll = RANDOM.nextFloat() * 180.0F;
        this.quadSize = 0.0F;
        this.hasPhysics = true;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 vec3d = camera.getPosition();
        float f = (float)(Mth.lerp((double)tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float)(Mth.lerp((double)tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float)(Mth.lerp((double)tickDelta, this.zo, this.z) - vec3d.z());
        Vector3f[] Vec3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float j = this.getQuadSize(tickDelta);

        for(int k = 0; k < 4; ++k) {
            Vector3f Vec3f2 = Vec3fs[k];
            Vec3f2.transform(new Quaternion(0.0F, this.roll, 0.0F, true));
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

    public void tick() {
        if (!this.onGround) {
            this.move(0.0, -1.0, 0.0);
            this.wasOnGround = false;
        } else {
            if (!this.wasOnGround) {
                this.setLocationFromBoundingbox();
                this.wasOnGround = true;
            }

            if (this.age++ < this.lifetime) {
                if (this.quadSize == 0.0F) {
                    this.y -= 0.4000000059604645;
                }

                if (this.quadSize < 0.4F) {
                    this.quadSize = Math.min(0.4F, this.quadSize + 0.05F);
                    this.y += 0.05000000074505806;
                }

                if (this.age == 75) {
                    this.setSprite(this.provider.get(1, 1));
                }
            }

            this.setBoundingBox(this.getBoundingBox().inflate(0.0, 1.0, 0.0));
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            this.oRoll = this.roll;
            if (this.age >= this.lifetime) {
                this.quadSize = Math.max(0.0F, this.quadSize - 0.05F);
                this.y -= 0.05000000074505806;
                if (this.quadSize <= 0.0F) {
                    this.remove();
                }
            }

        }
    }

    protected void setLocationFromBoundingbox() {
        AABB box = this.getBoundingBox();
        this.x = (box.minX + box.maxX) / 2.0;
        this.y = box.minY + 0.4;
        this.z = (box.minZ + box.maxZ) / 2.0;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SculkTendrilParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
