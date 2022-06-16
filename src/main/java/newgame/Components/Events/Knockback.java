package newgame.Components.Events;

import com.badlogic.ashley.core.Component;

/** Component for knockbacks
 * @author Maxim Fründt
 */
public class Knockback implements Component
{
    /** Duration of the knockback in frames */
    public int knockbackFrames;
    /** Applied speed during the knockback */
    public float speed;
    /** Direction of the knockback */
    public Direction direction;

    /** Create new knockback component
     *
     * @param knockbackFrames Duration of the knockback in frames
     * @param speed Applied speed during the knockback
     * @param direction Direction of the knockback
     */
    public Knockback(int knockbackFrames, float speed, Direction direction)
    {
        this.knockbackFrames = knockbackFrames;
        this.speed = speed;
        this.direction = direction;
    }

    /** Direction in which the knockback may occur
     * @author Maxim Fründt
     */
    public enum Direction
    {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
