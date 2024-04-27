package ladysnake.illuminations.client.render.entity.model.pet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ladysnake.illuminations.client.render.GlowyRenderLayer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class WillOWispModel extends Model {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation("illuminations", "will_o_wisp"), "main");
    private final ModelPart skull;

    public WillOWispModel(ModelPart root) {
        super(GlowyRenderLayer::get);
        this.skull = root.getChild("skull");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F).texOffs(0, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.skull.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
