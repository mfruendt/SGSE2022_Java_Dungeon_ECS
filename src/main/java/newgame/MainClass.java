package newgame;

/** Main class of the game
 *
 * @author Maxim Fründt
 */
public class MainClass
{
    /** Main method
     *
     * @param args additional execution arguments
     */
    public static void main(String[] args)
    {
        // Run the game
        DesktopLauncher.run(new GameHandler());
    }
}
