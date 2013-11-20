package com.cubes;

public class Vector3Int {

    private int x;
    private int y;
    private int z;

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Vector3Int(int x, int y, int z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     */
    public Vector3Int() {
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     * @return
     */
    public Vector3Int setX(int x) {
        this.x = x;
        return this;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     * @return
     */
    public Vector3Int setY(int y) {
        this.y = y;
        return this;
    }

    /**
     *
     * @return
     */
    public int getZ() {
        return z;
    }

    /**
     *
     * @param z
     * @return
     */
    public Vector3Int setZ(int z) {
        this.z = z;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean hasNegativeCoordinate() {
        return ((x < 0) || (y < 0) || (z < 0));
    }

    /**
     *
     * @param vector3Int
     * @return
     */
    public Vector3Int set(Vector3Int vector3Int) {
        return set(vector3Int.getX(), vector3Int.getY(), vector3Int.getZ());
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     *
     * @param vector3Int
     * @return
     */
    public Vector3Int add(Vector3Int vector3Int) {
        return add(vector3Int.getX(), vector3Int.getY(), vector3Int.getZ());
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int add(int x, int y, int z) {
        return new Vector3Int(this.x + x, this.y + y, this.z + z);
    }

    /**
     *
     * @param vector3Int
     * @return
     */
    public Vector3Int addLocal(Vector3Int vector3Int) {
        return addLocal(vector3Int.getX(), vector3Int.getY(), vector3Int.getZ());
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int addLocal(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     *
     * @param vector3Int
     * @return
     */
    public Vector3Int subtract(Vector3Int vector3Int) {
        return subtract(vector3Int.getX(), vector3Int.getY(), vector3Int.getZ());
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int subtract(int x, int y, int z) {
        return new Vector3Int(this.x - x, this.y - y, this.z - z);
    }

    /**
     *
     * @param vector3Int
     * @return
     */
    public Vector3Int subtractLocal(Vector3Int vector3Int) {
        return subtractLocal(vector3Int.getX(), vector3Int.getY(), vector3Int.getZ());
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int subtractLocal(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     *
     * @return
     */
    public Vector3Int negate() {
        return mult(-1);
    }

    /**
     *
     * @param factor
     * @return
     */
    public Vector3Int mult(int factor) {
        return mult(factor, factor, factor);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int mult(int x, int y, int z) {
        return new Vector3Int(this.x * x, this.y * y, this.z * z);
    }

    /**
     *
     * @param factor
     * @return
     */
    public Vector3Int div(int factor) {
        return div(factor, factor, factor);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int div(int x, int y, int z) {
        return new Vector3Int(this.x / x, this.y / y, this.z / z);
    }

    /**
     *
     * @return
     */
    public Vector3Int negateLocal() {
        return multLocal(-1);
    }

    /**
     *
     * @param factor
     * @return
     */
    public Vector3Int multLocal(int factor) {
        return multLocal(factor, factor, factor);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3Int multLocal(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector3Int clone() {
        return new Vector3Int(x, y, z);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Vector3Int) {
            Vector3Int vector3Int = (Vector3Int) object;
            return ((x == vector3Int.getX()) && (y == vector3Int.getY()) && (z == vector3Int.getZ()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        hash = 79 * hash + this.z;
        return hash;
    }

    @Override
    public String toString() {
        return "[Vector3Int x=" + x + " y=" + y + " z=" + z + "]";
    }
}
