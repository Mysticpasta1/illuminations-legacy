package ladysnake.illuminations.client.enums;

public enum GlowwormSpawnRate {
    DISABLE(0.0F),
    LOW(4.0E-5F),
    MEDIUM(2.0E-4F),
    HIGH(5.0E-4F);

    public final float spawnRate;

    private GlowwormSpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
    }
}
