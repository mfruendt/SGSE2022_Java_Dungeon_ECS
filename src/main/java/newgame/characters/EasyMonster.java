package newgame.characters;

import java.util.ArrayList;

import newgame.DamageDetails;
import newgame.animations.CharacterAnimations;
import newgame.characters.ai.MeleeAttackStrategy;
import newgame.characters.ai.PassiveAttackStrategy;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** Class that represent an easy monster.
 *
 * @author Maxim Fründt
 */
public class EasyMonster extends Monster
{
    private static final float MONSTER_HEALTH = 5.0f;

    private static final float MONSTER_EXPERIENCE_AWARD = 0.1f;

    private static final float MONSTER_TARGET_RADIUS = 10.0f;

    /** Create a new monster type that is easy
     *
     */
    public EasyMonster(final Hero hero)
    {
        super(hero);
        animations = new ArrayList<>();
        attackStrategy = new PassiveAttackStrategy();

        animations.add(AnimationStates.IDLE_LEFT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_IDLE_L));
        animations.add(AnimationStates.IDLE_RIGHT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_IDLE_R));
        animations.add(AnimationStates.RUNNING_LEFT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_RUN_L));
        animations.add(AnimationStates.RUNNING_RIGHT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_RUN_R));

        isDead = false;
        health = MONSTER_HEALTH;
        activeAnimation = AnimationStates.IDLE_RIGHT;
        lastWalkedDirection = WalkingDirections.UP;

        GameEventsLogger.getLogger().info(LogMessages.CHARACTER_SPAWN + ": " + EasyMonster.class.getName());
    }

    /** Function to receive damage. For the monster this will damage the monster
     *
     * @param damageDetails details about received damage
     */
    @Override
    public boolean receiveDamage(final DamageDetails damageDetails)
    {
        if (damageDetails == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return false;
        }
        health -= damageDetails.getDamageDealt();
        GameEventsLogger.getLogger().fine(LogMessages.HERO_HARMED_MONSTER.toString());

        attackStrategy = new MeleeAttackStrategy(MONSTER_TARGET_RADIUS);

        if (health <= 0)
        {
            onDeath();
        }
        return true;
    }

    /** Get the experience points awarded for killing the monster
     *
     * @return Experience points in percent (1.0 = 100% level progress = new level)
     */
    @Override
    public float getExperiencePoints()
    {
        return MONSTER_EXPERIENCE_AWARD;
    }

    /** Update function of the monster that will be called by the game handler
     *
     */
    @Override
    public void update()
    {
        // Redraw the monster
        this.draw();

        // Calculate movement of the monster
        attackStrategy.move(hero, this);
    }
}
