package ladysnake.illuminations.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.stream.Collectors;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.data.OverheadData;
import ladysnake.illuminations.client.data.PlayerCosmeticData;
import ladysnake.illuminations.client.render.GlowyRenderLayer;
import ladysnake.illuminations.client.render.entity.model.hat.OverheadModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class OverheadFeatureRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private final Map<String, ResolvedOverheadData> models;

    public OverheadFeatureRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> featureRendererContext, EntityRendererProvider.Context loader) {
        super(featureRendererContext);
        this.models = Illuminations.OVERHEADS_DATA.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (data) -> new ResolvedOverheadData(data.getValue().getTexture(), data.getValue().createModel(loader))));
    }

    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        PlayerCosmeticData cosmeticData = Illuminations.getCosmeticData(entity);
        if (Config.shouldDisplayCosmetics() && cosmeticData != null && !entity.isInvisible()) {
            String playerOverhead = cosmeticData.getOverhead();
            if (playerOverhead != null) {
                ResolvedOverheadData resolvedOverheadData = this.models.get(playerOverhead);
                if (resolvedOverheadData != null) {
                    ResourceLocation texture = resolvedOverheadData.texture();
                    OverheadModel model = resolvedOverheadData.model();
                    model.head.x = this.getParentModel().head.x;
                    model.head.y = this.getParentModel().head.y;
                    model.head.xRot = this.getParentModel().head.xRot;
                    model.head.yRot = this.getParentModel().head.yRot;
                    model.renderToBuffer(matrices, vertexConsumers.getBuffer(GlowyRenderLayer.get(texture)), 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }

    }

    private record ResolvedOverheadData(ResourceLocation texture, OverheadModel model) {
        private ResolvedOverheadData(ResourceLocation texture, OverheadModel model) {
            this.texture = texture;
            this.model = model;
        }

        public ResourceLocation texture() {
            return this.texture;
        }

        public OverheadModel model() {
            return this.model;
        }
    }
}
