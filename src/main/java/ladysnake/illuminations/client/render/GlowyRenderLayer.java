package ladysnake.illuminations.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;

public class GlowyRenderLayer extends RenderType {
    public GlowyRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.Mode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderType get(ResourceLocation texture) {
        RenderType.CompositeState multiPhaseParameters = CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setTransparencyState(TransparencyStateShard.TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(NO_OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER).createCompositeState(true);
        return create("crown", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 256, false, false, multiPhaseParameters);
    }
}
