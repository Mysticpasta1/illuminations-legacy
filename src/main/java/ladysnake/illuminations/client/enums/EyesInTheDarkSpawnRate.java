package ladysnake.illuminations.client.enums;

public enum EyesInTheDarkSpawnRate {
    LOW(2.0E-5F),
    MEDIUM(1.0E-4F),
    HIGH(2.5E-4F);

    public final float spawnRate;

    private EyesInTheDarkSpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
    }
}
