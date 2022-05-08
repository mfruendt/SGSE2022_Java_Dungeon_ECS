package newgame.characters.ai;

import newgame.characters.Hero;
import newgame.characters.Monster;

/** Interface for the monster attack strategies
 * @author Maxim Fründt
 */
public interface IAttackStrategy
{
    /** Execute moving
     *
     * @param monster Monster that will be moved
     * @param hero Hero that may be used for targeting
     * @return True if moving was successful, else false
     */
    boolean move(Hero hero, Monster monster);

    /** Execute an attack
     *
     * @param hero Hero that will be attacked
     * @param monster Monster that will attack
     * @return True if the attack was successful, else false
     */
    boolean attack(Hero hero, Monster monster);
}
