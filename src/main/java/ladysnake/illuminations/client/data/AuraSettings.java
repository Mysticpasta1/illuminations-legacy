package ladysnake.illuminations.client.data;

public record AuraSettings(float spawnRate, int delay) {
    public AuraSettings(float spawnRate, int delay) {
        this.spawnRate = spawnRate;
        this.delay = delay;
    }

    public AuraSettings withSpawnRate(float value) {
        return new AuraSettings(value, this.delay);
    }

    public AuraSettings withDelay(int value) {
        return new AuraSettings(this.spawnRate, value);
    }

    public float spawnRate() {
        return this.spawnRate;
    }

    public int delay() {
        return this.delay;
    }
}
