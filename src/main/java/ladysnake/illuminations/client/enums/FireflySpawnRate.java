package ladysnake.illuminations.client.enums;

public enum FireflySpawnRate {
    DISABLE(0.0F),
    LOW(2.0E-5F),
    MEDIUM(1.0E-4F),
    HIGH(2.5E-4F),
    ULTRA(3.0E-3F);

    public final float spawnRate;

    private FireflySpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
    }
}
