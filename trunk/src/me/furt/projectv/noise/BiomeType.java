package me.furt.projectv.noise;

/**
 * ProjectV
 *
 * @author Furt
 */
public enum BiomeType {

    WATER(0),
    SWAMP(1),
    DESERT(2),
    PLAINS(3),
    FOREST(4),
    HILLS(5),
    MOUNTAINS(6);
    private int id = 0;

    private BiomeType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
