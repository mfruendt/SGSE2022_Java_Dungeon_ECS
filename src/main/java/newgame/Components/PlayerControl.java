package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;

public class PlayerControl implements Component
{
    public static int walkForwardKey = Input.Keys.W;
    public static int walkBackwardKey = Input.Keys.S;
    public static int walkLeftKey = Input.Keys.A;
    public static int walkRightKey = Input.Keys.D;

    public static int attackForwardKey = Input.Keys.UP;
    public static int attackBackwardKey = Input.Keys.DOWN;
    public static int attackRightKey = Input.Keys.RIGHT;
    public static int attackLeftKey = Input.Keys.LEFT;

    public static int pickupKey = Input.Keys.F;
    public static int dropKey = Input.Keys.R;
    public static int useKey = Input.Keys.U;

    public float speed;

    public PlayerControl(float speed)
    {
        this.speed = speed;
    }
}
