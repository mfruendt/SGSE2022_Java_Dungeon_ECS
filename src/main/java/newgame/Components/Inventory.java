package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements Component
{
    public List<Entity> items;
    public int usedSize;
    public int size;

    public Inventory(int size)
    {
        items = new ArrayList<>(size);
        this.size = size;
        for (int i = 0; i < size; i++)
        {
            this.items.add(null);
        }
    }
}
