package ladysnake.illuminations.client.particle.aura;

import java.util.Objects;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.data.PlayerCosmeticData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PrismaticConfettiParticle extends ConfettiParticle {
    public PrismaticConfettiParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        Player owner = world.getNearestPlayer(TargetingConditions.forNonCombat().range(1.0), this.x, this.y, this.z);
        if (owner != null && owner.getUUID() != null && Illuminations.getCosmeticData(owner) != null) {
            PlayerCosmeticData data = (PlayerCosmeticData)Objects.requireNonNull(Illuminations.getCosmeticData(owner));
            this.rCol = (float)data.getColorRed() / 255.0F;
            this.gCol = (float)data.getColorGreen() / 255.0F;
            this.bCol = (float)data.getColorBlue() / 255.0F;
        } else {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public DefaultFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new PrismaticConfettiParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
