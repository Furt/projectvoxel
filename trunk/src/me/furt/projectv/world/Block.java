package me.furt.projectv.world;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Block {

    // Block ID //
    private int id;
    // Block Name //
    private String name;
    // Hardness: how long it takes to mine the block //
    private int hardness;
    // Resistance: for resistance to explosion/fire/etc //
    private int resistance;
    // If this block can be broken return true //
    private boolean breakable;
    // If this block can be currently used return true //
    private boolean isActive;
    
    
    public Block() {
        
    }
    
    public Block(int id, String name, int hardness, int resistance, boolean breakable, boolean isActive) {
        this.id = id;
        this.name = name;
        this.hardness = hardness;
        this.resistance = resistance;
        this.breakable = breakable;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean IsActive) {
        this.isActive = IsActive;
    }
    
    
}
