package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","seconduser");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = 1080/2;
		//config.height = 1920/2;
		config.width = 1440/2;
		config.height = 1920/2;
		new LwjglApplication(new Main(), config);

	}
}
