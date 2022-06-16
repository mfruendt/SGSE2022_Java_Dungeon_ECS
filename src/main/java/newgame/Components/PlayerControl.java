package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;

/** Component used for player control of entities
 * @author Maxim Fründt
 */
public class PlayerControl implements Component
{
    /** Key used to walk up */
    public static int walkForwardKey = Input.Keys.W;
    /** Key used to walk down */
    public static int walkBackwardKey = Input.Keys.S;
    /** Key used to walk left */
    public static int walkLeftKey = Input.Keys.A;
    /** Key used to walk right */
    public static int walkRightKey = Input.Keys.D;

    /** Key used to attack up */
    public static int attackForwardKey = Input.Keys.UP;
    /** Key used to attack down */
    public static int attackBackwardKey = Input.Keys.DOWN;
    /** Key used to attack right */
    public static int attackRightKey = Input.Keys.RIGHT;
    /** Key used to attack left */
    public static int attackLeftKey = Input.Keys.LEFT;

    /** Key used to pick up items */
    public static int pickupKey = Input.Keys.F;
    /** Key used to drop items */
    public static int dropKey = Input.Keys.R;
    /** Key used to use items */
    public static int useKey = Input.Keys.U;

    /** Movement speed */
    public float speed;

    /** Create new player control component
     *
     * @param speed Movement speed
     */
    public PlayerControl(float speed)
    {
        this.speed = speed;
    }
}
