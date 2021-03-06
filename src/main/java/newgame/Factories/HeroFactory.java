package newgame.Factories;

import com.badlogic.ashley.core.Entity;
import newgame.Components.*;
import newgame.Components.Tags.Player;
import newgame.animations.CharacterAnimations;

/** Factory to create entities that represent heros
 * @author Maxim Fründt
 */
public class HeroFactory
{
    /** Health of the hero */
    public static final float HERO_HEALTH = 1000f;
    /** Speed of the hero */
    public static final float HERO_SPEED = 0.2f;
    /** Size of the inventory of the hero */
    public static final int HERO_INVENTORY_SIZE = 10;

    /** Create new entity that represents a hero
     *
     * @param spawnPosition Spawn position of the hero
     * @return Entity that represents a hero
     */
    public static Entity createHero(Position spawnPosition)
    {
        Entity hero = new Entity();

        hero.add(spawnPosition);
        hero.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_RUN_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_RUN_R),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_IDLE_L),
                CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_IDLE_R)));
        hero.add(new Velocity());
        hero.add(new PlayerControl(HERO_SPEED));
        hero.add(new Player());
        hero.add(new Inventory(HERO_INVENTORY_SIZE));
        hero.add(new Health(HERO_HEALTH));
        hero.add(new MeleeCombatStats());
        hero.add(new RangedCombatStats());
        hero.add((new Experience(0f)));

        return hero;
    }
}
