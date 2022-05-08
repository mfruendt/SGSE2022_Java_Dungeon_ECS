package newgame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

public class DesktopLauncher
{
    public DesktopLauncher()
    {

    }

    public static void run(MainController mc) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("PM-Dungeon");
        config.setForegroundFPS(30);
        config.setWindowedMode(800, 600);
        config.setResizable(false);
        new Lwjgl3Application(new GameSetup(mc), config);
    }
}
