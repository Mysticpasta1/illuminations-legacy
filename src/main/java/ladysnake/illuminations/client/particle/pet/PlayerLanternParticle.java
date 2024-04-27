package ladysnake.illuminations.client.particle.pet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import ladysnake.illuminations.client.render.GlowyRenderLayer;
import ladysnake.illuminations.client.render.entity.model.pet.LanternModel;
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
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PlayerLanternParticle extends Particle {
    public final ResourceLocation texture;
    final RenderType layer;
    public float yaw;
    public float pitch;
    public float prevYaw;
    public float prevPitch;
    protected Player owner;
    Model model;

    protected PlayerLanternParticle(ClientLevel world, double x, double y, double z, ResourceLocation texture, float red, float green, float blue) {
        super(world, x, y, z);
        this.texture = texture;
        this.model = new LanternModel(Minecraft.getInstance().getEntityModels().bakeLayer(LanternModel.MODEL_LAYER));
        this.layer = RenderType.entityTranslucent(texture);
        this.gravity = 0.0F;
        this.lifetime = 35;
        this.owner = world.getNearestPlayer(TargetingConditions.forNonCombat().range(1.0), this.x, this.y, this.z);
        if (this.owner == null) {
            this.remove();
        }

        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.alpha = 0.0F;
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
        if (this.age > 10) {
            this.alpha = 1.0F;
        } else {
            this.alpha = 0.0F;
        }

        if (this.owner != null) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ >= this.lifetime) {
                this.remove();
            }

            this.setPos(this.owner.getX() + Math.cos((double)(this.owner.yBodyRot / 50.0F)) * 0.5, this.owner.getY() + (double)this.owner.getBbHeight() + 0.5 + Math.sin((double)((float)this.owner.tickCount / 12.0F)) / 12.0, this.owner.getZ() - Math.cos((double)(this.owner.yBodyRot / 50.0F)) * 0.5);
            this.prevYaw = this.yaw;
            this.yaw = (float)(this.owner.tickCount * 2);
        } else {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final ResourceLocation texture;
        private final float red;
        private final float green;
        private final float blue;

        public DefaultFactory(SpriteSet spriteProvider, ResourceLocation texture, float red, float green, float blue) {
            this.texture = texture;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new PlayerLanternParticle(world, x, y, z, this.texture, this.red, this.green, this.blue);
        }
    }
}
