package edu.nyu.xiaoqianyu.tictactoe.dataType;

/**
 * Created by abc on 4/3/15.
 */
public class Cell {
    private Seed content;

    public Cell(Seed content){ this.content = content;}

    public Seed getContent() {
        return content;
    }

    public void setContent(Seed content) {
        this.content = content;
    }
}
