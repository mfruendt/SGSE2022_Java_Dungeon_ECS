package newgame.characters.ai;

import newgame.characters.Hero;
import newgame.characters.Monster;

/** Strategy for passive behaviours of the monster.
 *
 * @author Maxim Fründt
 */
public class PassiveAttackStrategy implements IAttackStrategy
{
    /** Execute moving.
     *
     * @param monster Monster that will be moved
     * @param hero Hero that may be used for targeting
     * @return True if moving was successful, else false
     */
    @Override
    public boolean move(Hero hero, Monster monster)
    {
        monster.calcNewRandomMovement();

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
        return false;
    }
}
