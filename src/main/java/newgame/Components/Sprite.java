package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

/** Component for sprites to render
 * @author Maxim Fründt
 */
public class Sprite implements Component
{
    /** Sprite used to render */
    public com.badlogic.gdx.graphics.g2d.Sprite sprite;

    /** Create a new sprite component
     *
     * @param texture Texture that will be rendered
     */
    public Sprite(Texture texture)
    {
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture);
    }
}
