package newgame.Components;

import com.badlogic.ashley.core.Component;

public class Experience implements Component
{
    public float experience;

    public Experience(float initialExperience)
    {
        experience = initialExperience;
    }
}
