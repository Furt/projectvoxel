package me.furt.projectv.core.block;

/**
 * ProjectV
 *
 * @author Furt
 */
public class TextureLocation {

    private int column;
    private int row;

    public TextureLocation(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
