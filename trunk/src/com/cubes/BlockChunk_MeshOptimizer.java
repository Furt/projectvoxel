package com.cubes;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;

public class BlockChunk_MeshOptimizer {

    private static Vector3f[] vertices;
    private static Vector2f[] textureCoordinates;
    private static int[] indices;

    public static Mesh generateOptimizedMesh(BlockChunkControl blockChunk, BlockChunk_MeshMerger meshMerger) {
        loadMeshData(blockChunk, meshMerger);
        return generateMesh();
    }

    private static void loadMeshData(BlockChunkControl chunk, BlockChunk_MeshMerger meshMerger) {
        ArrayList verticeList = new ArrayList();
        ArrayList textureCoordinateList = new ArrayList();
        ArrayList indicesList = new ArrayList();
        BlockTerrainControl blockTerrain = chunk.getTerrain();
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < blockTerrain.getSettings().getChunkSizeX(); x++) {
            for (int y = 0; y < blockTerrain.getSettings().getChunkSizeY(); y++) {
                for (int z = 0; z < blockTerrain.getSettings().getChunkSizeZ(); z++) {
                    tmpLocation.set(x, y, z);
                    BlockType block = chunk.getBlock(tmpLocation);
                    if (block != null) {
                        BlockSkin blockSkin = block.getSkin();
                        Vector3f blockLocation = new Vector3f(x, y, z);

                        Vector3f faceLoc_Bottom_TopLeft = blockLocation.add(new Vector3f(0.0F, 0.0F, 0.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Bottom_TopRight = blockLocation.add(new Vector3f(1.0F, 0.0F, 0.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Bottom_BottomLeft = blockLocation.add(new Vector3f(0.0F, 0.0F, 1.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Bottom_BottomRight = blockLocation.add(new Vector3f(1.0F, 0.0F, 1.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Top_TopLeft = blockLocation.add(new Vector3f(0.0F, 1.0F, 0.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Top_TopRight = blockLocation.add(new Vector3f(1.0F, 1.0F, 0.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Top_BottomLeft = blockLocation.add(new Vector3f(0.0F, 1.0F, 1.0F)).mult(blockTerrain.getSettings().getBlockSize());
                        Vector3f faceLoc_Top_BottomRight = blockLocation.add(new Vector3f(1.0F, 1.0F, 1.0F)).mult(blockTerrain.getSettings().getBlockSize());

                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Top)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Top_BottomLeft);
                            verticeList.add(faceLoc_Top_BottomRight);
                            verticeList.add(faceLoc_Top_TopLeft);
                            verticeList.add(faceLoc_Top_TopRight);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Top));
                        }
                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Bottom)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Bottom_BottomRight);
                            verticeList.add(faceLoc_Bottom_BottomLeft);
                            verticeList.add(faceLoc_Bottom_TopRight);
                            verticeList.add(faceLoc_Bottom_TopLeft);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Bottom));
                        }
                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Left)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Bottom_TopLeft);
                            verticeList.add(faceLoc_Bottom_BottomLeft);
                            verticeList.add(faceLoc_Top_TopLeft);
                            verticeList.add(faceLoc_Top_BottomLeft);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Left));
                        }
                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Right)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Bottom_BottomRight);
                            verticeList.add(faceLoc_Bottom_TopRight);
                            verticeList.add(faceLoc_Top_BottomRight);
                            verticeList.add(faceLoc_Top_TopRight);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Right));
                        }
                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Front)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Bottom_BottomLeft);
                            verticeList.add(faceLoc_Bottom_BottomRight);
                            verticeList.add(faceLoc_Top_BottomLeft);
                            verticeList.add(faceLoc_Top_BottomRight);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Front));
                        }
                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Back)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Bottom_TopRight);
                            verticeList.add(faceLoc_Bottom_TopLeft);
                            verticeList.add(faceLoc_Top_TopRight);
                            verticeList.add(faceLoc_Top_TopLeft);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Back));
                        }
                    }
                }
            }
        }

        vertices = new Vector3f[verticeList.size()];
        for (int i = 0; i < verticeList.size(); i++) {
            vertices[i] = ((Vector3f) verticeList.get(i));
        }
        textureCoordinates = new Vector2f[textureCoordinateList.size()];
        for (int i = 0; i < textureCoordinateList.size(); i++) {
            textureCoordinates[i] = ((Vector2f) textureCoordinateList.get(i));
        }
        indices = new int[indicesList.size()];
        for (int i = 0; i < indicesList.size(); i++) {
            indices[i] = ((Integer) indicesList.get(i)).intValue();
        }
    }

    private static void addBlockTextureCoordinates(ArrayList<Vector2f> textureCoordinatesList, BlockSkin_TextureLocation textureLocation) {
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 0, 0));
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 1, 0));
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 0, 1));
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 1, 1));
    }

    private static Vector2f getTextureCoordinates(BlockSkin_TextureLocation textureLocation, int xUnitsToAdd, int yUnitsToAdd) {
        float textureCount = 16.0F;
        float textureUnit = 1.0F / textureCount;
        float x = (textureLocation.getColumn() + xUnitsToAdd) * textureUnit;
        float y = (-1 * textureLocation.getRow() + (yUnitsToAdd - 1)) * textureUnit + 1.0F;
        return new Vector2f(x, y);
    }

    private static void addVerticeIndexes(ArrayList<Vector3f> verticeList, ArrayList<Integer> indexesList) {
        int verticesCount = verticeList.size();
        indexesList.add(Integer.valueOf(verticesCount + 2));
        indexesList.add(Integer.valueOf(verticesCount + 0));
        indexesList.add(Integer.valueOf(verticesCount + 1));
        indexesList.add(Integer.valueOf(verticesCount + 1));
        indexesList.add(Integer.valueOf(verticesCount + 3));
        indexesList.add(Integer.valueOf(verticesCount + 2));
    }

    private static Mesh generateMesh() {
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(textureCoordinates));
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indices));
        mesh.updateBound();
        return mesh;
    }
}