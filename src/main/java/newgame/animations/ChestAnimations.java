package newgame.animations;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.textures.ItemTextures;
/**
 * 
 * @author Dominik Haacke
 *
 */
public class ChestAnimations 
{
	public enum Animations
    {
        // Chest animations
		CHEST_UNOPENED,
        CHEST_OPENING,
        CHEST_OPEN,
    }
	
	/* Frame time between two animation textures */
    private static final int FRAME_TIME = 8;

    /** Get a Chest animation
     * 
     *
     * @param animation Animation that should be returned
     * @return Animation of the Chest
     */
    public static Animation getAnimation(Animations animation)
    {
        switch (animation) {
            case CHEST_UNOPENED:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(ItemTextures.CHEST_FULL_OPEN_0.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            case CHEST_OPENING:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(ItemTextures.CHEST_FULL_OPEN_0.getTexture());
                animationFrames.add(ItemTextures.CHEST_FULL_OPEN_1.getTexture());
                animationFrames.add(ItemTextures.CHEST_FULL_OPEN_2.getTexture());
                return new Animation(animationFrames, FRAME_TIME);
            }
            case CHEST_OPEN:
            {
                List<Texture> animationFrames = new ArrayList<>();
                animationFrames.add(ItemTextures.CHEST_FULL_OPEN_2.getTexture());
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
