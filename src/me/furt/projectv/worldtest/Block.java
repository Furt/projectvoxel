package me.furt.projectv.worldtest;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Block {

    private int id;
    private String name;
    private int hardness;
    private int resistance;
    private boolean breakable;
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
