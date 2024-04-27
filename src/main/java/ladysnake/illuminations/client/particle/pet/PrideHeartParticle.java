package ladysnake.illuminations.client.particle.pet;

import ladysnake.illuminations.client.render.entity.model.pet.PrideHeartModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PrideHeartParticle extends PlayerLanternParticle {
    protected PrideHeartParticle(ClientLevel world, double x, double y, double z, ResourceLocation texture, float red, float green, float blue) {
        super(world, x, y, z, texture, red, green, blue);
        this.model = new PrideHeartModel(Minecraft.getInstance().getEntityModels().bakeLayer(PrideHeartModel.MODEL_LAYER));
    }

    public void tick() {
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final ResourceLocation texture;
        private final float red;
        private final float green;
        private final float blue;

        public DefaultFactory(SpriteSet spriteProvider, ResourceLocation texture, float red, float green, float blue) {
            this.texture = texture;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new PrideHeartParticle(world, x, y, z, this.texture, this.red, this.green, this.blue);
        }
    }
}
