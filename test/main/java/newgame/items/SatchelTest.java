package newgame.items;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.GameHandler;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class SatchelTest
{
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidCapacity()
    {
        new Satchel<Potion>(0, ItemType.POTION);
    }

    @Test
    public void testConstructorWithValidParameters()
    {
        Satchel<Potion> satchel = new Satchel<Potion>(10, ItemType.POTION);
        assertEquals(satchel.getSatchelType(), ItemType.POTION);
    }
}
