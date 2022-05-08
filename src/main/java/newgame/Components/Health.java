package newgame.Components;

import com.badlogic.ashley.core.Component;

public class Health implements Component
{
    public float currentHealth;
    public float maxHealth;

    public Health(float maxHealth)
    {
        currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }
}
