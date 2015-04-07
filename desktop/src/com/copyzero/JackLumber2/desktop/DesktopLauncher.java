package com.copyzero.JackLumber2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.copyzero.JackLumber2.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Day A Canadian Saved The World";
		config.width = 800;
		config.height = 480;

		new LwjglApplication(new Main(), config);
	}
}
