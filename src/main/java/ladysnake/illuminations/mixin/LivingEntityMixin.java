package ladysnake.illuminations.mixin;

import java.time.LocalDate;
import java.time.Month;
import ladysnake.illuminations.client.Illuminations;
import ladysnake.illuminations.client.config.Config;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean isInvertedHealAndHarm();

    @Inject(
        method = {"die"},
        at = {@At("RETURN")}
    )
    public void onDeath(DamageSource source, CallbackInfo callbackInfo) {
        if (this.isInvertedHealAndHarm() && this.random.nextInt(5) == 0 && Illuminations.isNightTime(this.level) && (Config.getHalloweenFeatures() == HalloweenFeatures.ENABLE && LocalDate.now().getMonth() == Month.OCTOBER || Config.getHalloweenFeatures() == HalloweenFeatures.ALWAYS)) {
            this.level.playSound((Player)null, this.blockPosition(), SoundEvents.VEX_CHARGE, SoundSource.AMBIENT, 1.0F, 0.8F);
            if (Illuminations.POLTERGEIST.isPresent()) {
                this.level.addParticle((ParticleOptions)Illuminations.POLTERGEIST.get(), true, this.getX() + 0.5, this.getEyeY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }
}
