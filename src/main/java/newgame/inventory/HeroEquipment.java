package newgame.inventory;

import newgame.items.Shield;
import newgame.items.weapons.Weapon;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/**
 * The equipment of the hero
 * 
 * @author Benjamin Krüger
 */
public class HeroEquipment
{
    private Weapon weapon;
    private Shield shield;

    /**
     * Create new hero equipment object (with no weapon and no shield).
     */
    public HeroEquipment()
    {

    }

    /**
     * Get equipped weapon.
     * 
     * @return The equipped weapon. Null, if no equipped.
     */
    public Weapon getWeapon()
    {
        return this.weapon;
    }

    /**
     * Get equipped shield.
     * 
     * @return equipped shield
     */
    public Shield getShield()
    {
        return this.shield;
    }

    /**
     * Equip hero with weapon
     * 
     * @param toBeEquippedWeapon to be equipped weapon
     */
    public boolean equipWeapon(final Weapon toBeEquippedWeapon)
    {
        if (toBeEquippedWeapon == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return false;
        }

        this.weapon = toBeEquippedWeapon;
        GameEventsLogger.getLogger().info(LogMessages.WEAPON_EQUIPPED.toString());
        return true;
    }

    /**
     * Equip hero with shield
     * 
     * @param toBeEquippedShield to be equipped shield
     */
    public boolean equipShield(final Shield toBeEquippedShield)
    {
        if (toBeEquippedShield == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return false;
        }

        this.shield = toBeEquippedShield;
        GameEventsLogger.getLogger().info(LogMessages.SHIELD_EQUIPPED.toString());
        return true;
    }

    /**
     * Remove equipped weapon
     * 
     * @return removed weapon. null, if no weapon was equipped
     */
    public Weapon removeWeapon()
    {
        Weapon toBeRemovedWeapon = this.weapon;
        this.weapon = null;
        return toBeRemovedWeapon;
    }

    /**
     * Remove equipped shield
     * 
     * @return removed shield. null, if no shield was equipped
     */
    public Shield removeShield()
    {
        Shield toBeRemovedShield = this.shield;
        this.shield = null;
        return toBeRemovedShield;
    } 
}
