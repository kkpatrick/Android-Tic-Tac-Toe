package edu.nyu.xiaoqianyu.tictactoe.events;

import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;

/**
 * Created by abc on 4/3/15.
 */
public class CellTouchEvent {
    private int cellTouchedRow;
    private int cellTouchedCol;
    private Seed cellSeed;

    public CellTouchEvent(int row, int col, Seed cellSeed) {
        this.cellTouchedRow = row;
        this.cellTouchedCol = col;
        this.cellSeed = cellSeed;
    }

    public void setCellTouchedRow(int cellTouchedRow) {
        this.cellTouchedRow = cellTouchedRow;
    }

    public void setCellTouchedCol(int cellTouchedCol) {
        this.cellTouchedCol = cellTouchedCol;
    }

    public void setCellSeed(Seed cellSeed) {
        this.cellSeed = cellSeed;
    }

    public int getCellTouchedRow() {
        return cellTouchedRow;
    }

    public int getCellTouchedCol() {
        return cellTouchedCol;
    }

    public Seed getCellSeed() {
        return cellSeed;
    }
}

