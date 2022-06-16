package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component used for entity health
 * @author Maxim Fründt
 */
public class Health implements Component
{
    /** Current health of the entity */
    public float currentHealth;
    /** Maximum health of the entity */
    public float maxHealth;
    /** Entity that last attacked this entity */
    public Entity lastAttacker;

    /** Create new health component
     *
     * @param maxHealth Maximum health of the entity
     */
    public Health(float maxHealth)
    {
        currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }
}
