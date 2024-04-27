package ladysnake.illuminations.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import ladysnake.illuminations.client.Illuminations;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WispTrailParticleEffect implements ParticleOptions {
    public static final Codec<WispTrailParticleEffect> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.FLOAT.fieldOf("r").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.red;
        }), Codec.FLOAT.fieldOf("g").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.green;
        }), Codec.FLOAT.fieldOf("b").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.blue;
        }), Codec.FLOAT.fieldOf("re").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.redEvolution;
        }), Codec.FLOAT.fieldOf("ge").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.greenEvolution;
        }), Codec.FLOAT.fieldOf("be").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.blueEvolution;
        })).apply(instance, WispTrailParticleEffect::new);
    });
    public static final ParticleOptions.Deserializer<WispTrailParticleEffect> PARAMETERS_FACTORY = new ParticleOptions.Deserializer<WispTrailParticleEffect>() {

        public WispTrailParticleEffect fromCommand(ParticleType<WispTrailParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float r = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float g = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float b = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float re = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float ge = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float be = (float)stringReader.readDouble();
            return new WispTrailParticleEffect(r, g, b, re, ge, be);
        }

        public WispTrailParticleEffect fromNetwork(ParticleType<WispTrailParticleEffect> particleType, FriendlyByteBuf packetByteBuf) {
            return new WispTrailParticleEffect(packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float redEvolution;
    private final float greenEvolution;
    private final float blueEvolution;

    public WispTrailParticleEffect(float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.redEvolution = redEvolution;
        this.greenEvolution = greenEvolution;
        this.blueEvolution = blueEvolution;
    }

    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
        buf.writeFloat(this.redEvolution);
        buf.writeFloat(this.greenEvolution);
        buf.writeFloat(this.blueEvolution);
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution);
    }

    public ParticleType<WispTrailParticleEffect> getType() {
        return (ParticleType)Illuminations.WISP_TRAIL.get();
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRedEvolution() {
        return this.redEvolution;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreenEvolution() {
        return this.greenEvolution;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlueEvolution() {
        return this.blueEvolution;
    }
}
