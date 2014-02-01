package me.furt.projectv.core.block;

/**
 * ProjectV
 *
 * @author Furt
 */
public class StorageBlock extends Block {
    private int slots[];
    
    public StorageBlock(int id, String name, int slots[]) {
        super(id, name);
        this.slots = slots;
    }

    public int[] getSlots() {
        return slots;
    }

    public void setSlots(int[] slots) {
        this.slots = slots;
    }
    
    
}
