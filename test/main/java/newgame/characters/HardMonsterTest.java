package newgame.characters;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.DamageDetails;
import newgame.GameHandler;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class HardMonsterTest
{
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullHero()
    {
        new HardMonster(null);
    }

    @Test
    public void testReceiveDamageWithNullDamage()
    {
        assertFalse(new HardMonster(new Hero()).receiveDamage(null));
    }

    @Test
    public void testReceiveDamageWithDamage()
    {
        assertTrue(new HardMonster(new Hero()).receiveDamage(new DamageDetails()));
    } 
}
