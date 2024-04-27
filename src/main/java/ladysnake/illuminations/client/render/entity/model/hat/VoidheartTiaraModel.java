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

public class VoidheartTiaraModel extends OverheadModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation("illuminations", "voidheart_tiara"), "main");

    public VoidheartTiaraModel(EntityRendererProvider.Context ctx) {
        super(ctx, MODEL_LAYER);
        ModelPart crown = this.head.getChild("crown");
        ModelPart south_r1 = crown.getChild("south_r1");
        ModelPart east_r1 = crown.getChild("east_r1");
        ModelPart north_r1 = crown.getChild("north_r1");
        ModelPart west_r1 = crown.getChild("west_r1");
        this.setRotationAngle(east_r1, -0.2618F, 1.5708F, 0.0F);
        this.setRotationAngle(north_r1, -0.2618F, 3.1416F, 0.0F);
        this.setRotationAngle(west_r1, -0.2618F, -1.5708F, 0.0F);
        this.setRotationAngle(south_r1, -0.2618F, 0.0F, 0.0F);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-4.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -4.0F, 0.0F));
        modelPartData2.addOrReplaceChild("west_r1", CubeListBuilder.create().texOffs(0, 36).addBox(-7.0F, -11.0F, 3.0F, 11.0F, 11.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -7.0F, 0.0F));
        modelPartData2.addOrReplaceChild("north_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, 3.0F, 9.0F, 8.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.5F, -7.0F, 0.0F));
        modelPartData2.addOrReplaceChild("east_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-5.0F, -11.0F, 3.0F, 10.0F, 11.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -6.0F, 1.0F));
        modelPartData2.addOrReplaceChild("south_r1", CubeListBuilder.create().texOffs(12, 39).addBox(-5.0F, -8.0F, 3.0F, 9.0F, 8.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.5F, -7.0F, 0.0F));
        return LayerDefinition.create(modelData, 48, 48);
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
