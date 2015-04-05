package edu.nyu.xiaoqianyu.tictactoe.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import edu.nyu.xiaoqianyu.tictactoe.MainActivity;
import edu.nyu.xiaoqianyu.tictactoe.R;
import edu.nyu.xiaoqianyu.tictactoe.dataType.GameLevel;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;
import edu.nyu.xiaoqianyu.tictactoe.dataType.VsMode;
import edu.nyu.xiaoqianyu.tictactoe.events.CellTouchEvent;
import edu.nyu.xiaoqianyu.tictactoe.events.MatchOverEvent;
import edu.nyu.xiaoqianyu.tictactoe.model.GameModel;

public class GamePlayActivity extends ActionBarActivity {

    private GameModel gameModel;
    private ImageButton[][] buttons = new ImageButton[3][3];
    private SoundPool soundPool;
    private int winnerApplause, loserSound, cellChangedSound;
    private Dialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        VsMode mode;
        if(getIntent().getExtras().getInt(MainActivity.CHOSEN_BUTTON) == MainActivity.PLAYER_VS_COM_ID){
            mode = VsMode.PLAYER_VS_COM;
            if(getIntent().getExtras().getInt(LevelChooseActivity.GAME_LEVEL) == LevelChooseActivity.EASY_MODE_ID) {
                gameModel = new GameModel(mode, GameLevel.EASY);
            }
            else {
                gameModel = new GameModel(mode, GameLevel.HARD);
            }
        }
        else {
            mode = VsMode.PLAYER_VS_PLAYER;
            gameModel = new GameModel(mode);
        }

        buttons[0][0] = (ImageButton)findViewById(R.id.button00);
        buttons[0][1] = (ImageButton)findViewById(R.id.button01);
        buttons[0][2] = (ImageButton)findViewById(R.id.button02);
        buttons[1][0] = (ImageButton)findViewById(R.id.button10);
        buttons[1][1] = (ImageButton)findViewById(R.id.button11);
        buttons[1][2] = (ImageButton)findViewById(R.id.button12);
        buttons[2][0] = (ImageButton)findViewById(R.id.button20);
        buttons[2][1] = (ImageButton)findViewById(R.id.button21);
        buttons[2][2] = (ImageButton)findViewById(R.id.button22);

        for(int i = 0; i <= 2; i++) {
            for(int j = 0; j <= 2; j ++) {
                final int touchedCellRow = i;
                final int touchedCellCol = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
                    }
                });
            }
        }
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        winnerApplause = soundPool.load(this, R.raw.applause, 1);
        loserSound = soundPool.load(this, R.raw.fail, 1);
        cellChangedSound = soundPool.load(this, R.raw.move, 1);

        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(CellTouchEvent event) {
        soundPool.play(cellChangedSound, 0.5f, 0.5f, 1,0,1);
        //Toast.makeText(GamePlayActivity.this, "event received" +" " + event.getCellTouchedRow(), Toast.LENGTH_SHORT).show();
        if(event.getCellSeed() == Seed.EMPTY) {
            buttons[event.getCellTouchedRow()][event.getCellTouchedCol()].setImageResource(R.mipmap.empty_cell);
        }
        else if(event.getCellSeed() == Seed.NOUGHT) {
            buttons[event.getCellTouchedRow()][event.getCellTouchedCol()].setImageResource(R.mipmap.circle_cell);
        }
        else if(event.getCellSeed() == Seed.CROSS) {
            buttons[event.getCellTouchedRow()][event.getCellTouchedCol()].setImageResource(R.mipmap.cross_cell);
        }
    }

    public void onEventMainThread(MatchOverEvent event) {
        Toast.makeText(GamePlayActivity.this, "someone wins" +" " + event.isSomeOneWins(), Toast.LENGTH_SHORT).show();
        String dialogTitle, dialogButton;
        dialogButton = "OK";
        if(event.isSomeOneWins() == true) {
            soundPool.play(winnerApplause, 0.5f, 0.5f, 1,0,1);
            dialogTitle = "Congrats! " + event.getWinner() + " wins!";
        }
        else {
            soundPool.play(loserSound, 0.5f, 0.5f, 1,0,1);
            dialogTitle = "Congrats! Draw game!";
        }
        AlertDialog.Builder matchOverDialog = new AlertDialog.Builder(this);
        matchOverDialog.setMessage(dialogTitle);
        matchOverDialog.setPositiveButton(dialogButton,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        final AlertDialog alertDialog = matchOverDialog.create();
        Handler handlerEvent = new Handler();
        handlerEvent.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // Back key event
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle("EXIT")
                    .setMessage("Give up the match?")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent;
                                    if (gameModel.getVsMode() == VsMode.PLAYER_VS_COM) {
                                        intent = new Intent(getApplicationContext(), LevelChooseActivity.class);
                                    }
                                    else {
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    alertDialog.cancel();
                                }
                            }).create();

            alertDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
