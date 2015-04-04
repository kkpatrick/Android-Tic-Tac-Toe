package edu.nyu.xiaoqianyu.tictactoe.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import edu.nyu.xiaoqianyu.tictactoe.MainActivity;
import edu.nyu.xiaoqianyu.tictactoe.R;

public class LevelChooseActivity extends ActionBarActivity {

    private Button easyModeButton;
    private Button hardModeButton;
    public final static int EASY_MODE_ID = 1;
    public final static String EASY_MODE = "easy_mode";
    public final static int HARD_MODE_ID = 2;
    public final static String HARD_MODE = "hard_mode";
    public final static String GAME_LEVEL = "game_level";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choose);

        easyModeButton = (Button)findViewById(R.id.button1);
        hardModeButton = (Button)findViewById(R.id.button2);

        easyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GamePlayActivity.class);
                intent.putExtra(GAME_LEVEL, EASY_MODE_ID);
                intent.putExtra(MainActivity.CHOSEN_BUTTON, MainActivity.PLAYER_VS_COM_ID);
                startActivity(intent);
                finish();
            }
        });

        hardModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GamePlayActivity.class);
                intent.putExtra(GAME_LEVEL, HARD_MODE_ID);
                intent.putExtra(MainActivity.CHOSEN_BUTTON, MainActivity.PLAYER_VS_COM_ID);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_choose, menu);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
