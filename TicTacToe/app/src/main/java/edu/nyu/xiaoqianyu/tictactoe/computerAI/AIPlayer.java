package edu.nyu.xiaoqianyu.tictactoe.computerAI;

import edu.nyu.xiaoqianyu.tictactoe.dataType.Board;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Cell;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;

/**
 * Created by abc on 4/3/15.
 */
public abstract class AIPlayer {
    protected int ROWS = 3;  // number of rows
    protected int COLS = 3;  // number of columns

    protected Cell[][] cells; // the board's ROWS-by-COLS array of Cells
    protected Seed mySeed;    // computer's seed
    protected Seed oppSeed;   // opponent's seed

    /** Constructor with reference to game board */
    public AIPlayer(Board board) {
        cells = board.getCells();
    }

    /** Set/change the seed used by computer and opponent */
    public void setSeed(Seed seed) {
        this.mySeed = seed;
        oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    /** Abstract method to get next move. Return int[2] of {row, col} */
    public abstract int[] move();  // to be implemented by subclasses
}
