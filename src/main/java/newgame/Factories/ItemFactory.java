package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Items.HealingPotionStats;
import newgame.Components.Items.RangedWeaponStats;
import newgame.Components.Items.ShieldStats;
import newgame.Components.Position;
import newgame.Components.Sprite;
import newgame.Components.Tags.Pickup;
import newgame.Components.Items.MeleeWeaponStats;
import newgame.textures.ItemTextures;
import newgame.textures.WeaponTextures;

/** Factory to create entities that represent items
 * @author Maxim Fründt
 */
public class ItemFactory
{
    /** Number of uses of the sword */
    public static final int SWORD_USES = 2;
    /** Damage of the sword */
    public static final float SWORD_DAMAGE = 5f;
    /** Range of the sword */
    public static final float SWORD_RANGE = 2f;
    /** Use cooldown of the sword */
    public static final int SWORD_COOLDOWN = 8;
    /** Knockback speed of the sword */
    public static final float SWORD_KNOCKBACK_SPEED = 0f;
    /** Knockback duration of the sword */
    public static final int SWORD_KNOCKBACK_DURATION = 0;

    /** Number of uses of the staff */
    public static final int STAFF_USES = 3;
    /** Damage of the staff */
    public static final float STAFF_DAMAGE = 10f;
    /** Range of the staff */
    public static final float STAFF_RANGE = 2f;
    /** Use cooldown of the staff */
    public static final int STAFF_COOLDOWN = 16;
    /** Speed of the attack of the staff */
    public static final float STAFF_SPEED = 0.5f;
    /** Duration of the attack of the staff */
    public static final int STAFF_DURATION = 20;

    /** Number of uses of the shield */
    public static final int SHIELD_USES = 3;
    /** Protection of the staff */
    public static final float SHIELD_PROTECTION = 0.5f;

    /** Amount of health that the health potion restores */
    public static final float HEALTH_POTION_RESTORED_HEALTH = 5f;
    /** Number of uses of the health potion */
    public static final int HEALTH_POTION_USES = 1;
    /** Use cooldown of the health potion */
    public static final int HEALTH_POTION_COOLDOWN = 8;

    /** Create new entity that represents a sword item
     *
     * @param level Level of the item
     * @return Entity that represents a sword item
     */
    public static Entity createSwordItem(DungeonWorld level)
    {
        Entity sword = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        sword.add(new Position(spawnPosition.x, spawnPosition.y, level));
        sword.add(new Sprite(WeaponTextures.REGULAR_SWORD.getTexture()));
        sword.add(new MeleeWeaponStats(SWORD_USES, SWORD_DAMAGE, SWORD_RANGE, SWORD_COOLDOWN, SWORD_KNOCKBACK_SPEED, SWORD_KNOCKBACK_DURATION));
        sword.add(new Pickup("Sword"));

        return sword;
    }

    /** Create new entity that represents a staff item
     *
     * @param level Level of the item
     * @return Entity that represents a staff item
     */
    public static Entity createStaffItem(DungeonWorld level)
    {
        Entity staff = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        staff.add(new Position(spawnPosition.x, spawnPosition.y, level));
        staff.add(new Sprite(WeaponTextures.STAFF.getTexture()));
        staff.add(new RangedWeaponStats(STAFF_USES, STAFF_DAMAGE, STAFF_RANGE, STAFF_COOLDOWN, STAFF_SPEED, STAFF_DURATION));
        staff.add(new Pickup("Staff"));

        return staff;
    }

    /** Create new entity that represents a shield item
     *
     * @param level Level of the item
     * @return Entity that represents a shield item
     */
    public static Entity createShieldItem(DungeonWorld level)
    {
        Entity shield = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        shield.add(new Position(spawnPosition.x, spawnPosition.y, level));
        shield.add(new Sprite(ItemTextures.SHIELD.getTexture()));
        shield.add(new ShieldStats(SHIELD_USES, SHIELD_PROTECTION));
        shield.add(new Pickup("Shield"));

        return shield;
    }

    /** Create new entity that represents a health potion item
     *
     * @param level Level of the item
     * @return Entity that represents a health potion item
     */
    public static Entity createHealthPotionItem(DungeonWorld level)
    {
        Entity healthPotion = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        healthPotion.add(new Position(spawnPosition.x, spawnPosition.y, level));
        healthPotion.add(new Sprite(ItemTextures.FLASK_BIG_GREEN.getTexture()));
        healthPotion.add(new HealingPotionStats(HEALTH_POTION_RESTORED_HEALTH, HEALTH_POTION_USES, HEALTH_POTION_COOLDOWN));
        healthPotion.add(new Pickup("Health Potion"));

        return healthPotion;
    }
}
