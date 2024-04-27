package ladysnake.illuminations.client.render.entity.model.hat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class HornsModel extends OverheadModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation("illuminations", "horns"), "main");

    public HornsModel(EntityRendererProvider.Context ctx) {
        super(ctx, MODEL_LAYER);
        ModelPart horns = this.head.getChild("horns");
        ModelPart south_r1 = horns.getChild("south_r1");
        ModelPart east_r1 = horns.getChild("east_r1");
        ModelPart west_r1 = horns.getChild("west_r1");
        this.setRotationAngle(west_r1, 0.0F, 2.5307F, 0.0F);
        this.setRotationAngle(east_r1, 0.0F, -2.5307F, 0.0F);
        this.setRotationAngle(south_r1, -0.2618F, 0.0F, 0.0F);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-4.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
        modelPartData2.addOrReplaceChild("west_r1", CubeListBuilder.create().texOffs(0, 39).addBox(-11.0F, -9.0F, 3.0F, 16.0F, 9.0F, 0.0F), PartPose.offset(6.0F, 2.0F, 3.0F));
        modelPartData2.addOrReplaceChild("east_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, -9.0F, 3.0F, 16.0F, 9.0F, 0.0F), PartPose.offset(-6.0F, 2.0F, 3.0F));
        modelPartData2.addOrReplaceChild("south_r1", CubeListBuilder.create().texOffs(7, 30).addBox(-4.0F, -8.0F, 3.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -4.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 48);
    }

    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.xRot = x;
        bone.yRot = y;
        bone.zRot = z;
    }
}
