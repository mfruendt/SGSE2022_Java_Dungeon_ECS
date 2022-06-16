package newgame.characters;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import org.lwjgl.system.CallbackI;

/** Camera character used to observe the playable character
 * @author Maxim Fründt
 */
public class Camera implements IDrawable
{
    /** Position of the camera */
    Point position;

    /** Create new camera character
     */
    public Camera()
    {
        position = new Point(0, 0);
    }

    /** Set the position of the camera
     *
     * @param position Position to be set
     */
    public void setPosition(Point position)
    {
        this.position = position;
    }

    /** Get the position of the camera
     *
     * @return Position of the camera
     */
    public Point getPosition()
    {
        return position;
    }

    /** Get the texture of the camera
     *
     * @return Texture of the camera
     */
    public Texture getTexture()
    {
        return null;
    }
}
