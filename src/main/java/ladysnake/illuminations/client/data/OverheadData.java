package ladysnake.illuminations.client.data;

import java.util.function.Function;
import ladysnake.illuminations.client.render.entity.model.hat.OverheadModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class OverheadData {
    private final Function<EntityRendererProvider.Context, OverheadModel> model;
    private final ResourceLocation texture;

    public OverheadData(Function<EntityRendererProvider.Context, OverheadModel> model, String textureName) {
        this.model = model;
        this.texture = new ResourceLocation("illuminations", "textures/entity/" + textureName + ".png");
    }

    public OverheadModel createModel(EntityRendererProvider.Context ctx) {
        return (OverheadModel)this.model.apply(ctx);
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }
}
