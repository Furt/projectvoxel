package me.furt.projectv.player;

import com.jme3.math.Vector3f;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Player {

    private final int id;
    private final String account;
    private String name;
    public Vector3f location;
    public Vector3f direction;

    public Player(int id, String account) {
        this.id = id;
        this.account = account;
        this.name = account;
    }

    public Player(int id, String account, String name, Vector3f location, Vector3f direction) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.location = location;
        this.direction = direction;
    }

    public int getId() {
        return this.id;
    }

    public String getAccount() {
        return this.account;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector3f getLocation() {
        return this.location;
    }

    public void setLocation(Vector3f loc) {
        this.location = loc;
    }

    public Vector3f getDirection() {
        return this.direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
