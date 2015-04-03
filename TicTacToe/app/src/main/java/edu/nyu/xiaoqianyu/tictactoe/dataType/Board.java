package edu.nyu.xiaoqianyu.tictactoe.dataType;

/**
 * Created by abc on 4/3/15.
 */
public class Board {
    private Cell[][] cells;
    public Board() {
        cells = new Cell[3][3];
        for(int i = 0; i <= 2; i++) {
            for(int j = 0; j <= 2; j++) {
                cells[i][j] = new Cell(Seed.EMPTY);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}
