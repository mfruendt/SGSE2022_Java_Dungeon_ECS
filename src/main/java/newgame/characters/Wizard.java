package newgame.characters;

import newgame.DamageDetails;
import newgame.animations.CharacterAnimations;
import newgame.logger.GameEventsLogger;
import newgame.quests.Quest;
import newgame.quests.QuestList;

import java.util.ArrayList;
import java.util.List;

public class Wizard extends Character
{
    /* Quest that the wizard may give to the player */
    private final List<Quest> quests;

    public Wizard()
    {
        quests = new ArrayList<>();

        for (int i = 0; i < QuestList.values().length && i < 10; i++)
        {
            quests.add(QuestList.values()[i].getQuest());
        }

        animations = new ArrayList<>();
        animations.add(CharacterAnimations.getAnimation(CharacterAnimations.Animations.WIZARD_IDLE));
        activeAnimation = AnimationStates.IDLE_LEFT;
        isDead = false;
    }

    /** Get the list of quests
     *
     * @return List of quests
     */
    public List<Quest> getQuests()
    {
        return quests;
    }

    /** Show all quests to let the hero choose one
     */
    public void showQuests()
    {
        StringBuilder questsLog = new StringBuilder("Quests:\n");

        for (int i = 0; i < quests.size(); i++)
        {
            questsLog.append(i).append(" -> ").append(quests.get(i).getQuestName()).append("\n");
        }

        GameEventsLogger.getLogger().info(questsLog.toString());
    }

    @Override
    public boolean receiveDamage(final DamageDetails damageDetails)
    {
        return false;
    }

    @Override
    public void onDeath()
    {

    }

    @Override
    public void update()
    {
        this.draw();
    }
}
