package ladysnake.illuminations.client.render.entity.model.pet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ladysnake.illuminations.client.render.GlowyRenderLayer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class PrideHeartModel extends Model {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation("illuminations", "pride_heart"), "main");
    private final ModelPart heart;

    public PrideHeartModel(ModelPart root) {
        super(GlowyRenderLayer::get);
        this.heart = root.getChild("heart");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("heart", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));
        modelPartData.getChild("heart").addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(22, 0).addBox(1.0F, -4.0F, -1.5F, 0.0F, 3.0F, 3.0F), PartPose.rotation(0.0F, 0.0F, -0.7854F));
        modelPartData.getChild("heart").addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -4.0F, -1.5F, 0.0F, 3.0F, 3.0F).texOffs(0, 0).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F), PartPose.rotation(0.0F, 0.0F, 0.7854F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.heart.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
