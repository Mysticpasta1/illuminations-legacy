package ladysnake.illuminations.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import ladysnake.illuminations.client.render.GlowyRenderLayer;
import ladysnake.illuminations.client.render.entity.model.pet.WillOWispModel;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class WillOWispParticle extends Particle {
    public final ResourceLocation texture;
    protected final float redEvolution;
    protected final float greenEvolution;
    protected final float blueEvolution;
    final Model model;
    final RenderType layer;
    public float yaw;
    public float pitch;
    public float prevYaw;
    public float prevPitch;
    public float speedModifier;
    protected double xTarget;
    protected double yTarget;
    protected double zTarget;
    protected int targetChangeCooldown = 0;
    protected int timeInSolid = -1;

    protected WillOWispParticle(ClientLevel world, double x, double y, double z, ResourceLocation texture, float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
        super(world, x, y, z);
        this.texture = texture;
        this.model = new WillOWispModel(Minecraft.getInstance().getEntityModels().bakeLayer(WillOWispModel.MODEL_LAYER));
        this.layer = RenderType.entityTranslucent(texture);
        this.gravity = 0.0F;
        this.lifetime = 600 + this.random.nextInt(600);
        this.speedModifier = 0.1F + Math.max(0.0F, this.random.nextFloat() - 0.1F);
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.redEvolution = redEvolution;
        this.blueEvolution = blueEvolution;
        this.greenEvolution = greenEvolution;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 vec3d = camera.getPosition();
        float f = (float)(Mth.lerp((double)tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float)(Mth.lerp((double)tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float)(Mth.lerp((double)tickDelta, this.zo, this.z) - vec3d.z());
        PoseStack matrixStack = new PoseStack();
        matrixStack.translate((double)f, (double)g, (double)h);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(g, this.prevYaw, this.yaw) - 180.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(g, this.prevPitch, this.pitch)));
        matrixStack.scale(0.5F, -0.5F, 0.5F);
        matrixStack.translate(0.0, -1.0, 0.0);
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer2 = immediate.getBuffer(GlowyRenderLayer.get(this.texture));
        if (this.alpha > 0.0F) {
            this.model.renderToBuffer(matrixStack, vertexConsumer2, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        immediate.endBatch();
    }

    public void tick() {
        if (this.xo == this.x && this.yo == this.y && this.zo == this.z) {
            this.selectBlockTarget();
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            for(int i = 0; i < 25; ++i) {
                this.level.addParticle(new WispTrailParticleEffect(this.rCol, this.gCol, this.bCol, this.redEvolution, this.greenEvolution, this.blueEvolution), this.x + this.random.nextGaussian() / 15.0, this.y + this.random.nextGaussian() / 15.0, this.z + this.random.nextGaussian() / 15.0, 0.0, 0.0, 0.0);
                this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SOUL_SAND.defaultBlockState()), this.x + this.random.nextGaussian() / 10.0, this.y + this.random.nextGaussian() / 10.0, this.z + this.random.nextGaussian() / 10.0, this.random.nextGaussian() / 20.0, this.random.nextGaussian() / 20.0, this.random.nextGaussian() / 20.0);
            }

            this.level.playLocalSound(new BlockPos(this.x, this.y, this.z), SoundEvents.SOUL_ESCAPE, SoundSource.AMBIENT, 1.0F, 1.5F, true);
            this.level.playLocalSound(new BlockPos(this.x, this.y, this.z), SoundEvents.SOUL_SAND_BREAK, SoundSource.AMBIENT, 1.0F, 1.0F, true);
            this.remove();
        }

        this.targetChangeCooldown -= (new Vec3(this.x, this.y, this.z)).distanceToSqr(this.xo, this.yo, this.zo) < 0.0125 ? 10 : 1;
        if (this.level.getGameTime() % 20L == 0L && (this.xTarget == 0.0 && this.yTarget == 0.0 && this.zTarget == 0.0 || (new Vec3(this.x, this.y, this.z)).distanceToSqr(this.xTarget, this.yTarget, this.zTarget) < 9.0 || this.targetChangeCooldown <= 0)) {
            this.selectBlockTarget();
        }

        Vec3 targetVector = new Vec3(this.xTarget - this.x, this.yTarget - this.y, this.zTarget - this.z);
        double length = targetVector.length();
        targetVector = targetVector.scale((double)this.speedModifier / length);
        this.xd = 0.9 * this.xd + 0.1 * targetVector.x;
        this.yd = 0.9 * this.yd + 0.1 * targetVector.y;
        this.zd = 0.9 * this.zd + 0.1 * targetVector.z;
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        Vec3 vec3d = new Vec3(this.xd, this.yd, this.zd);
        float f = (float)Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
        this.yaw = (float)(Mth.atan2(vec3d.x, vec3d.z) * 57.2957763671875);
        this.pitch = (float)(Mth.atan2(vec3d.y, (double)f) * 57.2957763671875);

        for(int i = 0; (float)i < 10.0F * this.speedModifier; ++i) {
            if (this.level.getBlockState(new BlockPos(this.x, this.y, this.z)).is(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                this.level.addParticle(ParticleTypes.SOUL, this.x + this.random.nextGaussian() / 10.0, this.y + this.random.nextGaussian() / 10.0, this.z + this.random.nextGaussian() / 10.0, this.random.nextGaussian() / 20.0, this.random.nextGaussian() / 20.0, this.random.nextGaussian() / 20.0);
            } else {
                this.level.addParticle(new WispTrailParticleEffect(this.rCol, this.gCol, this.bCol, this.redEvolution, this.greenEvolution, this.blueEvolution), this.x + this.random.nextGaussian() / 15.0, this.y + this.random.nextGaussian() / 15.0, this.z + this.random.nextGaussian() / 15.0, 0.0, 0.0, 0.0);
            }
        }

        if (!(new BlockPos(this.x, this.y, this.z)).equals(this.getTargetPosition())) {
            this.move(this.xd, this.yd, this.zd);
        }

        if (this.random.nextInt(20) == 0) {
            this.level.playLocalSound(new BlockPos(this.x, this.y, this.z), SoundEvents.SOUL_ESCAPE, SoundSource.AMBIENT, 1.0F, 1.5F, true);
        }

        BlockPos pos = new BlockPos(this.x, this.y, this.z);
        if (!this.level.getBlockState(pos).isAir()) {
            if (this.timeInSolid > -1) {
                ++this.timeInSolid;
            }
        } else {
            this.timeInSolid = 0;
        }

        if (this.timeInSolid > 25) {
            this.remove();
        }

    }

    public void move(double dx, double dy, double dz) {
        double d = dx;
        double e = dy;
        if (this.hasPhysics && !this.level.getBlockState(new BlockPos(this.x + dx, this.y + dy, this.z + dz)).is(BlockTags.SOUL_FIRE_BASE_BLOCKS) && (dx != 0.0 || dy != 0.0 || dz != 0.0)) {
            Vec3 vec3d = Entity.collideBoundingBox((Entity)null, new Vec3(dx, dy, dz), this.getBoundingBox(), this.level, List.of());
            dx = vec3d.x;
            dy = vec3d.y;
            dz = vec3d.z;
        }

        if (dx != 0.0 || dy != 0.0 || dz != 0.0) {
            this.setBoundingBox(this.getBoundingBox().move(dx, dy, dz));
            this.setLocationFromBoundingbox();
        }

        this.onGround = dy != dy && e < 0.0 && !this.level.getBlockState(new BlockPos(this.x, this.y, this.z)).is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
        if (d != dx) {
            this.xd = 0.0;
        }

        if (dz != dz) {
            this.zd = 0.0;
        }

    }

    public BlockPos getTargetPosition() {
        return new BlockPos(this.xTarget, this.yTarget + 0.5, this.zTarget);
    }

    private void selectBlockTarget() {
        this.xTarget = this.x + this.random.nextGaussian() * 10.0;
        this.yTarget = this.y + this.random.nextGaussian() * 10.0;
        this.zTarget = this.z + this.random.nextGaussian() * 10.0;
        BlockPos targetPos = new BlockPos(this.xTarget, this.yTarget, this.zTarget);
        if (this.level.getBlockState(targetPos).isCollisionShapeFullBlock(this.level, targetPos) && !this.level.getBlockState(targetPos).is(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
            this.targetChangeCooldown = 0;
        } else {
            this.speedModifier = 0.1F + Math.max(0.0F, this.random.nextFloat() - 0.1F);
            this.targetChangeCooldown = this.random.nextInt() % (int)(100.0F / this.speedModifier);
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
            return new WillOWispParticle(world, x, y, z, this.texture, this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution);
        }
    }
}
