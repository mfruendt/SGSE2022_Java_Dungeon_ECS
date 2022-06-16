package newgame;

import com.badlogic.ashley.core.Engine;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.util.Arrays;

import newgame.Components.*;
import newgame.Factories.HeroFactory;
import newgame.Factories.ItemFactory;
import newgame.Factories.MonsterFactory;
import newgame.Systems.*;
import newgame.characters.*;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** Game handler class that will be used to execute the game logic
 *
 * @author Maxim Fründt
 */
public class GameHandler extends MainController
{
    /* Number of easy monsters that may spawn on a level */
    private final static int NUMBER_OF_EASY_MONSTERS = 700;

    /* Number of hard monsters that may spawn on a level */
    private final static int NUMBER_OF_HARD_MONSTERS = 0;

    /* Number of hard monsters that may spawn on a level */
    private final static int NUMBER_OF_BOSS_MONSTERS = 0;

    /* Engine that handles the systems and entities */
    Engine engine;

    /* Entity that will be observed by the camera */
    Camera cameraEntity;

    /* Variables to measure the frame time */
    long startTime, elapsedTime = 0;

    /** Setup method that will be called upon starting the game
     *
     */
    @Override
    protected void setup()
    {
        // Create new camera entity and engine
        cameraEntity = new Camera();
        engine = new Engine();

        // Add systems to the engine
        engine.addSystem(new SpriteSystem());
        engine.addSystem(new KiSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new HealthSystem(engine));
        engine.addSystem(new DamageSystem(engine));
        engine.addSystem(new ItemSystem(engine));
        engine.addSystem(new KnockbackSystem());
        engine.addSystem(new GuiSystem());
        engine.addSystem(new PlayerControlSystem(engine));
        engine.addSystem(new CameraSystem(cameraEntity));

        // Make the camera follow the camera entity
        camera.follow(cameraEntity);
    }

    /** Method that will be executed on the beginning of every frame
     *
     */
    @Override
    protected void beginFrame()
    {
        startTime = System.nanoTime();

    }

    double[] timeMeasurements = new double[11];
    int currentMeasurement = 0;

    /** Method that will be executed on the end of every frame
     *
     */
    @Override
    protected void endFrame()
    {
        // Update engine
        engine.update(elapsedTime);

        // Measure frame time
        elapsedTime = System.nanoTime() - startTime;

        timeMeasurements[currentMeasurement++] = elapsedTime;

        if (currentMeasurement == 11)
        {
            currentMeasurement = 0;

            Arrays.sort(timeMeasurements);

            GameEventsLogger.getLogger().info("Time: " + timeMeasurements[5]);
        }
    }

    /** Method that will be called upon loading the level
     *
     */
    @Override
    public void onLevelLoad()
    {
        Point spawnPosition = new Point(levelController.getDungeon().getRandomPointInDungeon());
        Position heroPosition = new Position(spawnPosition.x, spawnPosition.y, levelController.getDungeon());
        engine.addEntity(HeroFactory.createHero(heroPosition));

        // Add easy monsters
        for (int i = 0; i < NUMBER_OF_EASY_MONSTERS; i++)
        {
            engine.addEntity(MonsterFactory.createEasyMonster(levelController.getDungeon(), heroPosition));
        }

        // Add hard monsters
        for (int i = 0; i < NUMBER_OF_HARD_MONSTERS; i++)
        {
            engine.addEntity(MonsterFactory.createHardMonster(levelController.getDungeon(), heroPosition));
        }

        // Add boss monsters
        for (int i = 0; i < NUMBER_OF_BOSS_MONSTERS; i++)
        {
            engine.addEntity(MonsterFactory.createBossMonster(levelController.getDungeon(), heroPosition));
        }

        // Add items
        engine.addEntity(ItemFactory.createSwordItem(levelController.getDungeon()));
        engine.addEntity(ItemFactory.createStaffItem(levelController.getDungeon()));
        engine.addEntity(ItemFactory.createShieldItem(levelController.getDungeon()));
        engine.addEntity(ItemFactory.createHealthPotionItem(levelController.getDungeon()));

        GameEventsLogger.getLogger().info(LogMessages.LEVEL_LOADED.toString());
    }
}