package newgame.characters;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import org.lwjgl.system.CallbackI;

public class Camera implements IDrawable
{
    Point position;

    public Camera()
    {
        position = new Point(0, 0);
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public Point getPosition()
    {
        return position;
    }

    public Texture getTexture()
    {
        return null;
    }
}
