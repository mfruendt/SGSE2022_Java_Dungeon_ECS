package newgame.items;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.GameHandler;

import org.junit.BeforeClass;

public class SpellTest
{
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullSpell()
    {
        new Spell(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptySpell()
    {
        new Spell("");
    }
}