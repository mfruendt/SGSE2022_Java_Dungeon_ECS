package newgame;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/** Extended interface of IEntity that implements needed functions to insert it into a hash table
 * @author Maxim Fründt
 */
public interface IHashableEntity extends IEntity
{
    /** Get the position of the entity
     *
     * @return Position of the entity
     */
    Point getPosition();
}
