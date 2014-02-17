package me.furt.projectv.core.block;

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
    // Block Texture //
    private BlockSkin texture;
    // Hardness: how long it takes to mine the block //
    private int hardness;
    // Resistance: for resistance to explosion/fire/etc //
    private int resistance;
    // If this block can be broken return true //
    private boolean breakable;
    // If this block can be currently used return true //
    private boolean isActive;
    
    public Block(int id, String name, BlockSkin blockSkin) {
        this.id = id;
        this.name = name;
        this.texture = blockSkin;
        this.hardness = 5;
        this.resistance = 5;
        this.breakable = true;
        this.isActive = true;
    }

    public Block(int id, String name, BlockSkin blockSkin, int hardness, int resistance, boolean breakable, boolean isActive) {
        this.id = id;
        this.name = name;
        this.texture = blockSkin;
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
    
    public BlockSkin getTexture() {
        return this.texture;
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
    
    public static enum Face {

        /**
         * Top Face
         */
        Top,
        /**
         * Bottom Face
         */
        Bottom,
        /**
         * Left Face
         */
        Left,
        /**
         * Right Face
         */
        Right,
        /**
         * Front Face
         */
        Front,
        /**
         * Back Face
         */
        Back
    };
}
