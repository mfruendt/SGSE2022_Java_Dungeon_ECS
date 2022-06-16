package newgame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

/** Desktop launcher of the game
 * @author Maxim Fründt
 */
public class DesktopLauncher
{
    /** Create new desktop launcher
     */
    public DesktopLauncher()
    {

    }

    /** Run the desktop launcher
     *
     * @param mc Controller used to run
     */
    public static void run(MainController mc) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("PM-Dungeon");
        config.setForegroundFPS(30);
        config.setWindowedMode(800, 600);
        config.setResizable(false);
        new Lwjgl3Application(new GameSetup(mc), config);
    }
}
