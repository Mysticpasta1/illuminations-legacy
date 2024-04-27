package ladysnake.illuminations.client.enums;

public enum PlanktonSpawnRate {
    DISABLE(0.0F),
    LOW(2.0E-4F),
    MEDIUM(0.001F),
    HIGH(0.0025F);

    public final float spawnRate;

    private PlanktonSpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
    }
}
