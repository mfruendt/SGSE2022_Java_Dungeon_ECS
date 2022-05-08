package newgame.textures;

import com.badlogic.gdx.graphics.Texture;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/**
 * Contains all Weapon Textures used in the Game
 * @author Dominik Haacke
 *
 */
public enum WeaponTextures 
{
    // Item Textures
    STAFF("weapon_green_magic_staff.png"),
	STAFF_BALL("Staff_Ball.png"),
	MONSTER_BALL("Monster_Ball.png"),
    BOW("Bow.png"),
	ARROW("Arrow.png"),
    REGULAR_SWORD("weapon_regular_sword.png");
	/* Path of the folder of all weapon textures */
    private static final String itemAssetsPath = "assets/textures/weapons/";

    private final String fileName;

    /* Create a new texture within given const fileName */
    WeaponTextures(final String fileName)
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
