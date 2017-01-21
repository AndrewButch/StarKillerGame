package com.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.game.Constants;


public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getSimpleName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public AtlasRegion player;
    public AtlasRegion shoot;
    public AtlasRegion bg1;
    public AtlasRegion bg2;
    public AtlasRegion bg3;
    public AtlasRegion smallStars;
    public AtlasRegion bigStars;
    public AssetFonts fonts;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        Gdx.app.debug(TAG, "Init AssetManager");
        this.assetManager = assetManager;

        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String str : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + str);
        }

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS, TextureAtlas.class);

        for(Texture texture : atlas.getTextures()) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        Gdx.app.debug(TAG, "# of sprites loaded: " + atlas.getRegions().size);
        for(AtlasRegion regions : atlas.getRegions()) {
            Gdx.app.debug(TAG, "asset: " + regions.name);
        }

        player = atlas.findRegion("SpaceShip");
        shoot = atlas.findRegion("star");
        bg1 = atlas.findRegion("clouds1");
        bg2 = atlas.findRegion("clouds2");
        bg3 = atlas.findRegion("clouds3");
        smallStars = atlas.findRegion("smallStars");
        bigStars = atlas.findRegion("bigStars");
        fonts = new AssetFonts();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn`t load asset '" + asset.fileName + "'", throwable );
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultBig.dispose();
    }


    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts() {
            defaultSmall = new BitmapFont(Gdx.files.internal("Font/arial-15.fnt"), true);
            defaultNormal = new BitmapFont(Gdx.files.internal("Font/arial-15.fnt"), true);
            defaultBig = new BitmapFont(Gdx.files.internal("Font/arial-15.fnt"), true);

            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);

            defaultSmall.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        }
    }
}


