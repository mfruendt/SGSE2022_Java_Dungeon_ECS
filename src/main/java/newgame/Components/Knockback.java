package newgame.Components;

import com.badlogic.ashley.core.Component;

public class Knockback implements Component
{
    public int knockbackFrames;
    public float speed;
    public Direction direction;

    public Knockback(int knockbackFrames, float speed, Direction direction)
    {
        this.knockbackFrames = knockbackFrames;
        this.speed = speed;
        this.direction = direction;
    }

    public enum Direction
    {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
