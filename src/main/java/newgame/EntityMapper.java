package newgame;

import com.badlogic.ashley.core.ComponentMapper;
import newgame.Components.*;
import newgame.Components.Events.*;
import newgame.Components.Items.HealingPotionStats;
import newgame.Components.Items.MeleeWeaponStats;
import newgame.Components.Items.RangedWeaponStats;
import newgame.Components.Items.ShieldStats;
import newgame.Components.Tags.HostileKi;
import newgame.Components.Tags.PassiveKi;
import newgame.Components.Tags.Pickup;
import newgame.Components.Tags.Player;

/** Mappers to get different component types
 * @author Maxim Fründt
 */
public class EntityMapper
{
    /** Mapper for position components */
    public static final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    /** Mapper for health components */
    public static final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    /** Mapper for velocity components */
    public static final ComponentMapper<Velocity> velocityMapper = ComponentMapper.getFor(Velocity.class);
    /** Mapper for animation components */
    public static final ComponentMapper<Animation> animationMapper = ComponentMapper.getFor(Animation.class);
    /** Mapper for sprite components */
    public static final ComponentMapper<newgame.Components.Sprite> spriteMapper = ComponentMapper.getFor(newgame.Components.Sprite.class);
    /** Mapper for melee attack components */
    public static final ComponentMapper<MeleeAttack> meleeAttackMapper = ComponentMapper.getFor(MeleeAttack.class);
    /** Mapper for ranged attack components */
    public static final ComponentMapper<RangedAttack> rangedAttackMapper = ComponentMapper.getFor(RangedAttack.class);
    /** Mapper for hard monster KI components */
    public static final ComponentMapper<HostileKi> hostileKiMapper = ComponentMapper.getFor(HostileKi.class);
    /** Mapper for easy monster KI components */
    public static final ComponentMapper<PassiveKi> passiveKiMapper = ComponentMapper.getFor(PassiveKi.class);
    /** Mapper for player components */
    public static final ComponentMapper<Player> playerMapper = ComponentMapper.getFor(Player.class);
    /** Mapper for ranged weapon stats components */
    public static final ComponentMapper<RangedWeaponStats> rangedWeaponMapper = ComponentMapper.getFor(RangedWeaponStats.class);
    /** Mapper for shield stats components */
    public static final ComponentMapper<ShieldStats> shieldMapper = ComponentMapper.getFor(ShieldStats.class);
    /** Mapper for melee combat stats components */
    public static final ComponentMapper<MeleeCombatStats> meleeCombatStatsMapper = ComponentMapper.getFor(MeleeCombatStats.class);
    /** Mapper for ranged combat stats components */
    public static final ComponentMapper<RangedCombatStats> rangedCombatStatsMapper = ComponentMapper.getFor(RangedCombatStats.class);
    /** Mapper for pick up components */
    public static final ComponentMapper<Pickup> pickupMapper = ComponentMapper.getFor(Pickup.class);
    /** Mapper for knockback components */
    public static final ComponentMapper<Knockback> knockbackMapper = ComponentMapper.getFor(Knockback.class);
    /** Mapper for experience components */
    public static final ComponentMapper<Experience> experienceMapper = ComponentMapper.getFor(Experience.class);
    /** Mapper for pickup requests components */
    public static final ComponentMapper<PickupRequest> pickupRequestMapper = ComponentMapper.getFor(PickupRequest.class);
    /** Mapper for drop requests components */
    public static final ComponentMapper<DropRequest> dropRequestMapper = ComponentMapper.getFor(DropRequest.class);
    /** Mapper for use requests components */
    public static final ComponentMapper<UseRequest> useRequestMapper = ComponentMapper.getFor(UseRequest.class);
    /** Mapper for destroy requests components */
    public static final ComponentMapper<ItemDestroyRequest> destroyRequestMapper = ComponentMapper.getFor(ItemDestroyRequest.class);
    /** Mapper for inventory components */
    public static final ComponentMapper<Inventory> inventoryMapper = ComponentMapper.getFor(Inventory.class);
    /** Mapper for melee weapon stats components */
    public static final ComponentMapper<MeleeWeaponStats> meleeWeaponStatsMapper = ComponentMapper.getFor(MeleeWeaponStats.class);
    /** Mapper for ranged weapon stats components */
    public static final ComponentMapper<RangedWeaponStats> rangedWeaponStatsMapper = ComponentMapper.getFor(RangedWeaponStats.class);
    /** Mapper for health potion stats components */
    public static final ComponentMapper<HealingPotionStats> healthPotionStatsMapper = ComponentMapper.getFor(HealingPotionStats.class);
    /** Mapper for player control components */
    public static final ComponentMapper<PlayerControl> playerControlMapper = ComponentMapper.getFor(PlayerControl.class);
}
