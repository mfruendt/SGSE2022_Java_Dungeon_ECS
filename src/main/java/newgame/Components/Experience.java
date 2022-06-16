package newgame.Components;

import com.badlogic.ashley.core.Component;

/** Component used for entity experience points
 * @author Maxim Fründt
 */
public class Experience implements Component
{
    /** Experience of this entity */
    public float experience;

    /** Create new experience component
     *
     * @param initialExperience Initial experience of the entity
     */
    public Experience(float initialExperience)
    {
        experience = initialExperience;
    }
}
