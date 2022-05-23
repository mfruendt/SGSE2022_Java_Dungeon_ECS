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

public class EntityMapper
{
    public static final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    public static final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    public static final ComponentMapper<Velocity> velocityMapper = ComponentMapper.getFor(Velocity.class);
    public static final ComponentMapper<Animation> animationMapper = ComponentMapper.getFor(Animation.class);
    public static final ComponentMapper<newgame.Components.Sprite> spriteMapper = ComponentMapper.getFor(newgame.Components.Sprite.class);
    public static final ComponentMapper<MeleeAttack> meleeAttackMapper = ComponentMapper.getFor(MeleeAttack.class);
    public static final ComponentMapper<RangedAttack> rangedAttackMapper = ComponentMapper.getFor(RangedAttack.class);
    public static final ComponentMapper<HostileKi> hostileKiMapper = ComponentMapper.getFor(HostileKi.class);
    public static final ComponentMapper<PassiveKi> passiveKiMapper = ComponentMapper.getFor(PassiveKi.class);
    public static final ComponentMapper<Player> playerMapper = ComponentMapper.getFor(Player.class);
    public static final ComponentMapper<RangedWeaponStats> rangedWeaponMapper = ComponentMapper.getFor(RangedWeaponStats.class);
    public static final ComponentMapper<ShieldStats> shieldMapper = ComponentMapper.getFor(ShieldStats.class);
    public static final ComponentMapper<MeleeCombatStats> meleeCombatStatsMapper = ComponentMapper.getFor(MeleeCombatStats.class);
    public static final ComponentMapper<RangedCombatStats> rangedCombatStatsMapper = ComponentMapper.getFor(RangedCombatStats.class);
    public static final ComponentMapper<Pickup> pickupMapper = ComponentMapper.getFor(Pickup.class);
    public static final ComponentMapper<Knockback> knockbackMapper = ComponentMapper.getFor(Knockback.class);
    public static final ComponentMapper<Experience> experienceMapper = ComponentMapper.getFor(Experience.class);
    public static final ComponentMapper<PickupRequest> pickupRequestMapper = ComponentMapper.getFor(PickupRequest.class);
    public static final ComponentMapper<DropRequest> dropRequestMapper = ComponentMapper.getFor(DropRequest.class);
    public static final ComponentMapper<UseRequest> useRequestMapper = ComponentMapper.getFor(UseRequest.class);
    public static final ComponentMapper<ItemDestroyRequest> destroyRequestMapper = ComponentMapper.getFor(ItemDestroyRequest.class);
    public static final ComponentMapper<Inventory> inventoryMapper = ComponentMapper.getFor(Inventory.class);
    public static final ComponentMapper<MeleeWeaponStats> meleeWeaponStatsMapper = ComponentMapper.getFor(MeleeWeaponStats.class);
    public static final ComponentMapper<RangedWeaponStats> rangedWeaponStatsMapper = ComponentMapper.getFor(RangedWeaponStats.class);
    public static final ComponentMapper<HealingPotionStats> healthPotionStatsMapper = ComponentMapper.getFor(HealingPotionStats.class);
    public static final ComponentMapper<PassiveKi> easyKiMapper = ComponentMapper.getFor(PassiveKi.class);
    public static final ComponentMapper<PlayerControl> playerControlMapper = ComponentMapper.getFor(PlayerControl.class);
}
