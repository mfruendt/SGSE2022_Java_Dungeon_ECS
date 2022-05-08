package newgame.textures;

import com.badlogic.gdx.graphics.Texture;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/**
 * Contains all Trap Textures used in the Game
 * @author Dominik Haacke
 *
 */
public enum TrapTextures 
{
	//Trap Textures
	TRAP_STATE_0("floor_spikes_anim_f0.png"),
	TRAP_STATE_1("floor_spikes_anim_f1.png"),
	TRAP_STATE_2("floor_spikes_anim_f2.png"),
	TRAP_STATE_3("floor_spikes_anim_f3.png"),
	TRAP_HOLE("hole.png");
    private static final String itemAssetsPath = "assets/textures/dungeon/trap/";

    private final String fileName;

    /* Create a new texture within given const fileName */
    TrapTextures(final String fileName)
    {
        this.fileName = fileName;
    }

    /** Get the texture with the input file name
     *
     * @return Loaded texture
     */
    public Texture getTexture()
    {
        if (fileName != null)
        {
            return new Texture(itemAssetsPath + fileName);
        }
        else
        {
            GameEventsLogger.getLogger().severe(LogMessages.NO_TEXTURE_FOUND.toString());
            throw new IllegalArgumentException(LogMessages.NO_TEXTURE_FOUND.toString());
        }
    }
}
