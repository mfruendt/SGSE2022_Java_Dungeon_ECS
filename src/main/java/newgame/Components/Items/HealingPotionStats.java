package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

public class HealingPotionStats implements Component
{
    public float healthRestored;
    public int usesLeft;
    public int cooldown;
    public int framesSinceLastUse = 0;

    public HealingPotionStats(float healthRestored, int usesLeft, int cooldown)
    {
        this.healthRestored = healthRestored;
        this.usesLeft = usesLeft;
        this.cooldown = cooldown;
    }
}
