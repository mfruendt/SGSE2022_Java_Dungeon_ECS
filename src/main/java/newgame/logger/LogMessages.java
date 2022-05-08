package newgame.logger;

/** Defined messages to log events
 * 
 * @author Benjamin Krüger
 */
public enum LogMessages
{
    KEYBOARD_INPUT("Keyboard input"),
    CHARACTER_SPAWN("Character spawn"),
    LEVEL_LOADED("Dungeon level loaded"),
    HERO_GOT_DAMAGE("Hero got damage"),
    HERO_KNOCKBACK("Hero knockbacked"),
    HERO_ATTACKS("Hero attacks"),
    HERO_HARMED_MONSTER("Hero harmed monster"),
    HERO_MOVED("Hero moved"),
    GAME_OVER("GAME OVER"),
    RESTART_GAME("Starting new game... good luck!"),
    ILLEGAL_ARGUMENT("ILLEGAL Argument!"),
    NO_TEXTURE_FOUND("No texture found!"),
    MONSTER_KILLED("Monster killed"),
    ANIMATION_NOT_FOUND("Animation not found"),
    INVENTORY_FULL("There is no more place left in inventory"),
    INVENTORY_NULL("Inventory is null"),
    ITEM_ADDED_TO_INVENTORY("Item added to inventory"),
    ITEM_DROPPED_FROM_INVENTORY("Item dropped from inventory"),
    ITEM_ADDED_TO_SATCHEL_IN_INVENTORY("Item added to satchel in inventory"),
    ARGUMENT_OUT_OF_RANGE("Argument out of range"),
    CANT_EQUIP_INVALID_ITEM_TYPE("Can not equip invalid item type"),
    CHEST_ITEMS("CHEST ITEMS:"),
    HERO_INVENTORY_ITEMS("HERO INVENTORY ITEMS:"),
    INVENTORY_ITEMS_ARRAY_IS_EMPTY("Inventory items array is empty!"),
    TRIED_TO_USE_INVALID_ITEM_TYPE("Tried to use invalid item type!"),
    CAN_NOT_USE_EMPTY_PLACE("Can not use empty place of inventory"),
    CAN_NOT_DROP_EMPTY_PLACE("Can not drop empty place of inventory"),
    DRANK_POTION("Hero successfully drank potion"),
    READ_SPELL("Hero successfully read spell"),
    WEAPON_EQUIPPED("Weapon successfully equipped"),
    SHIELD_EQUIPPED("Shield successfully equipped"),
    WEAPON_SPENT("Weapon destroyed: weapon spent"),
    DROPPING_CHEST_ITEM_FAILED("Error: dropping chest item failed!"),
    DROPPING_ITEM_FAILED("Dropping item failed"),
    CANT_DROP_FROM_EMPTY_CHEST_PLACE("Can not drop from empty chest place"),
    DROPPED_ITEM_FROM_CHEST_INTO_INVENTORY("Successfully dropped item from chest into heroes inventory"),
    ITEM_IS_NOT_A_SATCHEL("Item is not a satchel!"),
    NO_ITEM_IN_SATCHEL("No items in satchel"),
    GUI_ENTITY_NULL("Added HUD entity was null"),
    OBSERVER_NULL("Observer that was tried to be called was null"),
    TRAP_ACTIVATED(" Trap Activated"),
    HERO_FLASH_SKILL("Hero has used flash skill"),
    HERO_TELEPORT_SKILL("Hero has used teleport skill"),
    QUEST_ABORTED("Quest aborted"),
    QUEST_ACCEPTED("New quest accepted"),
    QUEST_COMPLETED("Quest completed"),
	HERO_SHOOTS("Hero shoots"),
    ACCESS_WITH_NULL_ATTRIBUTE("Tried to access with null attribute"),
    UNKNOWN_VARIABLE_VALUE("Unknown variable value"),
    SATCHEL_EMPTY("Satchel is empty"),
    FAILED_TO_USE_ITEM("Failed to use item!");

    private final String text;

    /* Create a new element within an const text value */
    LogMessages(final String text)
    {
        this.text = text;
    }

    /** Get const value of element as string
     * 
     * @return Const value of element
     */
    @Override
    public String toString() 
    {
        return text;
    }
}