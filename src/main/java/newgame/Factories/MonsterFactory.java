package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.*;
import newgame.animations.CharacterAnimations;

public class MonsterFactory
{
    public static Entity createEasyMonster(DungeonWorld level)
    {
        Entity easyMonster = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        easyMonster.add(new Position(spawnPosition.x, spawnPosition.y, level));
        easyMonster.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_RUN_L), CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_RUN_R), CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_IDLE_L)));
        easyMonster.add(new EasyMonsterKi(0.1f));
        easyMonster.add(new Velocity());
        easyMonster.add(new Health(0.5f));
        easyMonster.add(new Collisions());

        return easyMonster;
    }

    public static Entity createHardMonster(DungeonWorld level, Position target)
    {
        Entity hardMonster = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        hardMonster.add(new Position(spawnPosition.x, spawnPosition.y, level));
        hardMonster.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_RUN_L), CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_RUN_R), CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_IDLE_L)));
        hardMonster.add(new HardMonsterKi(0.1f, 1f, target));
        hardMonster.add(new Velocity());
        hardMonster.add(new Health(0.5f));
        hardMonster.add(new Collisions());

        return hardMonster;
    }
}
