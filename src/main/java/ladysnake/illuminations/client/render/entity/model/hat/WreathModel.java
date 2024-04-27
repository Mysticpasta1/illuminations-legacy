package ladysnake.illuminations.client.render.entity.model.hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WreathModel extends OverheadModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation("illuminations", "wreath"), "main");

    public WreathModel(EntityRendererProvider.Context ctx) {
        super(ctx, MODEL_LAYER);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-4.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData1.addOrReplaceChild("wreath", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -34.5F, -5.0F, 10.0F, 5.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(modelData, 48, 16);
    }

    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
