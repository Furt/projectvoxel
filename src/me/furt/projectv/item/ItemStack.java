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
public class ItemStack {
    
    public int id;
    public int durability;
    public int quanity;
    
    private ItemStack() {}
    
    public ItemStack(Block block)
    {
        this(block, 1);
    }
    
    public ItemStack(Block block, int quanity)
    {
        this(block.id, quanity, 0);
    }
    
    public ItemStack(int id, int quanity, int durability)
    {
        this.id = id;
        this.quanity = quanity;
        this.durability = durability;

        if (this.durability < 0)
        {
            this.durability = 0;
        }
    }
    
    public ItemStack(Item item)
    {
        this(item.id, 1, 0);
    }

    public ItemStack(Item item, int quanity)
    {
        this(item.id, quanity, 0);
    }

    public ItemStack(Item item, int quanity, int durability)
    {
        this(item.id, quanity, durability);
    }
}
