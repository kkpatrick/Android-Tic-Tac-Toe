package edu.nyu.xiaoqianyu.tictactoe.model;

import de.greenrobot.event.EventBus;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;
import edu.nyu.xiaoqianyu.tictactoe.events.CellTouchEvent;

/**
 * Created by abc on 4/3/15.
 */
public class GameModel {

    public GameModel() {
    }

    public void cellOnTouch(int row, int col){
        EventBus.getDefault().post(new CellTouchEvent(row, col, Seed.CROSS));
    }
}
