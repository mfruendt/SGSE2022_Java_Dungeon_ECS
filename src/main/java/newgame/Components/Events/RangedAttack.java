package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for ranged attacks
 * @author Maxim Fründt
 */
public class RangedAttack implements Component
{
    /** Damage that will be applied to the target */
    public float damage;
    /** Range that this attack can travel */
    public float bulletRange;
    /** Duration that this attack can travel */
    public int attackDurationLeft;
    /** Flag if this attack has hit something */
    public boolean hasHit;
    /** Type of entity that can receive this attack */
    public Receiver receiver;
    /** Entity that started this attack */
    public Entity attacker;

    /** Create new ranged attack component
     *
     * @param damage Damage that will be applied to the target
     * @param attackDuration Duration that this attack can travel
     * @param bulletRange Range that this attack can travel
     * @param receiver Type of entity that can receive this attack
     * @param attacker Entity that started this attack
     */
    public RangedAttack(float damage, int attackDuration, float bulletRange, Receiver receiver, Entity attacker)
    {
        this.damage = damage;
        this.bulletRange = bulletRange;
        attackDurationLeft = attackDuration;
        this.receiver = receiver;
        this.attacker = attacker;
        hasHit = false;
    }

    /** Possible receivers of attacks
     * @author Maxim Fründt
     */
    public enum Receiver
    {
        HOSTILE,
        PLAYER
    }
}
