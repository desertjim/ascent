package net.jamesbaca.ascent.mymodule.app;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by jamesbaca on 10/17/14.
 */
public class DemoApplication extends Application {
    private ObjectGraph mApplicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        mApplicationGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DemoApplicationModule(this));
    }

    ObjectGraph getApplicationGraph() {
        return mApplicationGraph;
    }
}
