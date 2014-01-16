package me.furt.projectv.noise;

/**
 * ProjectV
 *
 * @author Furt
 */
public enum BiomeType {

    WATER(0, "Water"),
    SWAMP(1, "Swamp"),
    DESERT(2, "Desert"),
    PLAINS(3, "Plains"),
    FOREST(4, "Forest"),
    HILLS(5, "Hills"),
    MOUNTAINS(6, "Mountains"),
    FLATLANDS(7, "Flatlands");
    private int id = 0;
    private String name = "";

    private BiomeType(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    private BiomeType(int id) {
        this.id = id;
    }
    
    private BiomeType(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}
