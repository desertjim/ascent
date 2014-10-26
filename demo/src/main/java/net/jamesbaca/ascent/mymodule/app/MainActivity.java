package net.jamesbaca.ascent.mymodule.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.jamesbaca.ascent.Ascent;
import net.jamesbaca.ascent.Font;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dagger.ObjectGraph;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.title) @Font("Lobster.ttf") TextView mTitle;
    @InjectView(R.id.option1) @Font("Lobster.ttf") TextView mOption1;
    @InjectView(R.id.option2) @Font("Playball-Regular.ttf") TextView mOption2;
    @InjectView(R.id.option3) @Font("Lobster.ttf") TextView mOption3;
    @InjectView(R.id.button) @Font("Playball-Regular.ttf") TextView mTextView;
    @Inject Ascent mAscent;
    private ObjectGraph mAppGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) Setup dagger graph
        // (Allow us to not have to create a new ascent object on every activity & fragment)
        DemoApplication application = (DemoApplication) getApplication();
        mAppGraph = application.getApplicationGraph();

        // 2) Dagger injection
        mAppGraph.inject(this);

        // 3) Butterknife injection
        // skip all the dull findViewById calls
        ButterKnife.inject(this, findViewById(R.id.root));

        // 4) Ascent injection
        // leverage the top two libraries to easily set the typeface on the TextView type objects
        mAscent.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
