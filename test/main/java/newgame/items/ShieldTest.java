package newgame.items;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.GameHandler;

import org.junit.BeforeClass;

public class ShieldTest
{
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeDefense()
    {
        new Shield(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeDurablitiy()
    {
        new Shield(5, -1);
    }
}
