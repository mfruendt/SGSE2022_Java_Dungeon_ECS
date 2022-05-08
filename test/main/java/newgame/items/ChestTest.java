package newgame.items;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.GameHandler;


public class ChestTest
{
    private static final int CAPACITY_OF_TESTED_INVENTORY = 10;

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
        assertEquals(this.testChest.getCapacity(), CAPACITY_OF_TESTED_INVENTORY);
        assertEquals(this.testChest.getAllItems().size(), CAPACITY_OF_TESTED_INVENTORY);
        for (int i = 0; i < CAPACITY_OF_TESTED_INVENTORY; i++)
        {
            assertEquals(this.testChest.getAllItems().get(i), null);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetLevelWithNullLevel()
    {
        this.testChest.setLocation(null);
    }
}