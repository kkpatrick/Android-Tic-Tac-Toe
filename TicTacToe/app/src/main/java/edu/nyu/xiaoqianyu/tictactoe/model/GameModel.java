package edu.nyu.xiaoqianyu.tictactoe.model;

import de.greenrobot.event.EventBus;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Board;
import edu.nyu.xiaoqianyu.tictactoe.dataType.PlayerRole;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;
import edu.nyu.xiaoqianyu.tictactoe.dataType.VsMode;
import edu.nyu.xiaoqianyu.tictactoe.events.CellTouchEvent;

/**
 * Created by abc on 4/3/15.
 */
public class GameModel {

    private Board board;
    private VsMode VsMode;
    private PlayerRole currentPlayer; //player1 uses circle, player2 and computer use cross

    public GameModel(VsMode mode) {
        board = new Board();
        VsMode = mode;
        currentPlayer = PlayerRole.PLAYER1;
    }

    public void cellOnTouch(int row, int col){
        //this cell was touched before, no response;
        if(board.getCells()[row][col].getContent() != Seed.EMPTY) return;

        if(PlayerRole.PLAYER1 == currentPlayer) { // player1, circle
            board.getCells()[row][col].setContent(Seed.NOUGHT);
            EventBus.getDefault().post(new CellTouchEvent(row, col, Seed.NOUGHT));
        }
        else { // player2 or computer, cross
            board.getCells()[row][col].setContent(Seed.CROSS);
            EventBus.getDefault().post(new CellTouchEvent(row, col, Seed.CROSS));
        }

        //after every, we must change player
        changePlayer();
    }

    private void changePlayer() {
        if(VsMode == VsMode.PLAYER_VS_COM) {
            if(currentPlayer == PlayerRole.COMPUTER){
                currentPlayer = PlayerRole.PLAYER1;
            }
            else {
                currentPlayer = PlayerRole.COMPUTER;
            }
        }
        else {
            if(currentPlayer == PlayerRole.PLAYER1){
                currentPlayer = PlayerRole.PLAYER2;
            }
            else {
                currentPlayer = PlayerRole.PLAYER1;
            }
        }
    }
}
