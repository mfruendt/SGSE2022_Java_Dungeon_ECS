package newgame.characters;

import java.util.ArrayList;

import newgame.animations.CharacterAnimations;
import newgame.characters.ai.MeleeAttackStrategy;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** Class that represent an easy monster.
 *
 * @author Maxim Fründt
 */
public class HardMonster extends Monster
{
    private static final float MONSTER_HEALTH = 10.0f;

    private static final float MONSTER_EXPERIENCE_AWARD = 0.5f;

    private static final float MONSTER_TARGET_RADIUS = 1.5f;

    /** Create a new monster type that is hard
     *
     */
    public HardMonster(final Hero hero)
    {
        super(hero);
        animations = new ArrayList<>();
        attackStrategy = new MeleeAttackStrategy(MONSTER_TARGET_RADIUS);

        animations.add(Character.AnimationStates.IDLE_LEFT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_IDLE_L));
        animations.add(Character.AnimationStates.IDLE_RIGHT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_IDLE_R));
        animations.add(Character.AnimationStates.RUNNING_LEFT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_RUN_L));
        animations.add(Character.AnimationStates.RUNNING_RIGHT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_RUN_R));

        isDead = false;
        health = MONSTER_HEALTH;
        activeAnimation = Character.AnimationStates.IDLE_RIGHT;
        lastWalkedDirection = Monster.WalkingDirections.UP;

        GameEventsLogger.getLogger().info(LogMessages.CHARACTER_SPAWN + ": " + HardMonster.class.getName());
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
