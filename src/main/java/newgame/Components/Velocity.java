package newgame.Components;

import com.badlogic.ashley.core.Component;

/** Component for movement velocity
 * @author Maxim Fründt
 */
public class Velocity implements Component
{
    /** Horizontal component of the velocity */
    public float x;
    /** Vertical component of the velocity */
    public float y;

    /** Create new velocity component
     */
    public Velocity()
    {
        float x = 0;
        float y = 0;
    }

    /** Create new velocity component
     *
     * @param x Horizontal component of the velocity
     * @param y Vertical component of the velocity
     */
    public Velocity(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
