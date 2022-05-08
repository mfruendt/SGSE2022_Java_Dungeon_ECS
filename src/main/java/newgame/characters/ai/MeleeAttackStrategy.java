package newgame.characters.ai;

import newgame.DamageDetails;
import newgame.characters.Hero;
import newgame.characters.Monster;

/** Strategy for melee attacks
 * @author Maxim Fründt
 */
public class MeleeAttackStrategy implements IAttackStrategy
{
    /* Radius in which the player will be searched */
    private final float searchRadius;

    /** Initialize the melee attack strategy
     *
     * @param searchRadius Radius in which the player will be targeted
     */
    public MeleeAttackStrategy(float searchRadius)
    {
        this.searchRadius = searchRadius;
    }

    /** Execute moving
     *
     * @param monster Monster that will be moved
     * @param hero Hero that may be used for targeting
     * @return True if moving was successful, else false
     */
    @Override
    public boolean move(Hero hero, Monster monster)
    {
        DamageDetails damageDetails = monster.checkPlayerContact(hero.getPosition(), searchRadius);

        // If the player is in range of the melee search radius target him
        if (damageDetails.hasPlayerContact())
        {
            monster.calcMovementToPosition(hero.getPosition());
        }
        // If not move randomly
        else
        {
            monster.calcNewRandomMovement();
        }

        return true;
    }

    /** Execute an attack
     *
     * @param hero Hero that will be attacked
     * @param monster Monster that will attack
     * @return True if the attack was successful, else false
     */
    public boolean attack(Hero hero, Monster monster)
    {
        if (hero == null)
        {
            return false;
        }

        DamageDetails damageDetails = monster.checkPlayerContact(hero.getPosition(), monster.getContactRadius());

        if (damageDetails.hasPlayerContact())
        {
            hero.receiveDamage(damageDetails);

            return true;
        }

        return false;
    }
}
