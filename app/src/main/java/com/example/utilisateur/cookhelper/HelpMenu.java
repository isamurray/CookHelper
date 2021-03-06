package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.graphics.Paint;
import android.widget.Toast;

public class HelpMenu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);

        Button button = (Button) findViewById(R.id.hrechercher);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button button1 = (Button) findViewById(R.id.hajoutrecette);
        button1.setPaintFlags(button1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button button2 = (Button) findViewById(R.id.hajouting);
        button2.setPaintFlags(button2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void onClickHelpSearch(View v) {
        Intent intent = new Intent(getApplicationContext(), HelpSearchRecipe.class); //Application Context and Activity
        startActivityForResult (intent,0);
    }

    public void onClickHelpAddRecipe(View v) {
        Intent intent = new Intent(getApplicationContext(), HelpAddRecipe.class); //Application Context and Activity
        startActivityForResult (intent,0);
    }

    public void onClickHelpAddIngredient(View v) {
        Intent intent = new Intent(getApplicationContext(), HelpAddIngredient.class); //Application Context and Activity
        startActivityForResult (intent,0);
    }

    //menu in title bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainmenu, menu);
        return true;
    }

    // To respond to menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main:
                Intent intent2 = new Intent(getApplication(), MainActivity.class);
                startActivityForResult(intent2, 0);
                return true;
            default:
                Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
}
