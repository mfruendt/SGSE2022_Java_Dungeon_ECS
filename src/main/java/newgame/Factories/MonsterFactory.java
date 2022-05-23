package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.*;
import newgame.Components.Tags.HostileKi;
import newgame.Components.Tags.BossKi;
import newgame.Components.Tags.PassiveKi;
import newgame.animations.CharacterAnimations;

public class MonsterFactory
{
    public static float HARD_MONSTER_SPEED = 0.1f;
    public static float HARD_MONSTER_HEALTH = 0.5f;
    public static float HARD_MONSTER_DAMAGE = 1f;
    public static float HARD_MONSTER_PROTECTION = 0.5f;
    public static float HARD_MONSTER_RANGE = 1f;
    public static float HARD_MONSTER_EXPERIENCE = 0.5f;
    public static float HARD_MONSTER_KNOCKBACK_SPEED = 0.5f;
    public static int HARD_MONSTER_KNOCKBACK_DURATION = 8;
    public static int HARD_MONSTER_ATTACK_COOLDOWN = 8;

    public static float EASY_MONSTER_SPEED = 0.1f;
    public static float EASY_MONSTER_HEALTH = 10f;
    public static float EASY_MONSTER_DAMAGE = 1f;
    public static float EASY_MONSTER_PROTECTION = 0.3f;
    public static float EASY_MONSTER_RANGE = 1f;
    public static float EASY_MONSTER_EXPERIENCE = 0.1f;
    public static float EASY_MONSTER_KNOCKBACK_SPEED = 0.2f;
    public static int EASY_MONSTER_KNOCKBACK_DURATION = 4;
    public static int EASY_MONSTER_ATTACK_COOLDOWN = 16;

    public static float BOSS_MONSTER_SPEED = 0.1f;
    public static float BOSS_MONSTER_HEALTH = 15f;
    public static float BOSS_MONSTER_DAMAGE = 1f;
    public static float BOSS_MONSTER_PROTECTION = 1f;
    public static float BOSS_MONSTER_RANGE = 1f;
    public static int BOSS_MONSTER_RANGED_DURATION = 64;
    public static float BOSS_MONSTER_RANGED_RANGE = 4f;
    public static float BOSS_MONSTER_RANGED_SPEED = 0.5f;
    public static float BOSS_MONSTER_EXPERIENCE = 2f;
    public static float BOSS_MONSTER_KNOCKBACK_SPEED = 0.5f;
    public static int BOSS_MONSTER_KNOCKBACK_DURATION = 8;
    public static int BOSS_MONSTER_ATTACK_COOLDOWN = 6;

    public static Entity createEasyMonster(DungeonWorld level, Position target)
    {
        Entity easyMonster = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        easyMonster.add(new Position(spawnPosition.x, spawnPosition.y, level));
        easyMonster.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_RUN_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_RUN_R),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_IDLE_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.ZOMBIE_IDLE_R)));
        easyMonster.add(new PassiveKi(EASY_MONSTER_SPEED, target));
        easyMonster.add(new MeleeCombatStats(EASY_MONSTER_DAMAGE, EASY_MONSTER_PROTECTION, EASY_MONSTER_RANGE, EASY_MONSTER_KNOCKBACK_SPEED, EASY_MONSTER_KNOCKBACK_DURATION, EASY_MONSTER_ATTACK_COOLDOWN));
        easyMonster.add(new Velocity());
        easyMonster.add(new Health(EASY_MONSTER_HEALTH));
        easyMonster.add(new Experience(EASY_MONSTER_EXPERIENCE));

        return easyMonster;
    }

    public static Entity createHardMonster(DungeonWorld level, Position target)
    {
        Entity hardMonster = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        hardMonster.add(new Position(spawnPosition.x, spawnPosition.y, level));
        hardMonster.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_RUN_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_RUN_R),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_IDLE_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.SKELETON_IDLE_R)));
        hardMonster.add(new HostileKi(HARD_MONSTER_SPEED, target));
        hardMonster.add(new MeleeCombatStats(HARD_MONSTER_DAMAGE, HARD_MONSTER_PROTECTION, HARD_MONSTER_RANGE, HARD_MONSTER_KNOCKBACK_SPEED, HARD_MONSTER_KNOCKBACK_DURATION, HARD_MONSTER_ATTACK_COOLDOWN));
        hardMonster.add(new Velocity());
        hardMonster.add(new Health(HARD_MONSTER_HEALTH));
        hardMonster.add(new Experience(HARD_MONSTER_EXPERIENCE));

        return hardMonster;
    }

    public static Entity createBossMonster(DungeonWorld level, Position target)
    {
        Entity bossMonster = new Entity();

        Point spawnPosition = new Point(level.getRandomPointInDungeon());
        bossMonster.add(new Position(spawnPosition.x, spawnPosition.y, level));
        bossMonster.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_RUN_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_RUN_R),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_IDLE_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_IDLE_R)));
        bossMonster.add(new HostileKi(BOSS_MONSTER_SPEED, target));
        bossMonster.add(new MeleeCombatStats(BOSS_MONSTER_DAMAGE, BOSS_MONSTER_PROTECTION, BOSS_MONSTER_RANGE, BOSS_MONSTER_KNOCKBACK_SPEED, BOSS_MONSTER_KNOCKBACK_DURATION, BOSS_MONSTER_ATTACK_COOLDOWN));
        bossMonster.add(new RangedCombatStats(BOSS_MONSTER_DAMAGE, BOSS_MONSTER_PROTECTION, BOSS_MONSTER_RANGED_RANGE, BOSS_MONSTER_RANGED_DURATION, BOSS_MONSTER_RANGED_SPEED, BOSS_MONSTER_ATTACK_COOLDOWN));
        bossMonster.add(new Velocity());
        bossMonster.add(new BossKi());
        bossMonster.add(new Health(BOSS_MONSTER_HEALTH));
        bossMonster.add(new Experience(BOSS_MONSTER_EXPERIENCE));

        return bossMonster;
    }
}
