package ladysnake.illuminations.mixin.jeb;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderLayer.class})
public abstract class FeatureRendererMixin {
    @Unique
    private static boolean isRgb;

    public FeatureRendererMixin() {
    }

    @Inject(
        method = {"renderColoredCutoutModel"},
        at = {@At("HEAD")}
    )
    private static <T extends LivingEntity> void captureEntity(EntityModel<T> model, ResourceLocation texture, PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float red, float green, float blue, CallbackInfo ci) {
        isRgb = !(entity instanceof Sheep) && entity.hasCustomName() && "jeb_".equals(entity.getName().getString().toString());
    }

    @ModifyArg(
        method = {"renderColoredCutoutModel"},
        at = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
)
    )
    private static RenderType replaceRenderLayer(RenderType base) {
        if (isRgb) {
        }

        return base;
    }
}
