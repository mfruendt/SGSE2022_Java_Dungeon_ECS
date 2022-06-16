package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for melee attacks
 * @author Maxim Fründt
 */
public class MeleeAttack implements Component
{
    /** Damage that will be applied to the target */
    public float damage;
    /** Radius used for collision detection */
    public float radius;
    /** Duration of the knockback that will be applied to the target in frames */
    public int knockbackDuration;
    /** Speed of the knockback that will be applied to the target */
    public float knockbackSpeed;
    /** Direction of the attack */
    public AttackDirection attackDirection;
    /** Type of entity that can receive this attack */
    public Receiver receiver;
    /** Entity that started this attack */
    public Entity attacker;

    /** Create new melee attack component
     *
     * @param damage Damage that will be applied to the target
     * @param attackDirection Direction of the attack
     * @param radius Radius used for collision detection
     * @param knockbackDuration Duration of the knockback that will be applied to the target in frames
     * @param knockbackSpeed Speed of the knockback that will be applied to the target
     * @param receiver Type of entity that can receive this attack
     * @param attacker Entity that started this attack
     */
    public MeleeAttack(float damage, AttackDirection attackDirection, float radius, int knockbackDuration, float knockbackSpeed, Receiver receiver, Entity attacker)
    {
        this.damage = damage;
        this.radius = radius;
        this.knockbackDuration = knockbackDuration;
        this.knockbackSpeed = knockbackSpeed;
        this.attackDirection = attackDirection;
        this.receiver = receiver;
        this.attacker = attacker;
    }

    /** Possible receivers of attacks
     * @author Maxim Fründt
     */
    public enum Receiver
    {
        HOSTILE,
        PLAYER
    }

    /** Possible directions for attacks
     * @author Maxim Fründt
     */
    public enum AttackDirection
    {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
