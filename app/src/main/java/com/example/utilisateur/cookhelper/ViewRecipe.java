package com.example.utilisateur.cookhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.android.gms.common.api.GoogleApiClient;

public class ViewRecipe extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private String [] data1 ={"Hiren", "Pratik", "Dhruv", "Narendra", "Piyush", "Priyank"};
    private RatingBar mBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        final ListView lView1 = (ListView) findViewById(R.id.instructionListViewCheck);
        final ListView lView2 = (ListView) findViewById(R.id.ingredientListView);

        lView1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, data1));
        lView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lView1.getLayoutParams().height = 900 ;                                                //set to array size
        lView2.getLayoutParams().height = 1000 ;
        mBar = (RatingBar) findViewById(R.id.ratingScore);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_example, menu);
        return true;
    }

    // To respond to menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                System.out.println("Editing recipe...");
            case R.id.menu_trash:
                System.out.println("Trashing recipe...");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickRatingBar(View v) {
        mBar.getRating() ;
    }

}

