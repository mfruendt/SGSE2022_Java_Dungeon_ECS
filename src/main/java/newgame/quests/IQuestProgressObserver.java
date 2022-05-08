package newgame.quests;

/** Interface for observer that observe the quest progress.
 * This is used in both directions, to increase quest progress and to
 * end the quest.
 * @author Maxim Fründt
 */
public interface IQuestProgressObserver
{
    /** This function will be called from the observable when quest progress was made
     */
    void onProgressChanged();
}