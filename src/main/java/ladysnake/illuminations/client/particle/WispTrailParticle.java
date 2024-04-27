package ladysnake.illuminations.client.particle;

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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WispTrailParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;
    private final float redEvolution;
    private final float greenEvolution;
    private final float blueEvolution;

    private WispTrailParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, WispTrailParticleEffect wispTrailParticleEffect, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.rCol = wispTrailParticleEffect.getRed();
        this.gCol = wispTrailParticleEffect.getGreen();
        this.bCol = wispTrailParticleEffect.getBlue();
        this.redEvolution = wispTrailParticleEffect.getRedEvolution();
        this.greenEvolution = wispTrailParticleEffect.getGreenEvolution();
        this.blueEvolution = wispTrailParticleEffect.getBlueEvolution();
        this.lifetime = 10 + this.random.nextInt(10);
        this.quadSize *= 0.25F + (new Random()).nextFloat() * 0.5F;
        this.setSpriteFromAge(spriteProvider);
        this.yd = 0.1;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.alpha -= 0.05F;
        }

        if (this.alpha < 0.0F || this.quadSize <= 0.0F) {
            this.remove();
        }

        this.rCol = Mth.clamp(this.rCol + this.redEvolution, 0.0F, 1.0F);
        this.gCol = Mth.clamp(this.gCol + this.greenEvolution, 0.0F, 1.0F);
        this.bCol = Mth.clamp(this.bCol + this.blueEvolution, 0.0F, 1.0F);
        this.yd -= 0.001;
        this.xd = 0.0;
        this.zd = 0.0;
        this.quadSize = Math.max(0.0F, this.quadSize - 0.005F);
        this.move(this.xd, this.yd, this.zd);
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
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(l).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<WispTrailParticleEffect> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(WispTrailParticleEffect wispTrailParticleEffect, ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new WispTrailParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, wispTrailParticleEffect, this.spriteProvider);
        }
    }
}
