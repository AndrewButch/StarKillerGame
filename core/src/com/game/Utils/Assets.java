package com.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.game.Constants;


public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getSimpleName();
    private static Assets instance;
    private AssetManager assetManager;

    private Assets() {

    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String str : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + str);
        }
    }

    public static Assets getInstance() {
        if (instance == null)
            instance = new Assets();
        return instance;
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn`t load asset '" + asset.fileName + "'", (Exception)throwable );
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
