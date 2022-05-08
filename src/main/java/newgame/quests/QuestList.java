package newgame.quests;

public enum QuestList
{
    // Killing monsters quest
    QUEST_KILL_MONSTERS(new Quest(5, "To kill or be killed", "Kill monsters", QuestRewards.EXPERIENCE, QuestProgressAttributes.KILLS)),
    // Collecting items quest
    QUEST_COLLECT_ITEMS(new Quest(3, "Let the greed consume you", "Collect any items", QuestRewards.ITEM, QuestProgressAttributes.PICKUPS));

    /* Quest that the enum value holds */
    private final Quest quest;

    /** Create a new quest list entry
     *
     * @param quest Quest that the enum value holds
     */
    QuestList(final Quest quest)
    {
        this.quest = quest;
    }

    /** Get a random quest from the list
     *
     * @return Quest that was randomly selected
     */
    public Quest getQuest()
    {
        return quest;
    }
}
