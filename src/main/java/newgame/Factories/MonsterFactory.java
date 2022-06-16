package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.*;
import newgame.Components.Tags.HostileKi;
import newgame.Components.Tags.BossKi;
import newgame.Components.Tags.PassiveKi;
import newgame.animations.CharacterAnimations;

/** Factory to create entities that represent monsters
 * @author Maxim Fründt
 */
public class MonsterFactory
{
    /** Speed of the hard monster */
    public static float HARD_MONSTER_SPEED = 0.1f;
    /** Health of the hard monster */
    public static float HARD_MONSTER_HEALTH = 0.5f;
    /** Damage of the hard monster */
    public static float HARD_MONSTER_DAMAGE = 1f;
    /** Protection of the hard monster */
    public static float HARD_MONSTER_PROTECTION = 0.5f;
    /** Range of the hard monster */
    public static float HARD_MONSTER_RANGE = 1f;
    /** Experience of the hard monster */
    public static float HARD_MONSTER_EXPERIENCE = 0.5f;
    /** Knockback speed of the hard monster */
    public static float HARD_MONSTER_KNOCKBACK_SPEED = 0.5f;
    /** Knockback duration of the hard monster */
    public static int HARD_MONSTER_KNOCKBACK_DURATION = 8;
    /** Attack cooldown of the hard monster */
    public static int HARD_MONSTER_ATTACK_COOLDOWN = 8;

    /** Speed of the easy monster */
    public static float EASY_MONSTER_SPEED = 0.1f;
    /** Health of the easy monster */
    public static float EASY_MONSTER_HEALTH = 10f;
    /** Damage of the easy monster */
    public static float EASY_MONSTER_DAMAGE = 1f;
    /** Protection of the easy monster */
    public static float EASY_MONSTER_PROTECTION = 0.3f;
    /** Range of the easy monster */
    public static float EASY_MONSTER_RANGE = 1f;
    /** Experience of the easy monster */
    public static float EASY_MONSTER_EXPERIENCE = 0.1f;
    /** Knockback speed of the easy monster */
    public static float EASY_MONSTER_KNOCKBACK_SPEED = 0.2f;
    /** Knockback duration of the easy monster */
    public static int EASY_MONSTER_KNOCKBACK_DURATION = 4;
    /** Attack cooldown of the easy monster */
    public static int EASY_MONSTER_ATTACK_COOLDOWN = 16;

    /** Speed of the boss monster */
    public static float BOSS_MONSTER_SPEED = 0.1f;
    /** Health of the boss monster */
    public static float BOSS_MONSTER_HEALTH = 15f;
    /** Damage of the boss monster */
    public static float BOSS_MONSTER_DAMAGE = 1f;
    /** Protection of the boss monster */
    public static float BOSS_MONSTER_PROTECTION = 1f;
    /** Range of the boss monster */
    public static float BOSS_MONSTER_RANGE = 1f;
    /** Duration of ranged attacks of the boss monster */
    public static int BOSS_MONSTER_RANGED_DURATION = 64;
    /** Range of ranged attacks of the boss monster */
    public static float BOSS_MONSTER_RANGED_RANGE = 4f;
    /** Speed of ranged attacks of the boss monster */
    public static float BOSS_MONSTER_RANGED_SPEED = 0.5f;
    /** Experience of the boss monster */
    public static float BOSS_MONSTER_EXPERIENCE = 2f;
    /** Knockback speed of the boss monster */
    public static float BOSS_MONSTER_KNOCKBACK_SPEED = 0.5f;
    /** Knockback duration of the boss monster */
    public static int BOSS_MONSTER_KNOCKBACK_DURATION = 8;
    /** Attack cooldown of the boss monster */
    public static int BOSS_MONSTER_ATTACK_COOLDOWN = 6;

    /** Create new entity that represents an easy monster
     *
     * @param level Level of the monster
     * @param target Target of the monster
     * @return Entity that represents an easy monster
     */
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
        easyMonster.add(new RangedCombatStats());
        easyMonster.add(new Velocity());
        easyMonster.add(new Health(EASY_MONSTER_HEALTH));
        easyMonster.add(new Experience(EASY_MONSTER_EXPERIENCE));

        return easyMonster;
    }

    /** Create new entity that represents a hard monster
     *
     * @param level Level of the monster
     * @param target Target of the monster
     * @return Entity that represents aa hard monster
     */
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
        hardMonster.add(new RangedCombatStats());
        hardMonster.add(new Velocity());
        hardMonster.add(new Health(HARD_MONSTER_HEALTH));
        hardMonster.add(new Experience(HARD_MONSTER_EXPERIENCE));

        return hardMonster;
    }

    /** Create new entity that represents a boss monster
     *
     * @param level Level of the monster
     * @param target Target of the monster
     * @return Entity that represents aa boss monster
     */
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
