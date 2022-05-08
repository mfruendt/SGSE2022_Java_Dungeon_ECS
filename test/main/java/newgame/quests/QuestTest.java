package newgame.quests;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;

import newgame.GameHandler;
import newgame.characters.Hero;
import newgame.items.Potion;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class QuestTest
{
    private Quest questWithExperienceReward;
    private Quest questWithItemReward;
    private Hero hero;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        DesktopLauncher.run(new GameHandler());
    }

    @Before
    public void setUpBeforeMethod()
    {
        this.questWithExperienceReward = new Quest(1, "Some name", "Some description",  QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS);
        this.questWithItemReward = new Quest(1, "Some name", "Some description",  QuestRewards.ITEM, QuestProgressAttributes.PICKUPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZeroTargetCount()
    {
        new Quest(0, "Some name", "Some description", QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeTargetCount()
    {
        new Quest(-50, "Some name", "Some description", QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithUndefinedName()
    {
        new Quest(1, null, "Some description", QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithUndefinedDescription()
    {
        new Quest(1, "Some name", null, QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS);
    }

    @Test
    public void testConstructorWithValidParameters()
    {
        Quest quest = new Quest(1, "Some name", "Some description",  QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS);
        assertEquals(1, quest.getTargetCount());
        assertEquals("Some name", quest.getQuestName());
        assertEquals("Some description", quest.getQuestDescription());
        assertEquals(QuestProgressAttributes.KILLS, quest.getQuestProgressAttribute());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTakeRewardWithNullHero()
    {
        this.questWithExperienceReward.takeReward(null);
    }

    @Test
    public void testTakeRewardWithNotNullHero()
    {
        try 
        {
            this.questWithExperienceReward.onProgressChanged(); // set actualCount equal to initialized targetCount (1)
            fail();
        }
        catch (IllegalAccessError e)
        {
            this.hero = new Hero();
            // success depends on dependencies of hero.
            // so just check, if it goes to do expected heroes Method
            assertEquals(this.questWithExperienceReward.takeReward(this.hero), this.hero.addExperience(1)) ;
        }

        try 
        {
            this.questWithItemReward.onProgressChanged(); // set actualCount equal to initialized targetCount (1)
            fail();
        }
        catch (IllegalAccessError e)
        {
            this.hero = new Hero();
            // success depends on dependencies of hero.
            // so just check, if it goes to do expected heroes Method
            assertEquals(this.questWithItemReward.takeReward(this.hero), this.hero.addItemToInventory(new Potion()));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterWithNullObserver()
    {
        this.questWithExperienceReward.register(null);
    }
}