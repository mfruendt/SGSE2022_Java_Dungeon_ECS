package newgame.characters;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import newgame.DamageDetails;
import newgame.GameHandler;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class EasyMonsterTest
{
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullHero()
    {
        new EasyMonster(null);
    }

    @Test
    public void testReceiveDamageWithNullDamage()
    {
        assertFalse(new EasyMonster(new Hero()).receiveDamage(null));
    }

    @Test
    public void testReceiveDamageWithDamage()
    {
        assertTrue(new EasyMonster(new Hero()).receiveDamage(new DamageDetails()));
    } 
}
