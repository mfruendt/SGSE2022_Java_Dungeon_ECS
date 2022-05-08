package newgame.items;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import newgame.GameHandler;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class DungeonItemTest
{
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullItem()
    {
        new DungeonItem(null);
    }

    @Test
    public void testConstructorWithItem()
    {
        Potion potion = new Potion();
        DungeonItem testDungeonItem = new DungeonItem(potion);
        assertEquals(testDungeonItem.getItem(), potion);
    }

    @Test
    public void testSetLocationWithNullParameters()
    {
        DungeonItem testDungeonItem = new DungeonItem(new Potion());
        assertThrows(IllegalArgumentException.class, () -> { testDungeonItem.setLocation(null); });
        assertThrows(IllegalArgumentException.class, () -> { testDungeonItem.setLocation(null, null); });
        assertThrows(IllegalArgumentException.class, () -> { testDungeonItem.setLocation(new DungeonWorld(2, 4), null); });
    }
}
