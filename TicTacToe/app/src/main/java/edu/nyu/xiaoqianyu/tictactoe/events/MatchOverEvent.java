package edu.nyu.xiaoqianyu.tictactoe.events;

import edu.nyu.xiaoqianyu.tictactoe.dataType.PlayerRole;

/**
 * Created by abc on 4/3/15.
 */
public class MatchOverEvent {
    private boolean someOneWins;
    private PlayerRole winner;

    public MatchOverEvent(boolean someOneWins) {
            this.someOneWins = someOneWins;
    }

    public MatchOverEvent(boolean someOneWins, PlayerRole winner) {
        this.someOneWins = someOneWins;
        this.winner = winner;
    }

    public boolean isSomeOneWins() {
        return someOneWins;
    }

    public PlayerRole getWinner() {
        return winner;
    }

    public void setSomeOneWins(boolean someOneWins) {
        this.someOneWins = someOneWins;
    }

    public void setWinner(PlayerRole winner) {
        this.winner = winner;
    }
}
