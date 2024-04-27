package ladysnake.illuminations.client.render.entity.model.hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ladysnake.illuminations.client.render.GlowyRenderLayer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public abstract class OverheadModel extends Model {
    public final ModelPart head;

    public OverheadModel(EntityRendererProvider.Context ctx, ModelLayerLocation entityModelLayer) {
        super(GlowyRenderLayer::get);
        this.head = ctx.bakeLayer(entityModelLayer).getChild("head");
    }

    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
