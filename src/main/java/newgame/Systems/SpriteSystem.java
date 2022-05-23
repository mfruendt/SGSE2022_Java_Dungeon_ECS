package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Animation;
import newgame.Components.Position;
import newgame.Components.Velocity;
import newgame.EntityMapper;

public class SpriteSystem extends EntitySystem
{
    private ImmutableArray<Entity> animatableEntities;
    private ImmutableArray<Entity> drawableEntities;

    @Override
    public void addedToEngine(Engine engine)
    {
        animatableEntities = engine.getEntitiesFor(Family.all(Animation.class, Velocity.class, Position.class).get());
        drawableEntities = engine.getEntitiesFor(Family.all(newgame.Components.Sprite.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        GameSetup.batch.begin();

        for (int i = 0; i < animatableEntities.size(); i++)
        {
            drawAnimation(animatableEntities.get(i));
        }

        for (int i = 0; i < drawableEntities.size(); i++)
        {
            drawSprite(drawableEntities.get(i));
        }

        GameSetup.batch.end();
    }

    private void drawSprite(Entity entity)
    {
        Position position = EntityMapper.positionMapper.get(entity);
        newgame.Components.Sprite sprite = EntityMapper.spriteMapper.get(entity);

        drawSprite(sprite.sprite.getHeight() / sprite.sprite.getWidth(), sprite.sprite, new Point(position.x, position.y));
    }

    private void drawAnimation(Entity entity)
    {
        Velocity velocity = EntityMapper.velocityMapper.get(entity);
        Animation animation = EntityMapper.animationMapper.get(entity);
        Position position = EntityMapper.positionMapper.get(entity);
        Texture texture;

        if (velocity.x < 0f)
        {
            animation.activeAnimation = Animation.ActiveAnimation.RUN_LEFT;
            texture = animation.animationRunLeft.getNextAnimationTexture();
        }
        else if (velocity.x == 0f && velocity.y == 0f)
        {
            if (animation.activeAnimation == Animation.ActiveAnimation.RUN_LEFT || animation.activeAnimation == Animation.ActiveAnimation.IDLE_LEFT)
            {
                texture = animation.animationIdleLeft.getNextAnimationTexture();
                animation.activeAnimation = Animation.ActiveAnimation.IDLE_LEFT;
            }
            else
            {
                texture = animation.animationIdleRight.getNextAnimationTexture();
                animation.activeAnimation = Animation.ActiveAnimation.IDLE_RIGHT;
            }
        }
        else
        {
            animation.activeAnimation = Animation.ActiveAnimation.RUN_RIGHT;
            texture = animation.animationRunRight.getNextAnimationTexture();
        }

        drawSprite((float)texture.getHeight() / (float)texture.getWidth(), new com.badlogic.gdx.graphics.g2d.Sprite(texture), new Point(position.x, position.y));
    }

    private void drawSprite(float yScaling, Sprite sprite, Point position)
    {
        sprite.setSize(1.0f, yScaling);
        sprite.setPosition(position.x - 0.85f, position.y - 0.5f);
        sprite.draw(GameSetup.batch);
    }
}
