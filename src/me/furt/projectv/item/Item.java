/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.item;

import me.furt.projectv.block.Block;

/**
 *
 * @author Terry
 */
public class Item {

    public static Item[] itemsList = new Item[128];
    /**
     * The ID of this item.
     */
    public final int id;
    /**
     * Maximum size of the stack.
     */
    protected int maxStack = 99;
    /**
     * Maximum damage an item can handle.
     */
    private int maxDamage;

    public Item(int i) {
        this.id = i;
        this.itemsList[i] = this;
    }

    public Item setMaxStackSize(int par1) {
        this.maxStack = par1;
        return this;
    }

    public int getItemID() {
        return this.id;
    }

    public boolean canHarvestBlock(Block block) {
        return false;
    }
}
