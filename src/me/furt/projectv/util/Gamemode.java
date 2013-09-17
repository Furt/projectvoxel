/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.util;

/**
 *
 * @author Terry
 */
public enum Gamemode {

    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    BUILD(1, "build"),
    MINI(2, "mini");
    int id;
    String name;

    private Gamemode(int i, String s) {
        this.id = i;
        this.name = s;
    }

    /**
     * Returns the ID of this gamemode
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the name of this gamemode
     */
    public String getName() {
        return this.name;
    }
}
