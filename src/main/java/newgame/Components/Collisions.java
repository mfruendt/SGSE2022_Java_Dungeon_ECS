package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

public class Collisions implements Component
{
    public List<Entity> collisions;

    public Collisions()
    {
        collisions = new ArrayList<>();
    }
}
