package com.cubes;

import com.cubes.network.*;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import java.io.IOException;
import java.util.ArrayList;
import me.furt.projectv.block.Block_Grass;
import me.furt.projectv.block.Block_Sand;
import me.furt.projectv.block.Block_Stone;
import me.furt.projectv.block.Block_Water;
import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;
import me.furt.projectv.libnoise.module.Billow;
import me.furt.projectv.libnoise.module.Perlin;
import me.furt.projectv.libnoise.module.RidgedMulti;
import me.furt.projectv.libnoise.module.ScaleBias;
import me.furt.projectv.libnoise.module.Select;
import me.furt.projectv.libnoise.module.Turbulence;
import me.furt.projectv.libnoise.util.NoiseMap;
import me.furt.projectv.libnoise.util.NoiseMapBuilderPlane;

public class TerrainControl extends AbstractControl implements BitSerializable {

    /**
     *
     * @param settings
     * @param chunksCount
     */
    public TerrainControl(CubesSettings settings, Vector3Int chunksCount) {
        this.settings = settings;
        initializeChunks(chunksCount);
    }
    private CubesSettings settings;
    private ChunkControl[][][] chunks;
    private ArrayList<ChunkListener> chunkListeners = new ArrayList<ChunkListener>();

    /**
     *
     * @param chunksCount
     */
    private void initializeChunks(Vector3Int chunksCount) {
        chunks = new ChunkControl[chunksCount.getX()][chunksCount.getY()][chunksCount.getZ()];
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                for (int z = 0; z < chunks[0][0].length; z++) {
                    ChunkControl chunk = new ChunkControl(this, x, y, z);
                    chunks[x][y][z] = chunk;
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void addChunk(int x, int y, int z) {
    }

    /**
     *
     * @param spatial
     */
    @Override
    public void setSpatial(Spatial spatial) {
        Spatial oldSpatial = this.spatial;
        super.setSpatial(spatial);
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                for (int z = 0; z < chunks[0][0].length; z++) {
                    if (spatial == null) {
                        oldSpatial.removeControl(chunks[x][y][z]);
                    } else {
                        spatial.addControl(chunks[x][y][z]);
                    }
                }
            }
        }
    }

    /**
     *
     * @param lastTimePerFrame
     */
    @Override
    protected void controlUpdate(float lastTimePerFrame) {
        updateSpatial();
    }

    /**
     *
     * @param renderManager
     * @param viewPort
     */
    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {
    }

    /**
     *
     * @param spatial
     * @return
     */
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public BlockType getBlock(int x, int y, int z) {
        return getBlock(new Vector3Int(x, y, z));
    }

    /**
     *
     * @param location
     * @return
     */
    public BlockType getBlock(Vector3Int location) {
        LocalBlockState localBlockState = getLocalBlockState(location);
        if (localBlockState != null) {
            return localBlockState.getBlock();
        }
        return null;
    }

    /**
     *
     * @param location
     * @param size
     * @param blockClass
     */
    public void setBlockArea(Vector3Int location, Vector3Int size, Class<? extends Block> blockClass) {
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    tmpLocation.set(location.getX() + x, location.getY() + y, location.getZ() + z);
                    setBlock(tmpLocation, blockClass);
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param blockClass
     */
    public void setBlock(int x, int y, int z, Class<? extends Block> blockClass) {
        setBlock(new Vector3Int(x, y, z), blockClass);
    }

    /**
     *
     * @param location
     * @param blockClass
     */
    public void setBlock(Vector3Int location, Class<? extends Block> blockClass) {
        LocalBlockState localBlockState = getLocalBlockState(location);
        if (localBlockState != null) {
            localBlockState.setBlock(blockClass);
        }
    }

    /**
     *
     * @param location
     * @param size
     */
    public void removeBlockArea(Vector3Int location, Vector3Int size) {
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    tmpLocation.set(location.getX() + x, location.getY() + y, location.getZ() + z);
                    removeBlock(tmpLocation);
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void removeBlock(int x, int y, int z) {
        removeBlock(new Vector3Int(x, y, z));
    }

    /**
     *
     * @param location
     */
    public void removeBlock(Vector3Int location) {
        LocalBlockState localBlockState = getLocalBlockState(location);
        if (localBlockState != null) {
            localBlockState.removeBlock();
        }
    }

    /**
     *
     * @param blockLocation
     * @return
     */
    private LocalBlockState getLocalBlockState(Vector3Int blockLocation) {
        if (blockLocation.hasNegativeCoordinate()) {
            return null;
        }
        ChunkControl chunk = getChunk(blockLocation);
        if (chunk != null) {
            Vector3Int localBlockLocation = getLocalBlockLocation(blockLocation, chunk);
            return new LocalBlockState(chunk, localBlockLocation);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ChunkControl[][][] getChunks() {
        return chunks;
    }

    /**
     *
     * @param chunkX
     * @param chunkY
     * @param chunkZ
     * @return
     */
    public ChunkControl getChunk(int chunkX, int chunkY, int chunkZ) {
        ChunkControl chunkControl = chunks[chunkX][chunkY][chunkY];
        if (chunkControl != null) {
            return chunkControl;
        }
        return null;
    }

    /**
     *
     * @param blockLocation
     * @return
     */
    public ChunkControl getChunk(Vector3Int blockLocation) {
        if (blockLocation.hasNegativeCoordinate()) {
            return null;
        }
        Vector3Int chunkLocation = getChunkLocation(blockLocation);
        if (isValidChunkLocation(chunkLocation)) {
            return chunks[chunkLocation.getX()][chunkLocation.getY()][chunkLocation.getZ()];
        }
        return null;
    }

    /**
     *
     * @param location
     * @return
     */
    public boolean isValidChunkLocation(Vector3Int location) {
        return Util.isValidIndex(chunks, location);
    }

    public boolean isChunkGenerated(Vector3Int location) {
        if (Util.isValidIndex(chunks, location)) {
            return chunks[location.getX()][location.getY()][location.getZ()].isGenerated();
        }
        return false;
    }

    /**
     *
     * @param blockLocation
     * @return
     */
    private Vector3Int getChunkLocation(Vector3Int blockLocation) {
        Vector3Int chunkLocation = new Vector3Int();
        int chunkX = (blockLocation.getX() / settings.getChunkSizeX());
        int chunkY = (blockLocation.getY() / settings.getChunkSizeY());
        int chunkZ = (blockLocation.getZ() / settings.getChunkSizeZ());
        chunkLocation.set(chunkX, chunkY, chunkZ);
        return chunkLocation;
    }

    /**
     *
     * @param blockLocation
     * @param chunk
     * @return
     */
    public static Vector3Int getLocalBlockLocation(Vector3Int blockLocation, ChunkControl chunk) {
        Vector3Int localLocation = new Vector3Int();
        int localX = (blockLocation.getX() - chunk.getBlockLocation().getX());
        int localY = (blockLocation.getY() - chunk.getBlockLocation().getY());
        int localZ = (blockLocation.getZ() - chunk.getBlockLocation().getZ());
        localLocation.set(localX, localY, localZ);
        return localLocation;
    }

    /**
     *
     * @return
     */
    public boolean updateSpatial() {
        boolean wasUpdatedNeeded = false;
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                for (int z = 0; z < chunks[0][0].length; z++) {
                    ChunkControl chunk = chunks[x][y][z];
                    if (chunk.updateSpatial()) {
                        wasUpdatedNeeded = true;
                        for (int i = 0; i < chunkListeners.size(); i++) {
                            ChunkListener blockTerrainListener = chunkListeners.get(i);
                            blockTerrainListener.onSpatialUpdated(chunk);
                        }
                    }
                }
            }
        }
        return wasUpdatedNeeded;
    }

    /**
     *
     */
    public void updateBlockMaterial() {
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                for (int z = 0; z < chunks[0][0].length; z++) {
                    chunks[x][y][z].updateBlockMaterial();
                }
            }
        }
    }

    /**
     *
     * @param blockChunkListener
     */
    public void addChunkListener(ChunkListener blockChunkListener) {
        chunkListeners.add(blockChunkListener);
    }

    /**
     *
     * @param blockChunkListener
     */
    public void removeChunkListener(ChunkListener blockChunkListener) {
        chunkListeners.remove(blockChunkListener);
    }

    /**
     *
     * @return
     */
    public CubesSettings getSettings() {
        return settings;
    }

    /**
     *
     * @param location
     * @param heightmapPath
     * @param maximumHeight
     * @param blockClass
     */
    public void setBlocksFromHeightmap(Vector3Int location, String heightmapPath, int maximumHeight, float scale, Class<? extends Block> blockClass) {
        try {
            Texture heightmapTexture = settings.getAssetManager().loadTexture(heightmapPath);
            ImageBasedHeightMap heightmap = new ImageBasedHeightMap(heightmapTexture.getImage(), scale);
            heightmap.load();
            heightmap.setHeightScale(maximumHeight / 255f);
            setBlocksFromHeightmap(location, getHeightmapBlockData(heightmap.getScaledHeightMap(), heightmap.getSize()), blockClass);
        } catch (Exception ex) {
            System.out.println("Error while loading heightmap '" + heightmapPath + "'.");
        }
    }

    /**
     *
     * @param heightmapData
     * @param length
     * @return
     */
    private static int[][] getHeightmapBlockData(float[] heightmapData, int length) {
        int[][] data = new int[heightmapData.length / length][length];
        int x = 0;
        int z = 0;
        for (int i = 0; i < heightmapData.length; i++) {
            data[x][z] = (int) Math.round(heightmapData[i]);
            x++;
            if ((x != 0) && ((x % length) == 0)) {
                x = 0;
                z++;
            }
        }
        return data;
    }

    /**
     *
     * @param location
     * @param heightmap
     * @param blockClass
     */
    public void setBlocksFromHeightmap(Vector3Int location, int[][] heightmap, Class<? extends Block> blockClass) {
        Vector3Int tmpLocation = new Vector3Int();
        Vector3Int tmpSize = new Vector3Int();
        for (int x = 0; x < heightmap.length; x++) {
            for (int z = 0; z < heightmap[0].length; z++) {
                tmpLocation.set(location.getX() + x, location.getY(), location.getZ() + z);
                tmpSize.set(1, heightmap[x][z], 1);
                setBlockArea(tmpLocation, tmpSize, blockClass);
            }
        }
    }

    /**
     *
     * @param location
     * @param size
     * @param roughness
     * @param blockClass
     */
    public void setBlocksFromNoise(Vector3Int location, Vector3Int size, float roughness, Class<? extends Block> blockClass) {
        Noise noise = new Noise(null, roughness, size.getX(), size.getZ());
        float gridMinimum = noise.getMinimum();
        float gridLargestDifference = (noise.getMaximum() - gridMinimum);
        float[][] grid = noise.getGrid();
        for (int x = 0; x < grid.length; x++) {
            float[] row = grid[x];
            for (int z = 0; z < row.length; z++) {
                /*---Calculation of block height has been summarized to minimize the java heap---
                 float gridGroundHeight = (row[z] - gridMinimum);
                 float blockHeightInPercents = ((gridGroundHeight * 100) / gridLargestDifference);
                 int blockHeight = ((int) ((blockHeightInPercents / 100) * size.getY())) + 1;
                 ---*/
                int blockHeight = (((int) (((((row[z] - gridMinimum) * 100) / gridLargestDifference) / 100) * size.getY())) + 1);
                Vector3Int tmpLocation = new Vector3Int();
                for (int y = 0; y < blockHeight; y++) {
                    tmpLocation.set(location.getX() + x, location.getY() + y, location.getZ() + z);
                    setBlock(tmpLocation, blockClass);
                }
            }
        }
    }

    public void setBlocksFromSimplexNoise(Vector3Int loc, float roughness) {
    }

    public void setBlocksFromLibNoise(Vector3Int loc) {
        try {
            RidgedMulti mountainTerrain = new RidgedMulti();

            Billow baseFlatTerrain = new Billow();
            baseFlatTerrain.setFrequency(2.0);

            ScaleBias flatTerrain = new ScaleBias(baseFlatTerrain);
            flatTerrain.setScale(0.125);
            flatTerrain.setBias(-0.75);

            Perlin terrainType = new Perlin();
            terrainType.setOctaveCount(3);
            terrainType.setFrequency(0.5);
            terrainType.setPersistence(0.25);

            Select terrainSelector = new Select(flatTerrain, mountainTerrain, terrainType);
            terrainSelector.setBounds(0.0, 1000.0);
            terrainSelector.setEdgeFalloff(0.125);

            ScaleBias terrainScaler = new ScaleBias(terrainSelector);
            terrainScaler.setScale(80.0);
            terrainScaler.setBias(80.0);

            Turbulence finalTerrain = new Turbulence(terrainScaler);
            finalTerrain.setFrequency(4.0);
            finalTerrain.setPower(0.125);

            NoiseMap heightMap = new NoiseMap(160, 160);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(finalTerrain);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(160, 160);
            heightMapBuilder.setBounds(6.0, 10.0, 1.0, 5.0);
            heightMapBuilder.build();
            int grass = 60;
            int sand = 32;
            int rock = 29;
            for (int x = 0; x < 160; x++) {
                for (int z = 0; z < 160; z++) {
                    int blockHeight = (int) Math.round(heightMap.getValue(x, z));
                    if (blockHeight > 60) {
                        blockHeight = 60;
                    }
                    Vector3Int tmpLocation = new Vector3Int();
                    for (int y = 0; y < blockHeight; y++) {
                        tmpLocation.set(loc.getX() + x, loc.getY() + y, loc.getZ() + z);
                        if (y <= rock) {
                            setBlock(tmpLocation, Block_Stone.class);
                        } else if (y > rock && y <= sand) {
                            setBlock(tmpLocation, Block_Sand.class);
                        } else if (y > sand && y <= grass) {
                            setBlock(tmpLocation, Block_Grass.class);
                        } else {
                            setBlock(tmpLocation, Block_Water.class);
                        }
                    }
                }
            }
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
        }
    }

    /**
     *
     * @param location
     * @param size
     * @param blockClass
     */
    public void setBlocksForMaximumFaces(Vector3Int location, Vector3Int size, Class<? extends Block> blockClass) {
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    if (((x ^ y ^ z) & 1) == 1) {
                        tmpLocation.set(location.getX() + x, location.getY() + y, location.getZ() + z);
                        setBlock(tmpLocation, blockClass);
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public TerrainControl clone() {
        TerrainControl blockTerrain = new TerrainControl(settings, new Vector3Int());
        blockTerrain.setBlocksFromTerrain(this);
        return blockTerrain;
    }

    /**
     *
     * @param blockTerrain
     */
    public void setBlocksFromTerrain(TerrainControl blockTerrain) {
        CubesSerializer.readFromBytes(this, CubesSerializer.writeToBytes(blockTerrain));
    }

    /**
     *
     * @param outputStream
     */
    @Override
    public void write(BitOutputStream outputStream) {
        outputStream.writeInteger(chunks.length);
        outputStream.writeInteger(chunks[0].length);
        outputStream.writeInteger(chunks[0][0].length);
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                for (int z = 0; z < chunks[0][0].length; z++) {
                    chunks[x][y][z].write(outputStream);
                }
            }
        }
    }

    /**
     *
     * @param inputStream
     * @throws IOException
     */
    @Override
    public void read(BitInputStream inputStream) throws IOException {
        int chunksCountX = inputStream.readInteger();
        int chunksCountY = inputStream.readInteger();
        int chunksCountZ = inputStream.readInteger();
        initializeChunks(new Vector3Int(chunksCountX, chunksCountY, chunksCountZ));
        for (int x = 0; x < chunksCountX; x++) {
            for (int y = 0; y < chunksCountY; y++) {
                for (int z = 0; z < chunksCountZ; z++) {
                    chunks[x][y][z].read(inputStream);
                }
            }
        }
    }
}
