package ladysnake.illuminations.mixin;

import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.data.AuraData;
import ladysnake.illuminations.client.data.PlayerCosmeticData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Player.class})
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract void remove(RemovalReason arg);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
        method = {"tick"},
        at = {@At("RETURN")}
    )
    public void tick(CallbackInfo callbackInfo) {
        PlayerCosmeticData cosmeticData = Illuminations.getCosmeticData((Player) (Object) this);
        if (cosmeticData != null) {
            String playerAura = cosmeticData.getAura();
            if (Config.shouldDisplayCosmetics() && playerAura != null && Illuminations.AURAS_DATA.containsKey(playerAura) && (Config.getViewAurasFP() || Minecraft.getInstance().gameRenderer.getMainCamera().isDetached() || Minecraft.getInstance().player != selfPlayer()) && !this.isInvisible() && Illuminations.AURAS_DATA.containsKey(playerAura)) {
                AuraData aura = (AuraData)Illuminations.AURAS_DATA.get(playerAura);
                if (((AuraData)Illuminations.AURAS_DATA.get(playerAura)).shouldAddParticle(this.random, this.tickCount)) {
                    this.level.addParticle(aura.particle(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
                }
            }

            String playerPet = cosmeticData.getPet();
            if (Config.shouldDisplayCosmetics() && playerPet != null && Illuminations.PETS_DATA.containsKey(playerPet) && (Config.getViewAurasFP() || Minecraft.getInstance().gameRenderer.getMainCamera().isDetached() || Minecraft.getInstance().player != selfPlayer()) && !this.isInvisible() && Illuminations.PETS_DATA.containsKey(playerPet)) {
                SimpleParticleType overhead = (SimpleParticleType)Illuminations.PETS_DATA.get(playerPet);
                if (this.tickCount % 20 == 0) {
                    this.level.addParticle(overhead, this.getX() + Math.cos((double)(this.yBodyRot / 50.0F)) * 0.5, this.getY() + (double)this.getBbHeight() + 0.5 + Math.sin((double)((float)this.tickCount / 12.0F)) / 12.0, this.getZ() - Math.cos((double)(this.yBodyRot / 50.0F)) * 0.5, 0.0, 0.0, 0.0);
                }
            }
        }

    }

    private Player selfPlayer() {
        return (Player) self();
    }
}
