package newgame.characters;

import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import newgame.DamageDetails;
import newgame.DamageDetails.DamageDirections;
import newgame.characters.ai.IAttackStrategy;
import newgame.items.weapons.Projectile;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.textures.WeaponTextures;

import java.util.concurrent.ThreadLocalRandom;

/** Class that will be used to define attributes that only monsters will have
 *
 * @author Maxim Fründt
 */
public abstract class Monster extends Character
{
    /** List of directions in which the monster can walk
     *
     */
    protected enum WalkingDirections
    {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    /* Radius in which the player has to stand in to make contact with the monster */
    private static final float CONTACT_RADIUS = 0.5f;

    /* Amount of damage that will be dealt on contact with the monster */
    private static final float CONTACT_DAMAGE = 5.0f;

    /* Current attack strategy of the monster */
    protected IAttackStrategy attackStrategy;

    /* Hero that will be targeted by the monster */
    protected Hero hero;
    
    /* Projectile that will be shot by the ranged monster */
    protected Projectile projectile;
    
    /* Shooting Parameters */
    protected static final int COOLDOWN = 70;
    
    protected int cooldownTimer;
	
    protected boolean isCooldown = false;
    
    protected static final float PROJECTILE_SPEED = 0.08f;
    
    protected static final float PROJECTILE_DAMAGE = 5;
    
    protected static final float PROJECTILE_DAMAGE_FALLOFF = 0.01f;
    
    protected static final float PROJECTILE_ACCURACY_FALLOFF = 8;
    
    protected static final float PROJECTILE_RANGE = 0;
    
    protected static final boolean IS_PROJECTILE_UNLIMITED_RANGE = true;
    
    protected static final float PROJECTILE_SIZE = 0.4f;
    
    protected static final float PROJECTILE_INITIAL_OFFSET = 0.7f;
    
    /** The direction in which the monster walked last
     * (Is used for calculating the next walking direction)
     */
    protected WalkingDirections lastWalkedDirection;
    
    /**
     * Each Monster has to be initialized with an hero that will be focused by the hero
     * 
     * @param hero Hero that will be focused by the monster
     */
    public Monster(Hero hero)
    {
        if (hero == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            throw new IllegalArgumentException();
        }
        this.hero = hero;
    }

    /**
	 * Checks, if the monster can shoot and activates cooldown
	 * @return true, if weapon can shoot
	 */
    public boolean canShoot()
	{
		if(!isCooldown)
		{
			isCooldown = true;
			cooldownTimer = COOLDOWN;
			setProjectile();
			return true;
		}
		return false;
	}
    
    /**
     * Removes the projectile from the monster.
     */
    public void removeProjectile()
    {
    	projectile = null;
    }
    
    /**
     * Create a new projectile in the monster
     */
    public void setProjectile()
    {
    	double angle = Math.toDegrees(Math.atan2(hero.getPosition().y - currentPosition.y, hero.getPosition().x - currentPosition.x));
    	projectile = new Projectile(
				currentPosition.x, 
				currentPosition.y, 
				angle,
                PROJECTILE_SPEED,
                PROJECTILE_DAMAGE,
                PROJECTILE_DAMAGE_FALLOFF,
                PROJECTILE_ACCURACY_FALLOFF,
                PROJECTILE_RANGE,
                IS_PROJECTILE_UNLIMITED_RANGE,
                PROJECTILE_SIZE,
                PROJECTILE_INITIAL_OFFSET,
				WeaponTextures.MONSTER_BALL.getTexture(),
				currentDungeonWorld);
    }
    
    public Projectile getProjectile()
    {
    	return projectile;
    }
    
    /**
	 * Updates the monster ranged attack cooldown timers
	 */
    public void updateCooldownTimer()
	{
		if(isCooldown)
		{
			cooldownTimer--;
			if(cooldownTimer <= 0)
			{
				isCooldown = false;
				cooldownTimer = COOLDOWN;
			}
		}
	}
    
    /** Function to receive damage. For the monster this will damage the monster
     *
     * @param damageDetails details about received damage
     */
    @Override
    public boolean receiveDamage(final DamageDetails damageDetails)
    {
        if (damageDetails == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return false;
        }
        health -= damageDetails.getDamageDealt();
        GameEventsLogger.getLogger().fine(LogMessages.HERO_HARMED_MONSTER.toString());

        if (health <= 0)
        {
            onDeath();
        }
        return true;
    }

    /** Get the experience points awarded for killing the monster
     *
     * @return Experience points in percent (1.0 = 100% level progress = new level)
     */
    public abstract float getExperiencePoints();

    /** Get the contact radius of the monster
     *
     * @return Contact radius
     */
    public float getContactRadius()
    {
        return CONTACT_RADIUS;
    }

    /** Handle attacks of the monster
     *
     * @param hero Hero that should be attacked
     * @return True if the player has been attacked, else false
     */
    public boolean handleAttacks(Hero hero)
    {
        if (attackStrategy == null)
        {
            return false;
        }

        return attackStrategy.attack(hero, this);
    }

    /** Check if the player has contact with a monster
     *
     * @param playerPosition Position of the player
     * @return Details about the dealt damage
     */
    public DamageDetails checkPlayerContact(Point playerPosition, float contactRadius)
    {
        DamageDirections direction = checkCharacterContact(contactRadius, playerPosition, DamageDirections.ANY);

        // If the player made contact with the monster to deal damage to him
        if (direction != DamageDirections.NONE)
        {
            return new DamageDetails(direction, CONTACT_DAMAGE);
        }
        else
        {
            return new DamageDetails();
        }
    }

    /** Calculate new monster position based on a target
     *
     * @param position Position that should be targeted
     */
    public void calcMovementToPosition(Point position)
    {
        boolean canMoveInX = false;
        boolean canMoveInY = false;
        float newX;
        float newY;

        // Check if we could move in direction x
        if (currentPosition.x < position.x && currentDungeonWorld.isTileAccessible(new Point(currentPosition.x + MOVEMENT_SPEED, currentPosition.y))
        || currentPosition.x > position.x && currentDungeonWorld.isTileAccessible(new Point(currentPosition.x - MOVEMENT_SPEED, currentPosition.y)))
        {
            canMoveInX = true;
        }

        // Check if we could move in direction x
        if (currentPosition.y < position.y && currentDungeonWorld.isTileAccessible(new Point(currentPosition.x, currentPosition.y + MOVEMENT_SPEED))
                || currentPosition.y > position.y && currentDungeonWorld.isTileAccessible(new Point(currentPosition.x, currentPosition.y - MOVEMENT_SPEED)))
        {
            canMoveInY = true;
        }

        if (canMoveInX && canMoveInY)
        {
            if (currentPosition.x < position.x)
            {
                activeAnimation = AnimationStates.RUNNING_RIGHT;

                newX = currentPosition.x + MOVEMENT_SPEED / 1.414f;
            }
            else
            {
                activeAnimation = AnimationStates.RUNNING_LEFT;

                newX = currentPosition.x - MOVEMENT_SPEED / 1.414f;
            }

            if (currentPosition.y < position.y)
            {
                newY = currentPosition.y + MOVEMENT_SPEED / 1.414f;
            }
            else
            {
                newY = currentPosition.y - MOVEMENT_SPEED / 1.414f;
            }

            currentPosition = new Point(newX, newY);
        }
        else if (canMoveInX)
        {
            if (currentPosition.x < position.x)
            {
                activeAnimation = AnimationStates.RUNNING_RIGHT;

                newX = currentPosition.x + MOVEMENT_SPEED;
            }
            else
            {
                activeAnimation = AnimationStates.RUNNING_LEFT;

                newX = currentPosition.x - MOVEMENT_SPEED;
            }

            currentPosition = new Point(newX, currentPosition.y);
        }
        else if (canMoveInY)
        {
            if (currentPosition.y < position.y)
            {
                newY = currentPosition.y + MOVEMENT_SPEED;
            }
            else
            {
                newY = currentPosition.y - MOVEMENT_SPEED;
            }

            if (activeAnimation == AnimationStates.IDLE_LEFT || activeAnimation == AnimationStates.RUNNING_LEFT)
            {
                activeAnimation = AnimationStates.RUNNING_LEFT;
            }
            else
            {
                activeAnimation = AnimationStates.RUNNING_RIGHT;
            }

            currentPosition = new Point(currentPosition.x, newY);
        }
    }

    /** Calculate new random movement of the monster
     *
     */
    public void calcNewRandomMovement()
    {
        boolean hasMoved = false;

        // First we generate a random number
        int randomNum = ThreadLocalRandom.current().nextInt(0, 100);

        // If the number is between 40 and 100 (=60% probability) the monster will walk in the same direction as before
        if (randomNum >= 40)
        {
            if (move(lastWalkedDirection))
            {
                hasMoved = true;
            }
        }

        // If the number is between 30 and 39 (=10% probability) or the monster was unable to move, walk to the
        // left (from the perspective of the monster)
        if ((randomNum >= 30 && randomNum < 39) || !hasMoved)
        {
            WalkingDirections nextDirection = lastWalkedDirection;
            switch (lastWalkedDirection)
            {
                case UP:
                    nextDirection = WalkingDirections.LEFT;
                    break;
                case LEFT:
                    nextDirection = WalkingDirections.DOWN;
                    break;
                case DOWN:
                    nextDirection = WalkingDirections.RIGHT;
                    break;
                case RIGHT:
                    nextDirection = WalkingDirections.UP;
                    break;
            };

            if (move(nextDirection))
            {
                hasMoved = true;
                lastWalkedDirection = nextDirection;
            }
        }

        // If the number is between 20 and 29 (=10% probability) or the monster was unable to move, walk to the
        // right (from the perspective of the monster)
        if ((randomNum >= 20 && randomNum < 30) || !hasMoved)
        {
            WalkingDirections nextDirection = lastWalkedDirection;
            switch (lastWalkedDirection)
            {
                case UP:
                    nextDirection = WalkingDirections.RIGHT;
                    break;
                case RIGHT:
                    nextDirection = WalkingDirections.DOWN;
                    break;
                case DOWN:
                    nextDirection = WalkingDirections.LEFT;
                    break;
                case LEFT:
                    nextDirection = WalkingDirections.UP;
                    break;
            };

            if (move(nextDirection))
            {
                hasMoved = true;
                lastWalkedDirection = nextDirection;
            }
        }

        // If the number is between 15 and 19 (=5% probability) or the monster was unable to move, walk to the
        // opposite direction
        if ((randomNum >= 15 && randomNum < 20) || !hasMoved)
        {
            WalkingDirections nextDirection = lastWalkedDirection;
            switch (lastWalkedDirection)
            {
                case UP:
                    nextDirection = WalkingDirections.DOWN;
                    break;
                case RIGHT:
                    nextDirection = WalkingDirections.LEFT;
                    break;
                case DOWN:
                    nextDirection = WalkingDirections.UP;
                    break;
                case LEFT:
                    nextDirection = WalkingDirections.RIGHT;
                    break;
            };

            if (move(nextDirection))
            {
                hasMoved = true;
                lastWalkedDirection = nextDirection;
            }
        }

        // If the number is between 0 and 14 (=15% probability) or the monster was unable to move, then idle
        if ((randomNum >= 0 && randomNum < 14) || !hasMoved)
        {
            // if the last animation was oriented to the right, idle to the right, else to the left
            if (activeAnimation == AnimationStates.RUNNING_RIGHT || activeAnimation == AnimationStates.IDLE_RIGHT)
            {
                activeAnimation = AnimationStates.IDLE_RIGHT;
            }
            else
            {
                activeAnimation = AnimationStates.IDLE_LEFT;
            }
        }
    }

    /** Check if the desired direction is reachable and move the monster if so
     *
     * @param direction (Absolute) Direction that should be moved to
     * @return True if moving was successful, else false
     */
    protected boolean move(WalkingDirections direction)
    {
        Point newPosition = new Point(currentPosition);
        AnimationStates newAnimation = activeAnimation;

        // Get the new position
        switch (direction)
        {
            case LEFT:
            {
                newPosition.x -= MOVEMENT_SPEED;
                newAnimation = AnimationStates.RUNNING_LEFT;
                break;
            }
            case RIGHT:
            {
                newPosition.x += MOVEMENT_SPEED;
                newAnimation = AnimationStates.RUNNING_RIGHT;
                break;
            }
            case DOWN:
            {
                newPosition.y -= MOVEMENT_SPEED;
                // If the last animation was oriented to the right, set running to the right, else to the left
                if (activeAnimation == AnimationStates.RUNNING_RIGHT || activeAnimation == AnimationStates.IDLE_RIGHT)
                {
                    newAnimation = AnimationStates.RUNNING_RIGHT;
                }
                else
                {
                    newAnimation = AnimationStates.RUNNING_LEFT;
                }
                break;
            }
            case UP:
            {
                newPosition.y += MOVEMENT_SPEED;
                // If the last animation was oriented to the right, set running to the right, else to the left
                if (activeAnimation == AnimationStates.RUNNING_RIGHT || activeAnimation == AnimationStates.IDLE_RIGHT)
                {
                    newAnimation = AnimationStates.RUNNING_RIGHT;
                }
                else
                {
                    newAnimation = AnimationStates.RUNNING_LEFT;
                }
                break;
            }
        }

        if (currentDungeonWorld.isTileAccessible(newPosition))
        {
            currentPosition = newPosition;
            activeAnimation = newAnimation;
        }
        else
        {
            return false;
        }

        return true;
    }

    /** Function that will be called when the monster dies
     *
     */
    @Override
    public void onDeath()
    {
        isDead = true;
        GameEventsLogger.getLogger().info(LogMessages.MONSTER_KILLED.toString());
    }
}
