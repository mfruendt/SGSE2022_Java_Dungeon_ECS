package newgame.characters;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.DamageDetails;
import newgame.GameHandler;
import newgame.DamageDetails.DamageDirections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;

public class HeroTest
{
    private Hero hero;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Before
    public void setUpBeforeMethod()
    {
        this.hero = new Hero();
    }

    @Test
    public void testReceiveDamageWithNullDamage()
    {
        assertFalse(this.hero.receiveDamage(null));
    }

    @Test
    public void testReceiveDamageWithDamage()
    {
        assertFalse(this.hero.receiveDamage(new DamageDetails()));
        // With valid DamageDirection function have to fail either,
        // because currentPosition is not set
        assertFalse(this.hero.receiveDamage(new DamageDetails(DamageDirections.BOTTOM, 5.0f)));

        // now check with previous set current position
        this.hero.currentPosition = new Point(30, 30);
        assertTrue(this.hero.receiveDamage(new DamageDetails(DamageDirections.BOTTOM, 5.0f)));
    } 

    @Test
    public void testAddItemWithNullItem()
    {
        assertFalse(this.hero.addItemToInventory(null));
    }

    @Test
    public void testAddExperienceWithNegativeExperience()
    {
        assertFalse(this.hero.addExperience(-1));
    }
}
