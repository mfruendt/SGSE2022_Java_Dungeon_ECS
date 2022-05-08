package newgame.Components;

import com.badlogic.ashley.core.Component;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;

public class Position implements Component
{
    public float x;
    public float y;
    public DungeonWorld level;

    public Position()
    {

    }

    public Position(float x, float y, DungeonWorld level)
    {
        this.x = x;
        this.y = y;
        this.level = level;
    }
}
