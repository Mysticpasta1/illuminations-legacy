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
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhostlyAuraParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final float MAXIMUM_ALPHA = 0.02F;
    private final Player owner;
    private final int variant;
    private final SpriteSet spriteProvider;
    protected float alpha;
    protected float offsetX;
    protected float offsetZ;
    protected float offsetY;

    public GhostlyAuraParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.variant = RANDOM.nextInt(4);
        this.alpha = 0.0F;
        this.offsetX = RANDOM.nextFloat() * 0.7F - 0.35F;
        this.offsetZ = RANDOM.nextFloat() * 0.7F - 0.35F;
        this.offsetY = 0.0F;
        this.spriteProvider = spriteProvider;
        this.owner = world.getNearestPlayer(TargetingConditions.forNonCombat().range(1.0), this.x, this.y, this.z);
        this.quadSize *= 1.0F + RANDOM.nextFloat();
        this.lifetime = RANDOM.nextInt(5) + 8;
        this.hasPhysics = true;
        this.setSprite(spriteProvider.get(this.variant, 3));
        if (this.owner != null) {
            this.rCol = 1.0F;
            this.gCol = 1.0F;
            this.bCol = 1.0F;
            this.setPos(this.owner.getX() + (double)this.offsetX, this.owner.getY() + (double)this.offsetY, this.owner.getZ() + (double)this.offsetZ);
        } else {
            this.remove();
        }

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

        float minV;
        for(int k = 0; k < 4; ++k) {
            Vector3f Vec3f2 = Vec3fs[k];
            minV = Vec3f2.y();
            Vec3f2.set(Vec3f2.x(), 0.0F, Vec3f2.z());
            Vec3f2.transform(quaternion2);
            Vec3f2.set(Vec3f2.x() / (1.0F + this.offsetY * this.offsetY), minV * this.offsetY, Vec3f2.z() / (1.0F + this.offsetY * this.offsetY));
            Vec3f2.mul(j);
            Vec3f2.add(f, g, h);
        }

        float minU = this.getU0();
        float maxU = this.getU1();
        minV = this.getV0();
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
        if (this.owner != null) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ < this.lifetime) {
                this.alpha = (float)((double)this.alpha + 0.01);
            } else {
                this.alpha = (float)((double)this.alpha - 0.01);
                if (this.alpha <= 0.0F) {
                    this.remove();
                }
            }

            this.offsetY = (float)((double)this.offsetY + 0.1);
            this.setPos(this.owner.getX() + (double)this.offsetX, this.owner.getY() + (double)this.offsetY, this.owner.getZ() + (double)this.offsetZ);
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
            return new GhostlyAuraParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
