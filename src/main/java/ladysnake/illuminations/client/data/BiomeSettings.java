package ladysnake.illuminations.client.data;

import ladysnake.illuminations.client.enums.FireflySpawnRate;
import ladysnake.illuminations.client.enums.GlowwormSpawnRate;
import ladysnake.illuminations.client.enums.PlanktonSpawnRate;

public record BiomeSettings(FireflySpawnRate fireflySpawnRate, int fireflyColor, GlowwormSpawnRate glowwormSpawnRate, PlanktonSpawnRate planktonSpawnRate) {
    public BiomeSettings(FireflySpawnRate fireflySpawnRate, int fireflyColor, GlowwormSpawnRate glowwormSpawnRate, PlanktonSpawnRate planktonSpawnRate) {
        this.fireflySpawnRate = fireflySpawnRate;
        this.fireflyColor = fireflyColor;
        this.glowwormSpawnRate = glowwormSpawnRate;
        this.planktonSpawnRate = planktonSpawnRate;
    }

    public BiomeSettings withFireflySpawnRate(FireflySpawnRate value) {
        return new BiomeSettings(value, this.fireflyColor, this.glowwormSpawnRate, this.planktonSpawnRate);
    }

    public BiomeSettings withFireflyColor(int value) {
        return new BiomeSettings(this.fireflySpawnRate, value, this.glowwormSpawnRate, this.planktonSpawnRate);
    }

    public BiomeSettings withGlowwormSpawnRate(GlowwormSpawnRate value) {
        return new BiomeSettings(this.fireflySpawnRate, this.fireflyColor, value, this.planktonSpawnRate);
    }

    public BiomeSettings withPlanktonSpawnRate(PlanktonSpawnRate value) {
        return new BiomeSettings(this.fireflySpawnRate, this.fireflyColor, this.glowwormSpawnRate, value);
    }

    public FireflySpawnRate fireflySpawnRate() {
        return this.fireflySpawnRate;
    }

    public int fireflyColor() {
        return this.fireflyColor;
    }

    public GlowwormSpawnRate glowwormSpawnRate() {
        return this.glowwormSpawnRate;
    }

    public PlanktonSpawnRate planktonSpawnRate() {
        return this.planktonSpawnRate;
    }
}
