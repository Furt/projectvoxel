package me.furt.projectv.core.block;

/**
 * ProjectV
 *
 * @author Furt
 */
public class StorageBlock extends Block {
    private int slots[];
    
    public StorageBlock(int id, String name, BlockSkin texture, int slots[]) {
        super(id, name, texture);
        this.slots = slots;
    }

    public int[] getSlots() {
        return slots;
    }

    public void setSlots(int[] slots) {
        this.slots = slots;
    }
    
    
}
