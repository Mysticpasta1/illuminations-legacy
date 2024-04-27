package ladysnake.illuminations.client.particle.aura;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Optional;
import java.util.Random;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.particle.FireflyParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TwilightFireflyParticle extends FireflyParticle {
    private final Player owner;

    public TwilightFireflyParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.lifetime = 20;
        this.owner = world.getNearestPlayer(TargetingConditions.forNonCombat().range(1.0), this.x, this.y, this.z);
        this.maxHeight = 2;
        Optional.ofNullable(this.owner).map(Illuminations::getCosmeticData).ifPresentOrElse((data) -> {
            this.rCol = (float)data.getColorRed() / 255.0F;
            this.gCol = (float)data.getColorGreen() / 255.0F;
            this.bCol = (float)data.getColorBlue() / 255.0F;
            this.nextAlphaGoal = 1.0F;
        }, this::remove);
        this.setPos(this.x + getWanderingDistance(this.random), this.y + (double)this.random.nextFloat() * 2.0, this.z + getWanderingDistance(this.random));
    }

    public static double getWanderingDistance(RandomSource random) {
        return random.nextGaussian() / 5.0;
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
        if (this.owner != null) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ >= this.lifetime) {
                this.nextAlphaGoal = -0.05F;
                if (this.alpha < 0.0F) {
                    this.remove();
                }
            }

            if (this.alpha > this.nextAlphaGoal - 0.05F && this.alpha < this.nextAlphaGoal + 0.05F) {
                this.nextAlphaGoal = (new Random()).nextFloat();
            } else if (this.nextAlphaGoal > this.alpha) {
                this.alpha = Math.min(this.alpha + 0.05F, 1.0F);
            } else if (this.nextAlphaGoal < this.alpha) {
                this.alpha = Math.max(this.alpha - 0.05F, 0.0F);
            }

            this.targetChangeCooldown -= (new Vec3(this.x, this.y, this.z)).distanceToSqr(this.xo, this.yo, this.zo) < 0.0125 ? 10 : 1;
            if (this.level.getGameTime() % 20L == 0L && (this.xTarget == 0.0 && this.yTarget == 0.0 && this.zTarget == 0.0 || (new Vec3(this.x, this.y, this.z)).distanceToSqr(this.xTarget, this.yTarget, this.zTarget) < 9.0 || this.targetChangeCooldown <= 0)) {
                this.selectBlockTarget();
            }

            Vec3 targetVector = new Vec3(this.xTarget - this.x, this.yTarget - this.y, this.zTarget - this.z);
            double length = targetVector.length();
            targetVector = targetVector.scale(0.025 / length);
            if (!this.level.getBlockState(new BlockPos(this.x, this.y - 0.1, this.z)).getBlock().isPossibleToRespawnInThis()) {
                this.xd = 0.9 * this.xd + 0.1 * targetVector.x;
                this.yd = 0.05;
                this.zd = 0.9 * this.zd + 0.1 * targetVector.z;
            } else {
                this.xd = 0.9 * this.xd + 0.1 * targetVector.x;
                this.yd = 0.2 * this.yd + 0.1 * targetVector.y;
                this.zd = 0.9 * this.zd + 0.1 * targetVector.z;
            }

            if (!(new BlockPos(this.x, this.y, this.z)).equals(this.getTargetPosition())) {
                this.move(this.xd, this.yd, this.zd);
            }
        } else {
            this.remove();
        }

    }

    private void selectBlockTarget() {
        double groundLevel = 0.0;

        for(int i = 0; i < 20; ++i) {
            BlockState checkedBlock = this.level.getBlockState(new BlockPos(this.x, this.y - (double)i, this.z));
            if (!checkedBlock.getBlock().isPossibleToRespawnInThis()) {
                groundLevel = this.y - (double)i;
            }

            if (groundLevel != 0.0) {
                break;
            }
        }

        this.xTarget = this.owner.getX() + this.random.nextGaussian();
        this.yTarget = Math.min(Math.max(this.owner.getY() + this.random.nextGaussian(), groundLevel), groundLevel + (double)this.maxHeight);
        this.zTarget = this.owner.getZ() + this.random.nextGaussian();
        BlockPos targetPos = new BlockPos(this.xTarget, this.yTarget, this.zTarget);
        if (this.level.getBlockState(targetPos).isCollisionShapeFullBlock(this.level, targetPos) && this.level.getBlockState(targetPos).isRedstoneConductor(this.level, targetPos)) {
            ++this.yTarget;
        }

        this.targetChangeCooldown = this.random.nextInt() % 100;
    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new TwilightFireflyParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
