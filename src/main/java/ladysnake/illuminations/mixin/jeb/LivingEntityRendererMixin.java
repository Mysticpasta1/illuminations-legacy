package ladysnake.illuminations.mixin.jeb;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntityRenderer.class})
public class LivingEntityRendererMixin {
    public LivingEntityRendererMixin() {
    }

    @Inject(
        method = {"getRenderType"},
        at = {@At("RETURN")},
        cancellable = true
    )
    @Nullable
    protected void getRenderLayer(LivingEntity entity, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<RenderType> cir) {
        if (!(entity instanceof Sheep)) {
            RenderType baseLayer = (RenderType)cir.getReturnValue();
            if (entity.hasCustomName() && "jeb_".equals(entity.getName().toString())) {
            }
        }

    }
}
