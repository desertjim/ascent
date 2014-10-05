package net.jamesbaca.ascent;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by jamesbaca on 9/28/14.
 */
public class Ascent {

    String mFontAssetFolder = "fonts/";
    HashMap<String, Typeface> mTypefaces = new HashMap<String, Typeface>();
    AssetManager mAssetManager;

    public void setAssetManager(AssetManager aAssetManager){
        mAssetManager = aAssetManager;
    }

    public void setAssetsFolder(String path){
        mFontAssetFolder = path;
    }

    public void add(String identifier, Typeface font){
        mTypefaces.put(identifier, font);
    }

    public Typeface getTypeface(String identifier){
        if(mTypefaces.get(identifier) != null){
            return mTypefaces.get(identifier);
        }else if(mAssetManager != null){
            Typeface lTypeface = Typeface.createFromAsset(mAssetManager, mFontAssetFolder+identifier);
            mTypefaces.put(identifier, lTypeface);
            return lTypeface;
        }else{
            return null;
        }
    }

    public void inject(Object target){
        try {
            TypefaceHelper helper = getHelperForClass(target.getClass());
            if (helper != null) {
                helper.applyTypeface(target, this);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new UnableToInjectException(target, e);
        }
    }

    @SuppressWarnings("unchecked")
    protected TypefaceHelper getHelperForClass(Class<?> cls) throws NoSuchMethodException {

        String clsName = cls.getName();
        TypefaceHelper typefaceHelper = null;

        try {
            String generatedClassName = clsName + InjectedAscent.SUFFIX;
            typefaceHelper = (TypefaceHelper) Class.forName(generatedClassName).newInstance();
        } catch (ClassNotFoundException e) {
            typefaceHelper = getHelperForClass(cls.getSuperclass());
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

        return typefaceHelper;
    }
    
    public class UnableToInjectException extends RuntimeException{

        public UnableToInjectException(Object target, Exception e) {
        }
    }
}
