package newgame.quests;

import newgame.characters.Hero;
import newgame.items.Potion;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** Quest class that contains all necessary attributes and methods for a quest
 *
 * @author Maxim Fründt
 */
public class Quest implements IQuestProgressObserver
{
    /* Observer that will be called when the quest is done */
    private IQuestProgressObserver questProgressObserver;
    /* Number of objectives that must be done */
    private final int targetCount;
    /* Number of completed objectives */
    private int actualCount;
    /* Name of the quest */
    private final String questName;
    /* Description of the quest */
    private final String questDescription;
    /* Attribute that needs to be increased to progress in the quest */
    QuestProgressAttributes questProgressAttribute;
    /* Reward for completing the quest */
    QuestRewards reward;

    /** Create a new quest
     *
     * @param targetCount Number of objectives that must be done
     * @param questName Name of the quest
     * @param questDescription Description of the quest
     * @throws IllegalArgumentException if quest name or quest description is null or target count is smaller than 1
     */
    Quest(int targetCount, String questName, String questDescription, QuestRewards reward, QuestProgressAttributes questProgressAttribute) throws IllegalArgumentException
    {
        if (questName == null || questName.isEmpty())
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            throw new IllegalArgumentException("Undefined quest name");
        }
        if (questDescription == null || questDescription.isEmpty())
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            throw new IllegalArgumentException("Undefined quest description");
        }
        if (targetCount < 1)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            throw new IllegalArgumentException("Negative target count");
        }
        this.targetCount = targetCount;
        this.questName = questName;
        this.questDescription = questDescription;
        this.reward = reward;
        this.questProgressAttribute = questProgressAttribute;
        actualCount = 0;
    }

    /** Increase the number of completed objectives
     */
    public void onProgressChanged()
    {
        actualCount++;

        if (actualCount == targetCount)
        {
            onCompleted();
        }
    }

    /** Get the quest progress attributes
     *
     * @return Quest progress attribute
     */
    public QuestProgressAttributes getQuestProgressAttribute()
    {
        return questProgressAttribute;
    }

    /** Take the reward if the quest has been finished
     *
     * @param hero Hero that will get the quest reward
     */
    public boolean takeReward(Hero hero) throws IllegalArgumentException
    {
        if (hero == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            throw new IllegalArgumentException("Hero is null");
        }
        if (actualCount >= targetCount)
        {
            actualCount = 0;

            switch (reward)
            {
                case ITEM:
                    return hero.addItemToInventory(new Potion());
                case EXPERIENCE:
                    return hero.addExperience(1);
            };
        }
        return false;
    }

    /** Get the description of the quest
     *
     * @return Quest description
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /** Get the name of the quest
     *
     * @return Quest name
     */
    public String getQuestName()
    {
        return questName;
    }

    /** Get the target count of the quest
     *
     * @return Target count
     */
    public int getTargetCount()
    {
        return targetCount;
    }

    /** Get the actual count of the quest
     *
     * @return Actual count
     */
    public int getActualCount()
    {
        return actualCount;
    }

    /** Event that will be called when the quest is finished
     */
    protected void onCompleted()
    {
        if (questProgressObserver != null)
        {
            GameEventsLogger.getLogger().info(LogMessages.QUEST_COMPLETED + ": " + this.questName);
            questProgressObserver.onProgressChanged();
        }
        else
        {
            GameEventsLogger.getLogger().severe(LogMessages.OBSERVER_NULL.toString());
            throw new IllegalAccessError(LogMessages.OBSERVER_NULL.toString());
        }
    }

    /** Register an observer of the quest progress
     *
     * @param questProgressObserver Observer of the quest progress
     * @throws IllegalArgumentException if questProgressObserver is null
     */
    public void register(IQuestProgressObserver questProgressObserver) throws IllegalArgumentException
    {
        if (questProgressObserver == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.OBSERVER_NULL.toString());
            throw new IllegalArgumentException(LogMessages.OBSERVER_NULL.toString());
        }
        this.questProgressObserver = questProgressObserver;
    }
}

/** Rewards for completing a quest
 */
enum QuestRewards
{
    EXPERIENCE,
    ITEM
}
