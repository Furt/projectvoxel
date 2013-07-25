package me.furt.projectv.block;

public enum BlockType {
	AIR(0),
	GRASS(1),
	DIRT(2),
	STONE(3),
	GRAVEL(4),
	COBBLESTONE(5),
	SAND(6),
	WATER(7);
	private int blockId;
	BlockType(int i) {
		blockId = i;
	}
	
	public int getBlockId() {
		return blockId;
	}
}
