package newgame.characters;

import newgame.DamageDetails;
import newgame.animations.CharacterAnimations;
import newgame.characters.ai.MeleeAttackStrategy;
import newgame.characters.ai.RangedAttackStrategy;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

import java.util.ArrayList;

public class MiniBoss extends Monster
{
    private static final float MONSTER_HEALTH = 30.0f;

    private static final float MONSTER_EXPERIENCE_AWARD = 2.0f;

    private static final float MONSTER_MELEE_TARGET_RADIUS = 2.0f;

    private static final float MONSTER_RANGED_TARGET_RADIUS = 4.0f;

    /** Create a new monster type that is a mini boss
     *
     * @param hero Hero that will be targeted
     */
    public MiniBoss(final Hero hero)
    {
        super(hero);
        animations = new ArrayList<>();
        attackStrategy = new MeleeAttackStrategy(MONSTER_MELEE_TARGET_RADIUS);

        animations.add(Character.AnimationStates.IDLE_LEFT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_IDLE_L));
        animations.add(Character.AnimationStates.IDLE_RIGHT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_IDLE_R));
        animations.add(Character.AnimationStates.RUNNING_LEFT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_RUN_L));
        animations.add(Character.AnimationStates.RUNNING_RIGHT.getIndex(), CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_RUN_R));

        isDead = false;
        health = MONSTER_HEALTH;
        activeAnimation = Character.AnimationStates.IDLE_RIGHT;
        lastWalkedDirection = Monster.WalkingDirections.UP;

        GameEventsLogger.getLogger().info(LogMessages.CHARACTER_SPAWN + ": " + MiniBoss.class.getName());
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
        
        updateCooldownTimer();
        
        DamageDetails details = checkPlayerContact(hero.getPosition(), MONSTER_RANGED_TARGET_RADIUS);

        // If the player is in range of the ranged attack
        if (details.hasPlayerContact())
        {
            details = checkPlayerContact(hero.getPosition(), MONSTER_MELEE_TARGET_RADIUS);

            // And the player is not in the range of the melee attack
            if (!details.hasPlayerContact())
            {
                // Set strategy to ranged attack
                if (!(attackStrategy instanceof RangedAttackStrategy))
                {
                    attackStrategy = new RangedAttackStrategy(MONSTER_RANGED_TARGET_RADIUS);
                }
            }
            // If the player is in range of the melee attack
            else
            {
                // Set strategy to melee attack
                if (!(attackStrategy instanceof MeleeAttackStrategy))
                {
                    attackStrategy = new MeleeAttackStrategy(MONSTER_MELEE_TARGET_RADIUS);
                }
            }
        }

        // Calculate movement of the monster
        attackStrategy.move(hero, this);
    }
}
