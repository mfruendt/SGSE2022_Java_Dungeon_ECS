package newgame;

/** This class provides details about dealt damage to another character
 *
 * @author Maxim Fründt
 */
public class DamageDetails
{
    /** List of possible direction from which damage can be dealt
     *
     */
    public enum DamageDirections
    {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        NONE,
        ANY
    }

    private final DamageDirections damageDirection;

    /* The amount of damage that has been dealt */
    private final float damageDealt;

    private final boolean hasPlayerContact;

    /** Standard constructor that will be used when no damage has been dealt
     *
     */
    public DamageDetails()
    {
        damageDirection = null;
        damageDealt = 0;

        hasPlayerContact = false;
    }

    /** Create a new damage detail
     *
     * @param direction Direction from which the damage has been dealt
     * @param damageDealt The amount of damage that has been dealt
     */
    public DamageDetails(DamageDirections direction, float damageDealt)
    {
        this.damageDirection = direction;
        this.damageDealt = damageDealt;
        hasPlayerContact = true;
    }

    /** Get the direction from which the damage has been dealt
     *
     * @return Direction from which the damage has been dealt
     */
    public DamageDirections getDirection()
    {
        return damageDirection;
    }

    /** Get the amount of damage that has been dealt
     *
     * @return The amount of damage that has been dealt
     */
    public float getDamageDealt()
    {
        return damageDealt;
    }

    /** Check if damage has been dealt
     *
     * @return True if damage has been dealt, else false
     */
    public boolean hasPlayerContact()
    {
        return hasPlayerContact;
    }
}
