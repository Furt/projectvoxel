/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.util;

/**
 *
 * @author Terry
 */
public class Vector3Int {

    private int x;
    private int y;
    private int z;

    public Vector3Int() {
    }

    public Vector3Int(int x, int y, int z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3Int(float x, float y, float z) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
        this.z = (int) Math.round(z);
    }

    public Vector3Int(double x, double y, double z) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
        this.z = (int) Math.round(z);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public Vector3Int setX(int x) {
        this.x = x;
        return this;
    }

    public Vector3Int setY(int y) {
        this.y = y;
        return this;
    }

    public Vector3Int setZ(int z) {
        this.z = z;
        return this;
    }

    public Vector3Int set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    @Override
    public Vector3Int clone() {
        return new Vector3Int(this.x, this.y, this.z);
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
