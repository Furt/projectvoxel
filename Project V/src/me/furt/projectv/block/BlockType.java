package me.furt.projectv.block;

public enum BlockType {
	AIR(0),
	DIRT(1),
	GRASS(2),
	ROCK(3);
	private int blockId;
	BlockType(int i) {
		blockId = i;
	}
	
	public int getBlockId() {
		return blockId;
	}
}
