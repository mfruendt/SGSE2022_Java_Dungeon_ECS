package newgame.animations;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.textures.TrapTextures;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/** Class to load the trap animations
 *
 * @author Dominik Haacke
 */
public class TrapAnimations
{
    /** List of animations
     *
     */
    public enum Animations
    {
        DAMAGE_TRAP_IDLE,
        DAMAGE_TRAP_ACTIVATING,
        DAMAGE_TRAP_ACTIVE,
        DAMAGE_TRAP_DEACTIVATING,
        HOLE_TRAP
    }

    /* Frame time between two animation textures */
    private static final int FRAME_TIME = 8;

    /** Get a trap animation
     * 
     * @param animation Animation that should be returned
     * @return Animation of the trap
     */
    public static Animation getAnimation(Animations animation)
    {
        switch (animation)
        {
            // Get trap idle animation
            case DAMAGE_TRAP_IDLE:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(TrapTextures.TRAP_STATE_0.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            case DAMAGE_TRAP_ACTIVATING:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(TrapTextures.TRAP_STATE_0.getTexture());
                animationFrames.add(TrapTextures.TRAP_STATE_1.getTexture());
                animationFrames.add(TrapTextures.TRAP_STATE_2.getTexture());
                animationFrames.add(TrapTextures.TRAP_STATE_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            case DAMAGE_TRAP_ACTIVE:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(TrapTextures.TRAP_STATE_3.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            case DAMAGE_TRAP_DEACTIVATING:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(TrapTextures.TRAP_STATE_3.getTexture());
                animationFrames.add(TrapTextures.TRAP_STATE_2.getTexture());
                animationFrames.add(TrapTextures.TRAP_STATE_1.getTexture());
                animationFrames.add(TrapTextures.TRAP_STATE_0.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            case HOLE_TRAP:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(TrapTextures.TRAP_HOLE.getTexture());
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

