package edu.nyu.xiaoqianyu.tictactoe;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import edu.nyu.xiaoqianyu.tictactoe.activity.GamePlayActivity;
import edu.nyu.xiaoqianyu.tictactoe.activity.LevelChooseActivity;
import edu.nyu.xiaoqianyu.tictactoe.dataType.VsMode;


public class MainActivity extends ActionBarActivity {

    public static String PLAYER_VS_COM = "Player_vs_com";
    public static String PLAYER1_VS_PLAYER2 = "Player1_vs_player2";
    public static String CHOSEN_BUTTON = "Chosen_button";
    public static int PLAYER_VS_COM_ID = 1;
    public static int PLAYER1_VS_PLAYER2_ID = 2;
    private Button button1;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LevelChooseActivity.class);
                //intent.putExtra(CHOSEN_BUTTON, PLAYER_VS_COM_ID);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GamePlayActivity.class);
                intent.putExtra(CHOSEN_BUTTON, PLAYER1_VS_PLAYER2_ID);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
