package com.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
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

    public void loadMenuAssets(AssetManager assetManager) {
        Gdx.app.debug(TAG, "Loading menu assets");

        assetManager.load(Constants.TEXTURE_ATLAS_MENU, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String str : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + str);
        }
    }

    public void loadBars() {
        Gdx.app.debug(TAG, "Loading Bars assets");
        //assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        assetManager.load("background.png", Texture.class);
        assetManager.load("logo.png", Texture.class);
        assetManager.load("progress_bar.png", Texture.class);
        assetManager.load("progress_bar_base.png", Texture.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String str : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + str);
        }
    }
    public void loadGameAssets(){
        Gdx.app.debug(TAG, "Loading Game assets");
        assetManager.load(Constants.TEXTURE_ATLAS_GAME, TextureAtlas.class);

        assetManager.load("BigImg/img (1).jpg", Texture.class);
        assetManager.load("BigImg/img (2).jpg", Texture.class);
        assetManager.load("BigImg/img (3).jpg", Texture.class);
        assetManager.load("BigImg/img (4).jpg", Texture.class);
        assetManager.load("BigImg/img (5).jpg", Texture.class);
        assetManager.load("BigImg/img (6).jpg", Texture.class);
//        assetManager.load("BigImg/img (7).jpg", Texture.class);
//        assetManager.load("BigImg/img (8).jpg", Texture.class);
//        assetManager.load("BigImg/img (9).jpg", Texture.class);
//        assetManager.load("BigImg/img (10).jpg", Texture.class);
//        assetManager.load("BigImg/img (11).jpg", Texture.class);
        //assetManager.finishLoading();
       // initGameTextures();
    }

    public void init(AssetManager assetManager) {
        Gdx.app.debug(TAG, "Init AssetManager");
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
    }
    // init textures after loading game assets
    public void initGameTextures(){
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String str : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + str);
        }

        if(assetManager.isLoaded(Constants.TEXTURE_ATLAS_GAME)) {
            TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_GAME, TextureAtlas.class);
            for(Texture texture : atlas.getTextures()) {
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        Gdx.app.debug(TAG, "# Sprites from Game atlas loaded: " + atlas.getRegions().size);
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
        } else{

            Gdx.app.debug(TAG, "Atlas not loaded");
        }




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

    public AssetManager getAssetManager() {
        return assetManager;
    }
}


