package newgame.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.TextStage;
import newgame.quests.Quest;

/** Object that manages the player stats HUD
 * @author Maxim Fründt
 */
public class PlayerStatsHud
{
    /* Text stage that will contain all text labels */
    private final TextStage statsTexts;

    /* Font size of the player stats text */
    private final static int STATS_FONT_SIZE = 50;
    /* Font size of the quest text */
    private final static int QUEST_FONT_SIZE = 20;
    /* Start coordinate X of the quest text */
    private final static int QUEST_X0 = 10;
    /* Start coordinate Y of the quest text */
    private final static int QUEST_Y0 = 30;
    /* Start coordinate X of the stats text */
    private final static int STATS_X0 = Gdx.graphics.getWidth() - 280;
    /* Start coordinate Y of the stats text */
    private final static int STATS_Y0 = 10;
    /* Y offset between stats elements */
    private final static int STATS_Y_OFFSET = STATS_FONT_SIZE + 10;
    /* Path to the used font */
    private final static String STATS_FONT_PATH = "assets/fonts/OpenSans-Regular.ttf";
    /* Color of the texts */
    private final static Color STATS_COLOR = Color.CHARTREUSE;

    /* Label that will be used to display the player health */
    private final Label healthLabel;
    /* Label that will be used to display the player skill level */
    private final Label skillLabel;
    /* Label that will be used to display the active quest */
    private final Label questLabel;

    /* Text for the player health */
    private final static String STATS_TEXT_HP = "HP: ";
    /* Text for the player skill level */
    private final static String STATS_TEXT_LEVEL = "Lvl: ";

    /** Create a new player stats HUD
     *
     * @param batch Batch that will be used to render the text fields
     */
    public PlayerStatsHud(SpriteBatch batch)
    {
        statsTexts = new TextStage(batch);

        healthLabel = statsTexts.drawText(STATS_TEXT_HP + 0, STATS_FONT_PATH, STATS_COLOR, STATS_FONT_SIZE, STATS_FONT_SIZE, STATS_FONT_SIZE, STATS_X0, STATS_Y0);
        skillLabel = statsTexts.drawText(STATS_TEXT_LEVEL + 0, STATS_FONT_PATH, STATS_COLOR, STATS_FONT_SIZE, STATS_FONT_SIZE, STATS_FONT_SIZE, STATS_X0, STATS_Y0 + STATS_Y_OFFSET);
        questLabel = statsTexts.drawText("", STATS_FONT_PATH, STATS_COLOR, QUEST_FONT_SIZE, QUEST_FONT_SIZE, QUEST_FONT_SIZE, QUEST_X0, QUEST_Y0);
 }

    /** Draw the stats HUD
     *
     * @param health Health of the player to be displayed
     * @param skillLevel Skill level of the player to be displayed
     */
    public void draw(float health, float skillLevel, Quest quest)
    {
        healthLabel.setText(STATS_TEXT_HP + health);
        skillLabel.setText(STATS_TEXT_LEVEL + (int)skillLevel + " (" + (int)((skillLevel - (int)skillLevel) * 100) + "%)");

        // If the hero has an active quest draw the description, name and progress on scree. Else reset the text
        if (quest != null)
        {
            questLabel.setText(quest.getQuestName() + "\n" + quest.getQuestDescription() + "\n" + quest.getActualCount() + "/" + quest.getTargetCount());
        }
        else
        {
            questLabel.setText("");
        }

        statsTexts.draw();
    }
}
