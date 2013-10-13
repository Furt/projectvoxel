package com.cubes;

public class TextureLocation {

    public TextureLocation(int column, int row) {
        this.column = column;
        this.row = row;
    }
    private int column;
    private int row;

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
