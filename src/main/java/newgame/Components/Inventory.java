package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

/** Component used for the inventory of entities
 * @author Maxim Fründt
 */
public class Inventory implements Component
{
    /** Items that this inventory contains */
    public List<Entity> items;
    /** Number of used spaces */
    public int usedSize;
    /** Total size of the inventory */
    public int size;

    /** Create new inventory component
     *
     * @param size Size of the inventoy
     */
    public Inventory(int size)
    {
        items = new ArrayList<>(size);
        this.size = size;
        for (int i = 0; i < size; i++)
        {
            this.items.add(null);
        }
    }
}
