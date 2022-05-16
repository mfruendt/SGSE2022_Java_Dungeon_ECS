package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class Health implements Component
{
    public float currentHealth;
    public float maxHealth;
    public Entity lastAttacker;

    public Health(float maxHealth)
    {
        currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }
}
