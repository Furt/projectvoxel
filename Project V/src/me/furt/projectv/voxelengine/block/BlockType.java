package me.furt.projectv.voxelengine.block;

public enum BlockType {
	AIR(0), GRASS(1), DIRT(2), STONE(0);

	private int blockId;

	BlockType(int i) {
		blockId = i;
	}

	public int getBlockId() {
		return blockId;
	}

}
