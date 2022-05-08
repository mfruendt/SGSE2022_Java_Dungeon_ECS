package newgame.traps;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.animations.TrapAnimations;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/**
 * Class for traps
 * @author Dominik Haacke
 *
 */
public class Trap implements IAnimatable, IEntity
{
	/** List of possible animations of the Trap
    *
    */
	private enum AnimationStates
    {
        IDLE(0),
        ACTIVATING(1),
        ACTIVE(2),
        DEACTIVATING(3),
        HOLE(4);

        /** Enum element index
         *
         */
        final int index;

        /** Constructor of the enum elements
         *
         * @param index Index of the enum
         */
        AnimationStates(final int index)
        {
            this.index = index;
        }

        /** Get the index of an enum element
         *
         * @return Index of the element
         */
        public int getIndex()
        {
            return index;
        }
    }
	
	
	private Point currentPosition;
	private DungeonWorld currentDungeonWorld;
	private final List<Animation> animations;
	private AnimationStates activeAnimation;
	private final float size;
	private final ActivationType type;
	private final boolean cloaked;
	private int activations;
	private final boolean unlimited;
	private boolean destroyed;
	private final int INITIAL_DELAY;
	private final int ACTIVE_TIME;
	private int currentDelayTimer, activeTimer, cooldownTimer;
	private boolean isActivated, isActive, isCooldown, decremented, heroHasPotion;
	private final float damage;
	
	/**
	 * Create a new Trap.
	 *
	 * @param size Predefined size of trap.
	 * @param type Predefined activation type of trap.
	 * @param cloaked Init status, if trap is cloaked.
	 * @param activations Predefined number of activations.
	 * @param unlimited Init status, if trap is unlimited.
	 * @param initialDelay Predefined initial delay time.
	 * @param activeTime Predefined active time.
	 * @param coolDownTime Predefined cool down time.
	 * @param x Predefined x position.
	 * @param y Predefined y position.
	 * @param damage Predefined damage, which trap will be cause.
	 */
	public Trap(
			final float size,
			final ActivationType type,
			final boolean cloaked,
			final int activations,
			final boolean unlimited,
			final int initialDelay,
			final int activeTime,
			final int coolDownTime,
			final float x,
			final float y,
			final float damage)
	{
		this.size = size;
		this.type = type;
		this.cloaked = cloaked;
		this.activations = activations;
		this.unlimited = unlimited;
		this.damage = damage;
		INITIAL_DELAY = initialDelay;
		currentDelayTimer = INITIAL_DELAY;
		ACTIVE_TIME = activeTime;
		activeTimer = ACTIVE_TIME;
		cooldownTimer = coolDownTime;
		currentPosition = new Point(x, y);
		
		animations = new ArrayList<>();

        animations.add(AnimationStates.IDLE.getIndex(), TrapAnimations.getAnimation(TrapAnimations.Animations.DAMAGE_TRAP_IDLE));
        animations.add(AnimationStates.ACTIVATING.getIndex(), TrapAnimations.getAnimation(TrapAnimations.Animations.DAMAGE_TRAP_ACTIVATING));
        animations.add(AnimationStates.ACTIVE.getIndex(), TrapAnimations.getAnimation(TrapAnimations.Animations.DAMAGE_TRAP_ACTIVE));
        animations.add(AnimationStates.DEACTIVATING.getIndex(), TrapAnimations.getAnimation(TrapAnimations.Animations.DAMAGE_TRAP_DEACTIVATING));
        animations.add(AnimationStates.HOLE.getIndex(), TrapAnimations.getAnimation(TrapAnimations.Animations.HOLE_TRAP));
        
        if(type == ActivationType.DAMAGE)
        {
        	activeAnimation = AnimationStates.IDLE;
        }
        else
        {
        	activeAnimation = AnimationStates.HOLE;
        }
	}
	
	/** Change the level in which the trap currently is.
     * 
     * The start position in the new level will be randomized.
     *
     * @param level New level
     */
  	public void setLevel(DungeonWorld level)
   	{
        this.currentDungeonWorld = level;

  		if(currentPosition.x < 0 || currentPosition.y < 0 || !level.isTileAccessible(currentPosition))
  		{
  			findRandomPosition();
  		}
    }
	
	/** Set the trap position to a new random location inside the level
    *
    */
	public void findRandomPosition()
	{
    	this.currentPosition = new Point(currentDungeonWorld.getRandomPointInDungeon());
	}

	/**
	 * Returns the current position of the trap
	 */
	@Override
	public Point getPosition() 
	{
		return currentPosition;
	}

	/**
	 * Returns, if the trap is destroyed
	 */
	@Override
	public boolean deleteable() 
	{
		return destroyed;
	}
	
	/**
	 * Activates the trap, if it has not been activate
	 */
	public void activate()
	{
		if(!isActivated && !isCooldown)
		{
			isActivated = true;
			currentDelayTimer = INITIAL_DELAY;
			GameEventsLogger.getLogger().info(type.toString() + LogMessages.TRAP_ACTIVATED);
		}
	}
	
	/**
	 * Deactivates the trap, if it was active
	 */
	public void deactivate()
	{
		if(isActive)
		{
			isActive = false;
			activeTimer = ACTIVE_TIME;
			isCooldown = true;
			decremented = false;
			if(type == ActivationType.DAMAGE)
	        {
	        	activeAnimation = AnimationStates.IDLE;
	        }
	        else
	        {
	        	activeAnimation = AnimationStates.HOLE;
	        }
		}
	}

	/**
	 * Updates the state of the trap
	 */
	@SuppressWarnings("ConstantConditions")
	@Override
	public void update() 
	{
		if(!isCloaked())
		{
			this.draw();
		}
		if(isActivated && currentDelayTimer > 0 && !isActive)
		{
			currentDelayTimer --;
			if(type == ActivationType.DAMAGE)
	        {
				if(activeAnimation != AnimationStates.ACTIVATING)
				{
		        	activeAnimation = AnimationStates.ACTIVATING;
				}
	        }
	        else
	        {
	        	if(activeAnimation != AnimationStates.HOLE)
				{
	        		activeAnimation = AnimationStates.HOLE;
				}
	        }
		}
		else if(!isActive && isActivated && currentDelayTimer <= 0)
		{
			isActive = true;
			if(type == ActivationType.DAMAGE)
	        {
				if(activeAnimation != AnimationStates.ACTIVE)
				{
					activeAnimation = AnimationStates.ACTIVE;
				}
	        }
	        else
	        {
	        	if(activeAnimation != AnimationStates.HOLE)
				{
	        		activeAnimation = AnimationStates.HOLE;
				}
	        }
			if(!unlimited && !decremented)
			{
				activations--;
				decremented = true;
				if(activations == 0)
				{
					destroyed = true;
				}
			}
		}
		
		if(isActive && !isCooldown)
		{
			activeTimer --;
			if(activeTimer <= 0)
			{
				isActive = false;
				activeTimer = ACTIVE_TIME;
				isCooldown = true;
				if(type == ActivationType.DAMAGE)
		        {
					if(activeAnimation != AnimationStates.DEACTIVATING)
					{
						activeAnimation = AnimationStates.DEACTIVATING;
					}
		        }
		        else
		        {
		        	if(activeAnimation != AnimationStates.HOLE)
					{
		        		activeAnimation = AnimationStates.HOLE;
					}
		        }
			}
		}
		
		if(isCooldown)
		{
			cooldownTimer--;
			if(cooldownTimer <= 0)
			{
				if(type == ActivationType.DAMAGE)
		        {
					if(activeAnimation != AnimationStates.IDLE)
					{
						activeAnimation = AnimationStates.IDLE;
					}
		        }
		        else
		        {
		        	if(activeAnimation != AnimationStates.HOLE)
					{
			        	activeAnimation = AnimationStates.HOLE;
					}
		        }
				cooldownTimer = INITIAL_DELAY;
				isActivated = false;
				isActive = false;
				isCooldown = false;
				decremented = false;
				currentDelayTimer = INITIAL_DELAY;
			}
		}
	}
	
	/** Get the current animation of the Trap
    *
    * @return The current animation of the Trap
    */
	@Override
	public Animation getActiveAnimation()
	{
		return animations.get(activeAnimation.getIndex());
	}

	/**
	 * Returns true, if the trap is cloaked and not activated
	 * @return cloaked
	 */
	public boolean isCloaked() 
	{
		if(isActive || heroHasPotion)
		{
			return false;
		}
		return cloaked;
	}
	
	/**
	 * Returns if the trap could trigger the effect
	 * @return active
	 */
	public boolean isActive()
	{
		if(isCooldown)
		{
			return false;
		}
		return isActive;
	}
	
	/**
	 * Returns the activation type of this trap
	 * @return activation type
	 */
	public ActivationType getType() 
	{
		return type;
	}
	
	/**
	 * Returns the contact radius for the trap
	 * @return size of the trap
	 */
	public float getSize() 
	{
		return size;
	}

	/**
	 * Returns the Damage
	 * @return damage
	 */
	public float getDamage() 
	{
		return damage;
	}
	
	/**
	 * Sets the hero potion flag, if the trap is cloaked it will become visible, if it is set to true.
	 *
	 * @param heroHasPotion To be set status, if hero has potion.
	 */
	public void setHeroHasPotion(boolean heroHasPotion) 
	{
		this.heroHasPotion = heroHasPotion;
	}
}