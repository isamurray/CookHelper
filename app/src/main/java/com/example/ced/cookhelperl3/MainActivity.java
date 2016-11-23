package com.example.ced.cookhelperl3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_menu_example, menu);
        return true;
    }

    // To respond to menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_edit:
                System.out.println("Editing recipe...");
            case R.id.menu_trash:
                System.out.println("Trashing recipe...");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
