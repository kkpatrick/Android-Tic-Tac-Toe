package edu.nyu.xiaoqianyu.tictactoe.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import edu.nyu.xiaoqianyu.tictactoe.MainActivity;
import edu.nyu.xiaoqianyu.tictactoe.R;
import edu.nyu.xiaoqianyu.tictactoe.dataType.Seed;
import edu.nyu.xiaoqianyu.tictactoe.dataType.VsMode;
import edu.nyu.xiaoqianyu.tictactoe.events.CellTouchEvent;
import edu.nyu.xiaoqianyu.tictactoe.model.GameModel;

public class GamePlayActivity extends ActionBarActivity {

    private int touchedCellRow, touchedCellCol;
    private GameModel gameModel;
    /*
    private ImageButton button00, button01, button02,
            button10, button11, button12,
            button20, button21, button22;*/
    private ImageButton[][] buttons = new ImageButton[3][3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        VsMode mode;
        if(getIntent().getExtras().getString(MainActivity.CHOSEN_BUTTON) == MainActivity.PLAYER_VS_COM){
            mode = VsMode.PLAYER_VS_COM;
        }
        else {
            mode = VsMode.PLAYER_VS_PLAYER;
        }
        gameModel = new GameModel(mode);

        buttons[0][0] = (ImageButton)findViewById(R.id.button00);
        buttons[0][1] = (ImageButton)findViewById(R.id.button01);
        buttons[0][2] = (ImageButton)findViewById(R.id.button02);
        buttons[1][0] = (ImageButton)findViewById(R.id.button10);
        buttons[1][1] = (ImageButton)findViewById(R.id.button11);
        buttons[1][2] = (ImageButton)findViewById(R.id.button12);
        buttons[2][0] = (ImageButton)findViewById(R.id.button20);
        buttons[2][1] = (ImageButton)findViewById(R.id.button21);
        buttons[2][2] = (ImageButton)findViewById(R.id.button22);

        buttons[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 0;
                touchedCellCol = 0;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 0;
                touchedCellCol = 1;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 0;
                touchedCellCol = 2;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 1;
                touchedCellCol = 0;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 1;
                touchedCellCol = 1;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 1;
                touchedCellCol = 2;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 2;
                touchedCellCol = 0;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 2;
                touchedCellCol = 1;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        buttons[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchedCellRow = 2;
                touchedCellCol = 2;
                gameModel.cellOnTouch(touchedCellRow, touchedCellCol);
            }
        });

        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(CellTouchEvent event) {
        Toast.makeText(GamePlayActivity.this, "event received" +" " + event.getCellTouchedRow(), Toast.LENGTH_SHORT).show();
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
}
