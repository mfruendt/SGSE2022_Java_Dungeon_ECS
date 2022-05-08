package newgame.textures;

import com.badlogic.gdx.graphics.Texture;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/**
 * Contains all Item Textures used in the Game
 * @author Dominik Haacke
 *
 */
public enum ItemTextures 
{
    // Item Textures
    FLASK_BIG_GREEN("flask_big_green.png"),
	FLASK_BIG_BLUE("flask_big_blue.png"),
	SHIELD("crate.png"),
	SATCHEL("satchel.png"),
	//Chest Textures
	CHEST_FULL_OPEN_0("chest_full_open_anim_f0.png"),
	CHEST_FULL_OPEN_1("chest_full_open_anim_f1.png"),
	CHEST_FULL_OPEN_2("chest_full_open_anim_f2.png");
	/* Path of the folder of all item textures */
    private static final String itemAssetsPath = "assets/textures/items/";

    private final String fileName;

    /* Create a new texture within given const fileName */
    ItemTextures(final String fileName)
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
