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

public class AnimationSystem extends EntitySystem
{
    private ImmutableArray<Entity> animatableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<Velocity> velocityMapper = ComponentMapper.getFor(Velocity.class);
    private final ComponentMapper<Animation> animationMapper = ComponentMapper.getFor(Animation.class);

    @Override
    public void addedToEngine(Engine engine)
    {
        animatableEntities = engine.getEntitiesFor(Family.all(Animation.class, Velocity.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < animatableEntities.size(); i++)
        {
            drawAnimation(animatableEntities.get(i));
        }
    }

    private void drawAnimation(Entity entity)
    {
        Velocity velocity = velocityMapper.get(entity);
        Animation animation = animationMapper.get(entity);
        Position position = positionMapper.get(entity);
        Texture texture;

        if (velocity.x < 0f)
        {
            texture = animation.animationLeft.getNextAnimationTexture();
        }
        else if (velocity.x == 0f && velocity.y == 0f)
        {
            texture = animation.animationIdle.getNextAnimationTexture();
        }
        else
        {
            texture = animation.animationRight.getNextAnimationTexture();
        }

        drawSprite((float)texture.getHeight() / (float)texture.getWidth(), new com.badlogic.gdx.graphics.g2d.Sprite(texture), new Point(position.x, position.y));
    }

    private void drawSprite(float yScaling, Sprite sprite, Point position)
    {
        sprite.setSize(1.0f, yScaling);
        sprite.setPosition(position.x - 0.85f, position.y - 0.5f);
        GameSetup.batch.begin();
        sprite.draw(GameSetup.batch);
        GameSetup.batch.end();
    }
}
