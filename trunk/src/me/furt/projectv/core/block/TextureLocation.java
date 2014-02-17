package me.furt.projectv.core.block;

public class TextureLocation {

    /**
     *
     * @param column
     * @param row
     */
    public TextureLocation(int column, int row) {
        this.column = column;
        this.row = row;
    }
    private int column;
    private int row;

    /**
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }
}
