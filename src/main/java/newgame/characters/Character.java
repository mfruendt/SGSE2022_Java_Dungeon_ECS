package newgame.characters;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import newgame.DamageDetails;
import newgame.DamageDetails.DamageDirections;

import java.util.List;

/** Class for game characters
 *
 * @author Maxim Fründt
 */
public abstract class Character implements IAnimatable, IEntity
{
    /** List of possible animations of a character
     *
     */
    protected enum AnimationStates
    {
        IDLE_LEFT(0),
        IDLE_RIGHT(1),
        RUNNING_LEFT(2),
        RUNNING_RIGHT(3);

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

    /** List of hero animations
     *
     */
    protected List<Animation> animations;

    /** Currently active animation
     *
     */
    protected AnimationStates activeAnimation;

    /** Current position of the character
     *
     */
    protected Point currentPosition;

    /** Current game level of the character
     *
     */
    protected DungeonWorld currentDungeonWorld;

    /** Fixed movement speed of the character
     *
     */
    protected static final float MOVEMENT_SPEED = 0.1f;

    /** Health of the character
     *
     */
    protected float health;

    /** Flag if the character is dead
     *
     */
    protected boolean isDead;

    /** Function to call when a character has received damage
     *
     */
    public abstract boolean receiveDamage(DamageDetails damageDetails);

    /** Function to call when the character dies
     *
     */
    public abstract void onDeath();

    /** Get the deletable flag of the character
     *
     * @return True if the character is deletable, else false
     */
    @Override
    public boolean deleteable()
    {
        return isDead;
    }

    /** Get the current position of the character
     *
     * @return The current position of the character
     */
    @Override
    public Point getPosition()
    {
        return this.currentPosition;
    }

    /** Get the current animation of the character
     *
     * @return The current animation of the character
     */
    @Override
    public Animation getActiveAnimation()
    {
        return animations.get(activeAnimation.getIndex());
    }

    /** Change the level in which the character currently is.
     * 
     * The start position in the new level will be randomized.
     *
     * @param level New level
     */
    public void setLevel(DungeonWorld level)
    {
        this.currentDungeonWorld = level;
        findRandomPosition();
    }

    /** Check if a character is dead
     *
     * @return True if the character is dead, else false
     */
    public boolean isCharacterDead()
    {
        return isDead;
    }

    /** Set the character position to a new random location inside the level
     *
     */
    public void findRandomPosition()
    {
        this.currentPosition = new Point(currentDungeonWorld.getRandomPointInDungeon());
    }

    /** Check if another character has contact with this character
     *
     * @param contactRange Range in which the contact will be validated
     * @param characterPosition Position of the other character
     * @return The direction if contact has been made (NONE for no contact)
     */
    protected DamageDirections checkCharacterContact(float contactRange, Point characterPosition, DamageDirections direction)
    {
        boolean hasContact = true;

        // Check if the other character is within the radius to receive contact damage
        // This will be evaluated depending on the side of the attack
        if (direction == DamageDirections.LEFT || 
            direction == DamageDirections.ANY || 
            direction == DamageDirections.TOP || 
            direction == DamageDirections.BOTTOM)
        {
            hasContact = characterPosition.x >= currentPosition.x - contactRange;
        }

        if (direction == DamageDirections.RIGHT || 
            direction == DamageDirections.ANY || 
            direction == DamageDirections.TOP || 
            direction == DamageDirections.BOTTOM)
        {
            hasContact &= characterPosition.x <= currentPosition.x + contactRange;
        }    

        if (direction == DamageDirections.BOTTOM || 
            direction == DamageDirections.ANY || 
            direction == DamageDirections.RIGHT || 
            direction == DamageDirections.LEFT)
        {
            hasContact &= characterPosition.y >= currentPosition.y - contactRange;
        }

        if (direction == DamageDirections.TOP || 
            direction == DamageDirections.ANY || 
            direction == DamageDirections.RIGHT || 
            direction == DamageDirections.LEFT)
        {
            hasContact &= characterPosition.y <= currentPosition.y + contactRange;
        }    

        if (hasContact)
        {
            // Calculate which direction is the nearest to the other character when the attack may come from any side
            if (direction == DamageDirections.ANY)
            {
                // If the delta of the x value is smaller than the delta of the x value we will take the x direction for the damage direction
                if (Math.abs(characterPosition.x - currentPosition.x) > Math.abs(characterPosition.y - currentPosition.y))
                {
                    // If the player has a higher x coordinate than the monster, he received damage from the left, else from the right
                    if (currentPosition.x > characterPosition.x)
                    {
                        return DamageDirections.LEFT;
                    }
                    else
                    {
                        return DamageDirections.RIGHT;
                    }
                }
                // If the delta of the y value is smaller than the delta of the x value we will take the y direction instead
                else
                {
                    // If the player has a higher y coordinate than the monster, he received damage from the bottom, else from the top
                    if (currentPosition.y > characterPosition.y)
                    {
                        return DamageDirections.BOTTOM;
                    }
                    else
                    {
                        return DamageDirections.TOP;
                    }
                }
            }
            // Else the attack side is the input side
            else
            {
                return  direction;
            }
        }
        // Else return damage details that contain information that no damage has been dealt
        else
        {
            return DamageDirections.NONE;
        }
    }
    
    /**
     * Check if Character has contact with position
     * @param contactRange contactRange Range in which the contact will be validated
     * @param characterPosition Position of the other object
     * @return If the contact has been made
     */
    public boolean checkForAnyContact(float contactRange, Point characterPosition)
    {
        boolean hasContact = characterPosition.x >= currentPosition.x - contactRange;
        hasContact &= characterPosition.x <= currentPosition.x + contactRange;
        hasContact &= characterPosition.y >= currentPosition.y - contactRange;
        hasContact &= characterPosition.y <= currentPosition.y + contactRange;

        return hasContact;
    }
    
    /**
     * Sets the position of the Character, if the position is accessible
     * @param x x Coordinate
     * @param y y Coordinate
     */
    public void setPosition(final float x, final float y)
    {
    	currentPosition = new Point(x, y);
    	if(!currentDungeonWorld.isTileAccessible(currentPosition))
    	{
        	findRandomPosition();
    	}
    }
    
    /**
     * Receive damage.
     * @param damage Damage which will be received.
     */
    public void receiveFlatDamage(final float damage)
    {
    	health -= damage;
    	if(health <= 0)
    	{
    		onDeath();
    	}
    }
}
