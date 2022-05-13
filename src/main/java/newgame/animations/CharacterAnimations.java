package newgame.animations;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.textures.CharacterTextures;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/** Class to load the character animations
 *
 * @author Maxim Fründt
 */
public class CharacterAnimations
{
    /** List of animations (each animation consists of 4 textures)
     *
     */
    public enum Animations
    {
        // Hero male animations
        HERO_M_IDLE_L,
        HERO_M_IDLE_R,
        HERO_M_RUN_L,
        HERO_M_RUN_R,

        HERO_M_HIT_R,
        HERO_M_HIT_L,

        // Zombie animations
        ZOMBIE_IDLE_L,
        ZOMBIE_IDLE_R,
        ZOMBIE_RUN_L,
        ZOMBIE_RUN_R,

        // Skeleton animations
        SKELETON_IDLE_L,
        SKELETON_IDLE_R,
        SKELETON_RUN_L,
        SKELETON_RUN_R,

        // Demon animations
        DEMON_IDLE_L,
        DEMON_IDLE_R,
        DEMON_RUN_L,
        DEMON_RUN_R,

        // Wizard animations
        WIZARD_IDLE,
    }

    /* Frame time between two animation textures */
    private static final int FRAME_TIME = 4;

    /** Get a character animation
     * 
     * Each animation consists multiple idles
     *
     * @param animation Animation that should be returned
     * @return Animation of the character
     */
    public static Animation getAnimation(final Animations animation)
    {
        switch (animation) {
            // Get male hero animation for idling to the left
            case HERO_M_IDLE_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.HERO_M_IDLE_L_0.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_IDLE_L_1.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_IDLE_L_2.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_IDLE_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get male hero animation for idling to the right
            case HERO_M_IDLE_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.HERO_M_IDLE_R_0.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_IDLE_R_1.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_IDLE_R_2.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_IDLE_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get male hero animation for running to the left
            case HERO_M_RUN_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.HERO_M_RUN_L_0.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_RUN_L_1.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_RUN_L_2.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_RUN_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get male hero animation for running to the right
            case HERO_M_RUN_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.HERO_M_RUN_R_0.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_RUN_R_1.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_RUN_R_2.getTexture());
                animationFrames.add(CharacterTextures.HERO_M_RUN_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get male hero animation for hitting right
            case HERO_M_HIT_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.HERO_M_HIT_R.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get male hero animation for hitting left
            case HERO_M_HIT_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.HERO_M_HIT_L.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get zombie animation for idling to the left
            case ZOMBIE_IDLE_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_L_0.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_L_1.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_L_2.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get zombie animation for idling to the right
            case ZOMBIE_IDLE_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_R_0.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_R_1.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_R_2.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_IDLE_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get zombie animation for running to the left
            case ZOMBIE_RUN_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_L_0.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_L_1.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_L_2.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get zombie animation for running to the right
            case ZOMBIE_RUN_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_R_0.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_R_1.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_R_2.getTexture());
                animationFrames.add(CharacterTextures.ZOMBIE_RUN_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get skeleton animation for idling to the left
            case SKELETON_IDLE_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.SKELETON_IDLE_L_0.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_IDLE_L_1.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_IDLE_L_2.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_IDLE_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get skeleton animation for idling to the right
            case SKELETON_IDLE_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.SKELETON_IDLE_R_0.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_IDLE_R_1.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_IDLE_R_2.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_IDLE_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get skeleton animation for running to the left
            case SKELETON_RUN_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.SKELETON_RUN_L_0.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_RUN_L_1.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_RUN_L_2.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_RUN_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get skeleton animation for running to the right
            case SKELETON_RUN_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.SKELETON_RUN_R_0.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_RUN_R_1.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_RUN_R_2.getTexture());
                animationFrames.add(CharacterTextures.SKELETON_RUN_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get demon animation for idling to the left
            case DEMON_IDLE_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.DEMON_IDLE_L_0.getTexture());
                animationFrames.add(CharacterTextures.DEMON_IDLE_L_1.getTexture());
                animationFrames.add(CharacterTextures.DEMON_IDLE_L_2.getTexture());
                animationFrames.add(CharacterTextures.DEMON_IDLE_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get demon animation for idling to the right
            case DEMON_IDLE_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.DEMON_IDLE_R_0.getTexture());
                animationFrames.add(CharacterTextures.DEMON_IDLE_R_1.getTexture());
                animationFrames.add(CharacterTextures.DEMON_IDLE_R_2.getTexture());
                animationFrames.add(CharacterTextures.DEMON_IDLE_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get demon animation for running to the left
            case DEMON_RUN_L:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.DEMON_RUN_L_0.getTexture());
                animationFrames.add(CharacterTextures.DEMON_RUN_L_1.getTexture());
                animationFrames.add(CharacterTextures.DEMON_RUN_L_2.getTexture());
                animationFrames.add(CharacterTextures.DEMON_RUN_L_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get demon animation for running to the right
            case DEMON_RUN_R:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.DEMON_RUN_R_0.getTexture());
                animationFrames.add(CharacterTextures.DEMON_RUN_R_1.getTexture());
                animationFrames.add(CharacterTextures.DEMON_RUN_R_2.getTexture());
                animationFrames.add(CharacterTextures.DEMON_RUN_R_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }


            // Get wizard animation for idling
            case WIZARD_IDLE:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(CharacterTextures.WIZARD_IDLE_0.getTexture());
                animationFrames.add(CharacterTextures.WIZARD_IDLE_1.getTexture());
                animationFrames.add(CharacterTextures.WIZARD_IDLE_2.getTexture());
                animationFrames.add(CharacterTextures.WIZARD_IDLE_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            default:
            {
                GameEventsLogger.getLogger().severe(LogMessages.ANIMATION_NOT_FOUND.toString());
                return null;
            }
        }
    }
}
