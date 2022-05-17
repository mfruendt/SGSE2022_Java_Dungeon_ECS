package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Position;
import newgame.Components.Sprite;
import newgame.Components.Tags.Pickup;
import newgame.textures.WeaponTextures;

public class ItemFactory
{
    public static Entity createSwordItem(DungeonWorld level)
    {
        Entity sword = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        sword.add(new Position(spawnPosition.x, spawnPosition.y, level));
        sword.add(new Sprite(WeaponTextures.REGULAR_SWORD.getTexture()));
        sword.add(new Pickup("Sword"));

        return sword;
    }
}
