package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;

public class PlayerControl implements Component
{
    public static int forwardKey = Input.Keys.W;
    public static int backwardKey = Input.Keys.S;
    public static int leftKey = Input.Keys.A;
    public static int rightKey = Input.Keys.D;

    public float speed;

    public PlayerControl(float speed)
    {
        this.speed = speed;
    }
}
