package ladysnake.illuminations.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import ladysnake.illuminations.client.config.Config;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlanktonParticle extends TextureSheetParticle {
    private static final float BLINK_STEP = 0.01F;
    private static final Random RANDOM = new Random();
    private final SpriteSet spriteProvider;
    protected float nextAlphaGoal = 0.0F;
    private BlockPos lightTarget;
    private double xTarget;
    private double yTarget;
    private double zTarget;
    private int targetChangeCooldown = 0;

    private PlanktonParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.quadSize *= 0.05F + (new Random()).nextFloat() * 0.05F;
        this.lifetime = ThreadLocalRandom.current().nextInt(400, 1201);
        this.hasPhysics = true;
        this.setSpriteFromAge(spriteProvider);
        this.rCol = 0.0F;
        this.gCol = 0.25F + (new Random()).nextFloat() * 0.25F;
        this.bCol = 1.0F;
        this.alpha = 0.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
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
        float a = Math.min(1.0F, Math.max(0.0F, this.alpha));
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, minV + (maxV - minV) / 2.0F).color(this.rCol, this.gCol, this.bCol, a).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, a).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, a).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(minU, minV + (maxV - minV) / 2.0F).color(this.rCol, this.gCol, this.bCol, a).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[0].x(), (double)Vec3fs[0].y(), (double)Vec3fs[0].z()).uv(maxU, maxV).color(1.0F, 1.0F, 1.0F, a * (float)Config.getFireflyWhiteAlpha() / 100.0F).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[1].x(), (double)Vec3fs[1].y(), (double)Vec3fs[1].z()).uv(maxU, minV + (maxV - minV) / 2.0F).color(1.0F, 1.0F, 1.0F, a * (float)Config.getFireflyWhiteAlpha() / 100.0F).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[2].x(), (double)Vec3fs[2].y(), (double)Vec3fs[2].z()).uv(minU, minV + (maxV - minV) / 2.0F).color(1.0F, 1.0F, 1.0F, a * (float)Config.getFireflyWhiteAlpha() / 100.0F).uv2(l).endVertex();
        vertexConsumer.vertex((double)Vec3fs[3].x(), (double)Vec3fs[3].y(), (double)Vec3fs[3].z()).uv(minU, maxV).color(1.0F, 1.0F, 1.0F, a * (float)Config.getFireflyWhiteAlpha() / 100.0F).uv2(l).endVertex();
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.nextAlphaGoal = -0.01F;
            if (this.alpha < 0.0F) {
                this.remove();
            }
        }

        if (this.alpha > this.nextAlphaGoal - 0.01F && this.alpha < this.nextAlphaGoal + 0.01F) {
            this.nextAlphaGoal = (new Random()).nextFloat();
        } else if (this.nextAlphaGoal > this.alpha) {
            this.alpha += 0.01F;
        } else if (this.nextAlphaGoal < this.alpha) {
            this.alpha -= 0.01F;
        }

        this.targetChangeCooldown -= (new Vec3(this.x, this.y, this.z)).distanceToSqr(this.xo, this.yo, this.zo) < 0.0125 ? 10 : 1;
        if (this.level.getGameTime() % 20L == 0L && (this.xTarget == 0.0 && this.yTarget == 0.0 && this.zTarget == 0.0 || (new Vec3(this.x, this.y, this.z)).distanceToSqr(this.xTarget, this.yTarget, this.zTarget) < 9.0 || this.targetChangeCooldown <= 0)) {
            this.selectBlockTarget();
        }

        Vec3 targetVector = new Vec3(this.xTarget - this.x, this.yTarget - this.y, this.zTarget - this.z);
        double length = targetVector.length();
        targetVector = targetVector.scale(0.001 / length);
        if (!this.level.getBlockState(new BlockPos(this.x, this.y - 0.1, this.z)).getFluidState().is(FluidTags.WATER)) {
            this.xd = 0.9 * this.xd + 0.1 * targetVector.x;
            this.yd = 0.05;
            this.zd = 0.9 * this.zd + 0.1 * targetVector.z;
        } else {
            this.xd = 0.9 * this.xd + 0.1 * targetVector.x;
            this.yd = 0.9 * this.yd + 0.1 * targetVector.y;
            this.zd = 0.9 * this.zd + 0.1 * targetVector.z;
        }

        if (!(new BlockPos(this.x, this.y, this.z)).equals(this.getTargetPosition())) {
            this.move(this.xd, this.yd, this.zd);
        }

    }

    private void selectBlockTarget() {
        double groundLevel = 0.0;

        for(int i = 0; i < 20; ++i) {
            BlockState checkedBlock = this.level.getBlockState(new BlockPos(this.x, this.y - (double)i, this.z));
            if (checkedBlock.getFluidState().is(FluidTags.WATER)) {
                groundLevel = this.y - (double)i;
            }

            if (groundLevel != 0.0) {
                break;
            }
        }

        this.xTarget = this.x + this.random.nextGaussian() * 10.0;
        this.yTarget = Math.max(this.y + this.random.nextGaussian() * 2.0, groundLevel);
        this.zTarget = this.z + this.random.nextGaussian() * 10.0;
        BlockPos targetPos = new BlockPos(this.xTarget, this.yTarget, this.zTarget);
        if (this.level.getBlockState(targetPos).isCollisionShapeFullBlock(this.level, targetPos) && this.level.getBlockState(targetPos).isRedstoneConductor(this.level, targetPos)) {
            ++this.yTarget;
        }

        this.targetChangeCooldown = this.random.nextInt() % 100;
    }

    public BlockPos getTargetPosition() {
        return new BlockPos(this.xTarget, this.yTarget + 0.5, this.zTarget);
    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new PlanktonParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
