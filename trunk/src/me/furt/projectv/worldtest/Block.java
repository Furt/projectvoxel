package me.furt.projectv.worldtest;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Block {

    private boolean IsActive;
    private BlockType Type;

    public enum BlockType {

        Default(0),
        Grass(1),
        Dirt(2),
        Water(3),
        Stone(4),
        Wood(5),
        Sand(6),
        BlockTypes(7);
        private int BlockID;

        BlockType(int i) {
            BlockID = i;
        }

        public int GetID() {
            return BlockID;
        }
    }

    public Block(BlockType type) {
        Type = type;
    }

    public boolean IsActive() {
        return IsActive;
    }

    public void SetActive(boolean active) {
        IsActive = active;
    }

    public int GetID() {
        return Type.GetID();
    }
}
