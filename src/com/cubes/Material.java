package com.cubes;

/**
 * ProjectV
 *
 * @author Furt
 */
public enum Material {
    STONE(1),
    DIRT(2),
    GRASS(3),
    COBBLESTONE(4),
    GRAVEL(5),
    SAND(6);
    
    private final int id;
    
    Material(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}
