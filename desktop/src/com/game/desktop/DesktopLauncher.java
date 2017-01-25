package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","seconduser");
		boolean rebuild = true;

		if (rebuild) {
			TexturePacker.process("InputGame", "OutputGame", "atlas.atlas");
			TexturePacker.process( "InputMenu", "OutputMenu", "atlas.atlas");
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1080/2;
		config.height = 1920/2;
		config.useGL30 = false;
		//config.width = 1440/2;
		//config.height = 1920/2;
		new LwjglApplication(new Main(), config);

	}
}
