package me.furt.projectv.core.render;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;
import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.block.BlockSkin;
import me.furt.projectv.core.block.TextureLocation;
import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.core.world.Region;
import me.furt.projectv.core.world.World;
import me.furt.projectv.core.world.WorldSettings;
import me.furt.projectv.util.Vector3i;

/**
 * ProjectV
 *
 * @author Furt
 */
public class MeshOptimizer {

    private int[] indices;
    private Vector3f[] vertices;
    private Vector2f[] textureCoordinates;
    private float[] normals;
    private World world;

    public MeshOptimizer(World world) {
        this.world = world;
    }

    public Mesh generateOptimizedMesh(Chunk blockChunk, MeshMerger meshMerger) {
        loadMeshData(blockChunk, meshMerger);
        return generateMesh();
    }

    private void loadMeshData(Chunk chunk, MeshMerger meshMerger) {
        WorldSettings worldSettings = chunk.getRegion().getWorld().getSettings();
        ArrayList<Vector3f> verticeList = new ArrayList<Vector3f>();
        ArrayList<Vector2f> textureCoordinateList = new ArrayList<Vector2f>();
        ArrayList<Integer> indicesList = new ArrayList<Integer>();
        ArrayList<Float> normalsList = new ArrayList<Float>();
        Region region = chunk.getRegion();
        Vector3i tmpLocation = new Vector3i();
        float blockSize = worldSettings.getBlockSize();
        for (int x = 0; x < worldSettings.getChunkSizeX(); x++) {
            for (int y = 0; y < worldSettings.getChunkSizeY(); y++) {
                for (int z = 0; z < worldSettings.getChunkSizeZ(); z++) {
                    tmpLocation.set(x, y, z);
                    Block block = chunk.getBlock(tmpLocation);
                    if (block != null) {
                        BlockSkin blockSkin = block.getTexture();
                        Vector3f blockLocation = new Vector3f(x, y, z);

                        Vector3f faceLoc_Bottom_TopLeft = blockLocation.add(new Vector3f(0, 0, 0)).mult(blockSize);
                        Vector3f faceLoc_Bottom_TopRight = blockLocation.add(new Vector3f(1, 0, 0)).mult(blockSize);
                        Vector3f faceLoc_Bottom_BottomLeft = blockLocation.add(new Vector3f(0, 0, 1)).mult(blockSize);
                        Vector3f faceLoc_Bottom_BottomRight = blockLocation.add(new Vector3f(1, 0, 1)).mult(blockSize);
                        Vector3f faceLoc_Top_TopLeft = blockLocation.add(new Vector3f(0, 1, 0)).mult(blockSize);
                        Vector3f faceLoc_Top_TopRight = blockLocation.add(new Vector3f(1, 1, 0)).mult(blockSize);
                        Vector3f faceLoc_Top_BottomLeft = blockLocation.add(new Vector3f(0, 1, 1)).mult(blockSize);
                        Vector3f faceLoc_Top_BottomRight = blockLocation.add(new Vector3f(1, 1, 1)).mult(blockSize);

                        if (meshMerger.shouldFaceBeAdded(chunk, tmpLocation, Block.Face.Top)) {
                            addVerticeIndexes(verticeList, indicesList);
                            verticeList.add(faceLoc_Top_BottomLeft);
                            verticeList.add(faceLoc_Top_BottomRight);
                            verticeList.add(faceLoc_Top_TopLeft);
                            verticeList.add(faceLoc_Top_TopRight);
                            addBlockTextureCoordinates(textureCoordinateList, blockSkin.getTextureLocation(chunk, tmpLocation, Block.Face.Top));
                            addSquareNormals(normalsList, new float[]{0, 1, 0});
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
            vertices[i] = verticeList.get(i);
        }
        textureCoordinates = new Vector2f[textureCoordinateList.size()];
        for (int i = 0; i < textureCoordinateList.size(); i++) {
            textureCoordinates[i] = textureCoordinateList.get(i);
        }
        indices = new int[indicesList.size()];
        for (int i = 0; i < indicesList.size(); i++) {
            indices[i] = indicesList.get(i);
        }
        vertices = verticeList.toArray(new Vector3f[0]);
        textureCoordinates = textureCoordinateList.toArray(new Vector2f[0]);
        normals = new float[normalsList.size()];
        for (int i = 0; i < normalsList.size(); i++) {
            normals[i] = normalsList.get(i);
        }
    }

    private void addVerticeIndexes(ArrayList<Vector3f> verticeList, ArrayList<Integer> indexesList) {
        int verticesCount = verticeList.size();
        indexesList.add(verticesCount + 2);
        indexesList.add(verticesCount + 0);
        indexesList.add(verticesCount + 1);
        indexesList.add(verticesCount + 1);
        indexesList.add(verticesCount + 3);
        indexesList.add(verticesCount + 2);
    }

    private void addBlockTextureCoordinates(ArrayList<Vector2f> textureCoordinatesList, TextureLocation textureLocation) {
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 0, 0));
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 1, 0));
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 0, 1));
        textureCoordinatesList.add(getTextureCoordinates(textureLocation, 1, 1));
    }

    private Vector2f getTextureCoordinates(TextureLocation textureLocation, int xUnitsToAdd, int yUnitsToAdd) {
        float textureCount = world.getSettings().getRowCount();
        float textureUnit = 1f / textureCount;
        float x = (((textureLocation.getColumn() + xUnitsToAdd) * textureUnit));
        float y = ((((-1 * textureLocation.getRow()) + (yUnitsToAdd - 1)) * textureUnit) + 1);
        return new Vector2f(x, y);
    }

    private void addSquareNormals(ArrayList<Float> normalsList, float[] normal) {

        for (int i = 0; i < 4; i++) {
            normalsList.add(normal[0]);
            normalsList.add(normal[1]);
            normalsList.add(normal[2]);
        }
    }

    private Mesh generateMesh() {
        Mesh mesh = new Mesh();
        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(textureCoordinates));
        mesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indices));
        mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
        mesh.updateBound();
        return mesh;
    }
}
