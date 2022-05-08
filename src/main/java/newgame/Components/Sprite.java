package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class Sprite implements Component
{
    public com.badlogic.gdx.graphics.g2d.Sprite sprite;

    public Sprite()
    {

    }

    public Sprite(com.badlogic.gdx.graphics.g2d.Sprite sprite)
    {
        this.sprite = sprite;
    }

    public Sprite(Texture texture)
    {
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture);
    }
}
