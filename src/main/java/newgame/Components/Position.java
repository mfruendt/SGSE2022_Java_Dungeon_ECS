package newgame.Components;

import com.badlogic.ashley.core.Component;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;

/** Component for positions within the level
 * @author Maxim Fründt
 */
public class Position implements Component
{
    /** Horizontal component of the position */
    public float x;
    /** Vertical component of the position */
    public float y;
    /** Level in which this position is located */
    public DungeonWorld level;

    /** Create new position component
     */
    public Position()
    {

    }

    /** Create new position component
     *
     * @param x Horizontal component of the position
     * @param y Vertical component of the position
     * @param level Level in which this position is located
     */
    public Position(float x, float y, DungeonWorld level)
    {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    /** Create new position component
     *
     * @param position Position
     */
    public Position(Position position)
    {
        x = position.x;
        y = position.y;
        level = position.level;
    }
}
