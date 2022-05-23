package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Items.HealingPotionStats;
import newgame.Components.Items.RangedWeaponStats;
import newgame.Components.Items.ShieldStats;
import newgame.Components.Position;
import newgame.Components.RangedCombatStats;
import newgame.Components.Sprite;
import newgame.Components.Tags.Pickup;
import newgame.Components.Items.MeleeWeaponStats;
import newgame.textures.ItemTextures;
import newgame.textures.WeaponTextures;

public class ItemFactory
{
    public static final int SWORD_USES = 2;
    public static final float SWORD_DAMAGE = 5f;
    public static final float SWORD_RANGE = 2f;
    public static final int SWORD_COOLDOWN = 8;
    public static final float SWORD_KNOCKBACK_SPEED = 0f;
    public static final int SWORD_KNOCKBACK_DURATION = 0;

    public static final int STAFF_USES = 3;
    public static final float STAFF_DAMAGE = 10f;
    public static final float STAFF_RANGE = 2f;
    public static final int STAFF_COOLDOWN = 16;
    public static final float STAFF_SPEED = 0.5f;
    public static final int STAFF_DURATION = 20;

    public static final int SHIELD_USES = 3;
    public static final float SHIELD_PROTECTION = 0.5f;

    public static final float HEALTH_POTION_RESTORED_HEALTH = 5f;
    public static final int HEALTH_POTION_USES = 1;
    public static final int HEALTH_POTION_COOLDOWN = 8;

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
