package edu.nyu.xiaoqianyu.tictactoe.model;

import android.os.Handler;

import de.greenrobot.event.EventBus;
import edu.nyu.xiaoqianyu.tictactoe.computerAI.AIPlayer;
import edu.nyu.xiaoqianyu.tictactoe.computerAI.AIPlayerMinimax;
import edu.nyu.xiaoqianyu.tictactoe.computerAI.AIPlayerTableLookup;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Board;
import edu.nyu.xiaoqianyu.tictactoe.dataType.GameLevel;
import edu.nyu.xiaoqianyu.tictactoe.dataType.PlayerRole;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;
import edu.nyu.xiaoqianyu.tictactoe.dataType.VsMode;
import edu.nyu.xiaoqianyu.tictactoe.events.CellTouchEvent;
import edu.nyu.xiaoqianyu.tictactoe.events.MatchOverEvent;

/**
 * Created by abc on 4/3/15.
 */
public class GameModel {

    private Board board;
    private VsMode VsMode;
    private PlayerRole currentPlayer; //player1 uses circle, player2 and computer use cross
    private GameLevel gameLevel;

    public GameModel(VsMode mode) {
        board = new Board();
        VsMode = mode;
        currentPlayer = PlayerRole.PLAYER1;
        gameLevel = GameLevel.EASY;
    }

    public GameModel(VsMode mode, GameLevel level) {
        board = new Board();
        VsMode = mode;
        currentPlayer = PlayerRole.PLAYER1;
        gameLevel = level;
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
        //judge if someone wins
        boolean ifMatchOver = judgeWhoWins();
        if(ifMatchOver == true) return;
        //after every, we must change player
        changePlayer();

        //if it's computer's turn, AI player plays by itself
        if(currentPlayer == PlayerRole.COMPUTER) {
            int[] comMove = computerMove();
            if(comMove == null) return;
            board.getCells()[comMove[0]][comMove[1]].setContent(Seed.CROSS);

            //wait some time then change the cell
            Handler handlerEvent = new Handler();
            final int comMovRow = comMove[0];
            final int comMovCol = comMove[1];
            handlerEvent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new CellTouchEvent(comMovRow, comMovCol, Seed.CROSS));
                }
            }, 500);

            ifMatchOver = judgeWhoWins();
            if(ifMatchOver == true) return;
            changePlayer();
        }
    }

    private int[] computerMove() {
        AIPlayer computerPlayer;
        if(gameLevel == GameLevel.HARD) {
            computerPlayer = new AIPlayerMinimax(board);
        }
        else {
            computerPlayer = new AIPlayerTableLookup(board);
        }
        int[] computerMovRes = computerPlayer.move();
        if(computerMovRes == null) return null;
        return new int[]{computerMovRes[0], computerMovRes[1]};
    }

    private boolean judgeWhoWins() {
        boolean isSomeoneWins = false;
        //horizontal
        for(int i = 0; i <= 2; i++) {
            for(int j = 1; j <= 2; j++) {
                if(board.getCells()[i][j].getContent() !=
                   board.getCells()[i][j-1].getContent()) { // if non consistent occurs in the current row
                    break;
                }
                else {
                    if(j == 2) {
                        //someone wins
                        if(board.getCells()[i][j].getContent() == Seed.NOUGHT) {
                            //player1 wins
                            isSomeoneWins = true;
                            EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER1));
                            return true;
                        }
                        else if(board.getCells()[i][j].getContent() == Seed.CROSS){
                            //player2 or computer wins
                            isSomeoneWins = true;
                            if(VsMode == VsMode.PLAYER_VS_COM) {
                                EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.COMPUTER));
                            }
                            else {
                                EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER2));
                            }
                            return true;
                        }
                        else {
                            //continue;
                        }
                    }
                }
            }
        }

        //vertical
        for(int j = 0; j <= 2; j++) { //col
            for(int i = 1; i <= 2; i++) { //row
                if(board.getCells()[i][j].getContent() !=
                        board.getCells()[i-1][j].getContent()) { // if non consistent occurs in the current row
                    break;
                }
                else {
                    if(i == 2) {
                        //someone wins
                        if(board.getCells()[i][j].getContent() == Seed.NOUGHT) {
                            //player1 wins
                            isSomeoneWins = true;
                            EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER1));
                            return true;
                        }
                        else if(board.getCells()[i][j].getContent() == Seed.CROSS){
                            //player2 or computer wins
                            isSomeoneWins = true;
                            if(VsMode == VsMode.PLAYER_VS_COM) {
                                EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.COMPUTER));
                            }
                            else {
                                EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER2));
                            }
                            return true;
                        }
                        else {
                            //continue;
                        }
                    }
                }
            }
        }

        //angular
        for(int i = 1; i <= 2; i++) {
            if(board.getCells()[i][i].getContent() !=
               board.getCells()[i-1][i-1].getContent()) {
                break;
            }
            else{
                if(i == 2 && board.getCells()[i][i].getContent() != Seed.EMPTY) {
                    //someone wins
                    isSomeoneWins = true;
                    if(board.getCells()[i][i].getContent() == Seed.NOUGHT) {
                        //player1 wins
                        isSomeoneWins = true;
                        EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER1));
                    }
                    else if(board.getCells()[i][i].getContent() == Seed.CROSS){
                        //player2 or computer wins
                        isSomeoneWins = true;
                        if(VsMode == VsMode.PLAYER_VS_COM) {
                            EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.COMPUTER));
                        }
                        else {
                            EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER2));
                        }
                    }
                    return true;
                }
            }
        }
        for(int i = 1; i <= 2; i++) {
            if(board.getCells()[i][2-i].getContent() !=
               board.getCells()[i-1][3-i].getContent()){
                break;
            }
            else {
                if(i == 2 && board.getCells()[i][2-i].getContent() != Seed.EMPTY){
                    //someone wins
                    isSomeoneWins = true;
                    if(board.getCells()[i][2-i].getContent() == Seed.NOUGHT) {
                        //player1 wins
                        isSomeoneWins = true;
                        EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER1));
                    }
                    else if(board.getCells()[i][2-i].getContent() == Seed.CROSS){
                        //player2 or computer wins
                        isSomeoneWins = true;
                        if(VsMode == VsMode.PLAYER_VS_COM) {
                            EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.COMPUTER));
                        }
                        else {
                            EventBus.getDefault().post(new MatchOverEvent(true, PlayerRole.PLAYER2));
                        }
                    }
                    return true;
                }
            }
        }

        //judge if game draw
        boolean emptyExist = false;
        for(int i = 0; i <= 2; i++) {
            for(int j = 0; j <= 2; j++) {
                if(board.getCells()[i][j].getContent() == Seed.EMPTY)
                    emptyExist = true;
            }
        }
        if(emptyExist == false){
            EventBus.getDefault().post(new MatchOverEvent(false)); //no one wins, draw
            return true;
        }
        return false;
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

    public VsMode getVsMode() {
        return VsMode;
    }
}
