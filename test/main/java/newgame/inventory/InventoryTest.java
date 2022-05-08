package newgame.inventory;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.GameHandler;
import newgame.items.Chest;
import newgame.items.Item;
import newgame.items.ItemType;
import newgame.items.Potion;
import newgame.items.Satchel;
import newgame.items.Spell;


public class InventoryTest
{
    private static final int CAPACITY_OF_TESTED_INVENTORY = 10;

    /* Instance to test abstract class Inventory */
    private Chest<Item> testChest;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Before
    public void setUpBeforeMethod()
    {
        this.testChest = new Chest<Item>(CAPACITY_OF_TESTED_INVENTORY);
    }    

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidCapacity()
    {
        new Chest<Item>(0);
    }

    @Test
    public void testConstructorWithValidCapacity()
    {
        assertEquals(this.testChest.capacity, CAPACITY_OF_TESTED_INVENTORY);
        assertEquals(this.testChest.items.size(), CAPACITY_OF_TESTED_INVENTORY);
        for (int i = 0; i < CAPACITY_OF_TESTED_INVENTORY; i++)
        {
            assertEquals(this.testChest.items.get(i), null);
        }
    }

    @Test
    public void testGetItemAtWithInvalidIndex()
    {
        assertEquals(this.testChest.getItemAt(-1), null);
        assertEquals(this.testChest.getItemAt(CAPACITY_OF_TESTED_INVENTORY), null);
    }

    @Test
    public void testGetItemAtWithValidIndex()
    {
        assertEquals(this.testChest.getItemAt(0), null);
        assertEquals(this.testChest.getItemAt(CAPACITY_OF_TESTED_INVENTORY - 1), null);
        Potion potion = new Potion();
        this.testChest.items.set(0, potion);
        this.testChest.items.set(CAPACITY_OF_TESTED_INVENTORY - 1, potion);
        assertEquals(this.testChest.getItemAt(0), potion);
        assertEquals(this.testChest.getItemAt(CAPACITY_OF_TESTED_INVENTORY - 1), potion);
    }

    @Test
    public void testRemoveItemWithNullItem()
    {
        assertFalse(this.testChest.removeItem(null));
    }

    @Test
    public void testRemoveItemWithNotExistingItem()
    {
        Potion potion = new Potion();
        assertFalse(this.testChest.removeItem(potion));
    }

    @Test
    public void testRemoveItemWithExistingItem()
    {
        Potion potion = new Potion();
        this.testChest.items.set(0, potion);
        assertTrue(this.testChest.removeItem(potion));
    }

    @Test
    public void testEmptyInventorySlotWithInvalidIndex()
    {
        assertFalse(this.testChest.emptyInventorySlot(-1));
        assertFalse(this.testChest.emptyInventorySlot(CAPACITY_OF_TESTED_INVENTORY));
    }

    @Test
    public void testEmptyInventorySlotWithValidLimit()
    {
        Potion potion = new Potion();

        this.testChest.items.set(0, potion);
        this.testChest.items.set(CAPACITY_OF_TESTED_INVENTORY - 1, potion);

        this.testChest.emptyInventorySlot(0);
        this.testChest.emptyInventorySlot(CAPACITY_OF_TESTED_INVENTORY - 1);

        assertEquals(this.testChest.items.get(0), null);
        assertEquals(this.testChest.items.get(CAPACITY_OF_TESTED_INVENTORY - 1), null);
        assertTrue(this.testChest.emptyInventorySlot(0));
        assertTrue(this.testChest.emptyInventorySlot(CAPACITY_OF_TESTED_INVENTORY - 1));
    }

    @Test
    public void testAddItemWithNullItem()
    {
        assertFalse(this.testChest.addItem(null));
    }

    @Test
    public void testAddItemWithItems()
    {
        Potion potion = new Potion();
        assertTrue(this.testChest.addItem(potion));
        assertEquals(this.testChest.items.get(0), potion);

        Satchel<Potion> satchel = new Satchel<Potion>(CAPACITY_OF_TESTED_INVENTORY, ItemType.POTION);
        assertTrue(this.testChest.addItem(satchel));
        assertEquals(this.testChest.items.get(1), satchel);

        // adding second potion should be added into satchel at index 1
        assertTrue(this.testChest.addItem(potion));

        // now fullfill inventory and check reaction on further adding (have to be false, because full)
        // start at index 2 because 1 potion and 1 satchel already added
        // items have to be not potion, because they would be added to satchel
        Spell spell = new Spell("Some spell");
        for(int i = 2; i < CAPACITY_OF_TESTED_INVENTORY; i++)
        {
            this.testChest.addItem(spell);
        }
        assertFalse(this.testChest.addItem(spell));
    }
}