package newgame.Components;

import com.badlogic.ashley.core.Component;

public class BossKi implements Component
{
    public static int RANGED_ATTACK_COOLDOWN = 30;
    public int rangedAttackDuration;
    public float rangedAttackSpeed;
    public float rangedTargetRange;

    public BossKi(int rangedAttackDuration, float rangedTargetRange, float rangedAttackSpeed)
    {
        this.rangedAttackDuration = rangedAttackDuration;
        this.rangedTargetRange = rangedTargetRange;
        this.rangedAttackSpeed = rangedAttackSpeed;
    }
}
