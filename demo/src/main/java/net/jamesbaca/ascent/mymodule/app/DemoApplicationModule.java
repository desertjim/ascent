package net.jamesbaca.ascent.mymodule.app;

import net.jamesbaca.ascent.Ascent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jamesbaca on 10/17/14.
 */
@Module(
        injects = {
                MainActivity.class,
        },
        library = true
)
public class DemoApplicationModule {
    private final DemoApplication mApplication;
    private final Ascent mAscent = new Ascent();

    public DemoApplicationModule(DemoApplication application) {
        mApplication = application;
    }

    @Provides @Singleton Ascent provideLocationManager() {
        mAscent.setAssetManager(mApplication.getAssets());
        return mAscent;
    }
}
