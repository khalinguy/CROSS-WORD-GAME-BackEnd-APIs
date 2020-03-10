package com.source.core;

public class WordPosition {
    private int row;
    private int column;
    private int redirect;

    public WordPosition() {
    }

    public WordPosition(int row, int column, int redirect) {
        this.row = row;
        this.column = column;
        this.redirect = redirect;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRedirect() {
        return redirect;
    }

    public void setRedirect(int redirect) {
        this.redirect = redirect;
    }

    @Override
    public String toString() {
        return "{" + "row=" + row + ", column=" + column + ", redirect=" + redirect + '}';
    }
}
