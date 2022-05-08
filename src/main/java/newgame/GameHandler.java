package newgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.util.ArrayList;
import java.util.List;

import newgame.Components.*;
import newgame.Systems.*;
import newgame.animations.CharacterAnimations;
import newgame.characters.*;
import newgame.gui.HudHandler;
import newgame.items.Chest;
import newgame.items.DungeonItem;
import newgame.items.Item;
import newgame.items.weapons.Projectile;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.traps.Trap;

/** Game handler class that will be used to execute the game logic
 *
 * @author Maxim Fründt
 */
public class GameHandler extends MainController implements HeroObserver
{
    /* Number of easy monsters that may spawn on a level */
    private final static int NUMBER_OF_EASY_MONSTERS = 300;

    /* Number of hard monsters that may spawn on a level */
    private final static int NUMBER_OF_HARD_MONSTERS = 50;

    /* Number of frames that an attack will be active (= deal damage) */
    private final static int NUMBER_OF_ATTACK_FRAMES = 30;

    /* Number of frames between two character contacts */
    private final static int CONTACT_FRAMES = 30;

    /* Playable hero character */
    private Hero hero;

    /* Handler of the game hud */
    private HudHandler hud;

    /* List of all monsters of the current dungeon level */
    private List<Monster> monsters;

    private Wizard questGiver;

    /* List of all items in the current dungeon level */
    private List<DungeonItem> items;
    
    /* List of all chests in the current dungeon level */
    private List<Chest<Item>> chests;
    
    /* List of all traps in the current dungeon level */
    private List<Trap> traps;
    
    /* List of all projectiles in the current dungeon level */
    private List<Projectile> projectiles;

    /* Handler for item collisions */
    private CollisionHandler<DungeonItem> collisionHandler;

    /* Counter for attack frames
     * After the counter reached the NUMBER_OF_ATTACK_FRAMES the attack of the hero will be ended
     */
    private int attackFrameCounter = 0;

    private int contactFrameCounter = -1;

    Engine engine;
    Camera cameraEntity;
    long startTime, elapsedTime = 0;

    /** Setup method that will be called upon starting the game
     *
     */
    @Override
    protected void setup()
    {
        cameraEntity = new Camera();
        engine = new Engine();
        SpriteSystem spriteSystem = new SpriteSystem();
        KiMovementSystem kiMovementSystem = new KiMovementSystem();
        MovementSystem movementSystem = new MovementSystem();
        HealthSystem healthSystem = new HealthSystem(engine);
        DamageSystem damageSystem = new DamageSystem(engine);
        //CollisionSystem collisionSystem = new CollisionSystem();
        CameraSystem cameraSystem = new CameraSystem(cameraEntity);
        PlayerControlSystem playerControlSystem = new PlayerControlSystem(engine);
        engine.addSystem(spriteSystem);
        engine.addSystem(kiMovementSystem);
        engine.addSystem(movementSystem);
        engine.addSystem(healthSystem);
        engine.addSystem(damageSystem);
        //engine.addSystem(collisionSystem);
        engine.addSystem(playerControlSystem);
        engine.addSystem(cameraSystem);

        // Create new list of monsters, items and a hero
        monsters = new ArrayList<>();
        
        items = new ArrayList<>();

        chests = new ArrayList<>();

        traps = new ArrayList<>();
        
        projectiles = new ArrayList<>();

        questGiver = new Wizard();
        
        //hero = new Hero();
        //this.hero.register(this);

        //collisionHandler = new CollisionHandler<>();

        //hud = new HudHandler();

        // Add hero to the entity controller and set the camera to follow the hero
        //entityController.addEntity(hero);
        //if (!hud.addPlayerEntity(hero, hero.getInventorySize()))
        //{
        //    GameEventsLogger.getLogger().severe(LogMessages.GUI_ENTITY_NULL.toString());
        //}

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

    /** Method that will be executed on the end of every frame
     *
     */
    @Override
    protected void endFrame()
    {
        engine.update(elapsedTime);

        //hud.update();

        //if (contactFrameCounter == -1)
        //{
        //    for (Monster monster : monsters)
        //    {
        //        // Check if damage has been dealt to the hero by a monster
        //        if (monster.handleAttacks(hero))
        //        {
        //            contactFrameCounter++;
        //        }

        //        // Check if damage has been dealt to a monster by the hero
        //        DamageDetails damageDetails = hero.checkMonsterContact(monster.getPosition());

        //        if (damageDetails.hasPlayerContact())
        //        {
        //            monster.receiveDamage(damageDetails);
        //            hero.resetAttack();
        //            contactFrameCounter++;
        //        }
        //    }

        //    if (hero.checkForAnyContact(0.5f, questGiver.getPosition()))
        //    {
        //        hero.handleQuestInputs(questGiver.getQuests());
        //    }

        //    // Check if the player interacted with the quest giver
        //    DamageDetails damageDetails = hero.checkMonsterContact(questGiver.getPosition());
        //    if (damageDetails.hasPlayerContact())
        //    {
        //        questGiver.showQuests();
        //        hero.resetAttack();
        //        contactFrameCounter++;
        //    }
        //}
        //else
        //{
        //    if (contactFrameCounter < CONTACT_FRAMES)
        //    {
        //        contactFrameCounter++;
        //    }
        //    else
        //    {
        //        contactFrameCounter = -1;
        //    }
        //}

        //for(Trap trap : traps)
        //{
        //    trap.setHeroHasPotion(hero.isTrapPotion());
        //    boolean trapContact = hero.checkForAnyContact(trap.getSize(), trap.getPosition());
        //    if(trapContact)
        //    {
        //        trap.activate();
        //        if(trap.isActive())
        //        {
        //            if(trap.getType() == ActivationType.DAMAGE)
        //            {
        //                trap.deactivate();
        //                hero.receiveFlatDamage(trap.getDamage());
        //            }
        //            else if(trap.getType() == ActivationType.TELEPORT)
        //            {
        //                trap.deactivate();
        //                hero.findRandomPosition();
        //            }
        //            else if(trap.getType() == ActivationType.MONSTER)
        //            {
        //                trap.deactivate();
        //                HardMonster monster = new HardMonster(hero);
        //                entityController.addEntity(monster);
        //                monster.setLevel(levelController.getDungeon());
        //                monster.setPosition(trap.getPosition().x, trap.getPosition().y);
        //                monsters.add(monster);
        //            }
        //        }
        //    }

        //    for(Monster monster : new ArrayList<>(monsters))
        //    {
        //        boolean monsterTrapContact = monster.checkForAnyContact(trap.getSize(), trap.getPosition());
        //        if(monsterTrapContact)
        //        {
        //            trap.activate();
        //            if(trap.isActive())
        //            {
        //                if(trap.getType() == ActivationType.DAMAGE)
        //                {
        //                    trap.deactivate();
        //                    monster.receiveFlatDamage(trap.getDamage());
        //                }
        //                else if(trap.getType() == ActivationType.TELEPORT)
        //                {
        //                    trap.deactivate();
        //                    monster.findRandomPosition();
        //                }
        //                else if(trap.getType() == ActivationType.MONSTER)
        //                {
        //                    trap.deactivate();
        //                    HardMonster trapMonster = new HardMonster(hero);
        //                    entityController.addEntity(trapMonster);
        //                    trapMonster.setLevel(levelController.getDungeon());
        //                    trapMonster.setPosition(trap.getPosition().x, trap.getPosition().y);
        //                    monsters.add(trapMonster);
        //                }
        //            }
        //        }
        //    }
        //}

        //for(Monster monster : monsters)
        //{
        //	if(monster.getProjectile() != null)
        //	{
        //		projectiles.add(monster.getProjectile());
        //		entityController.addEntity(monster.getProjectile());
        //		monster.removeProjectile();
        //	}
        //}
        
        //if(hero.getProjectile() != null)
        //{
        //	projectiles.add(hero.getProjectile());
        //	entityController.addEntity(hero.getProjectile());
        //	hero.removeProjectile();
        //}
        
        //for(Projectile projectile : projectiles)
        //{
	    //    boolean projectileContact = hero.checkForAnyContact(projectile.getSize(), projectile.getPosition());
	    //    if(projectileContact)
	    //    {
	    //    	hero.receiveFlatDamage(projectile.getDamage());
	    //
	    //    	projectile.destroy();
	    //    }
	    //
	    //    for(Monster monster : new ArrayList<>(monsters))
	    //    {
	    //    	boolean monsterProjectileContact = monster.checkForAnyContact(projectile.getSize(), projectile.getPosition());
	    //        if(monsterProjectileContact)
	    //        {
	    //        	monster.receiveFlatDamage(projectile.getDamage());
	    //            projectile.destroy();
	    //        }
	    //    }
        //}

        //this.hero.handleItemPickUp(this.items);
        //this.hero.handleInputsForChestInteractions(this.chests);
        
        //for(Chest<Item> chest : chests)
        //{
        //    if (chest.isFullyOpen())
        //    {
        //        if (this.hud.getChestHudElement() == null)
        //        {
        //            this.hud.addChestView(chest.getCapacity());
        //        }
        //        chest.log(new InventoryHudLogVisitor(hud));
        //    }
        //    else
        //    {
        //        this.hud.removeChestView();
        //    }
        //}

        //if (levelController.checkForTrigger(hero.getPosition()))
        //{
        //    // Remove all current monsters from the entity controller
        //    for (Monster monster : monsters)
        //    {
        //        entityController.removeEntity(monster);
        //    }
        //
        //    for(DungeonItem item : items)
        //    {
        //    	entityController.removeEntity(item);
        //    }
        //
        //    for(Chest<Item> chest : chests)
        //    {
        //    	entityController.removeEntity(chest);
        //    }
        //
        //    for(Trap trap : traps)
        //    {
        //    	entityController.removeEntity(trap);
        //    }
        //
        //    for(Projectile projectile : projectiles)
        //    {
        //    	entityController.removeEntity(projectile);
        //    }

        //    // Remove all monsters, chests and items and trigger the next stage
        //    collisionHandler.clear();
        //    monsters.clear();
        //    items.clear();
        //    chests.clear();
        //    traps.clear();
        //    projectiles.clear();

        //    levelController.triggerNextStage();
        //}
        
        // Check if any monster is dead, if so remove it
        //for (Monster monster : new ArrayList<>(monsters))
        //{
        //    if (monster.isCharacterDead())
        //    {
        //        hero.addExperience(monster.getExperiencePoints());
        //        monsters.remove(monster);
        //        entityController.removeEntity(monster);
        //    }
        //}

        //List<DungeonItem> collectableItems = collisionHandler.getCollisions(hero.getPosition());

        //for (DungeonItem item : collectableItems)
        //{
        //    if (item.isPickedUp())
        //    {
        //        hero.addItemToInventory(item.getItem());
        //        collisionHandler.removeEntity(item.getPosition(), item);
        //        entityController.removeEntity(item);
        //        items.remove(item);
        //    }
        //}

        //for(DungeonItem item : new ArrayList<>(items))
        //{
        //    // if new items added (when hero dropped some), add them to controller
        //    if (!entityController.getList().contains(item))
        //    {
        //        entityController.addEntity(item);
        //        item.setLocation(levelController.getDungeon(), new Point(this.hero.getPosition()));
        //        collisionHandler.addEntity(item.getPosition(), item);
        //    }
        //}
        
        //for(Trap trap : new ArrayList<>(traps))
        //{
        //	if(trap.deleteable())
        //	{
        //		traps.remove(trap);
        //		entityController.removeEntity(trap);
        //	}
        //}
        
        //for(Projectile projectile : new ArrayList<>(projectiles))
        //{
        //	if(projectile.isDestroyed())
        //	{
        //		projectiles.remove(projectile);
        //		entityController.removeEntity(projectile);
        //	}
        //}

        //if (hero.getHasInventoryContentChanged())
        //{
        //    hero.logInventoryItems(new InventoryConsoleLogVisitor());
        //    hero.logInventoryItems(new InventoryHudLogVisitor(hud));
        //}

        // Increase the attack frame counter if an attack is ongoing, else reset it to 0
        //if (hero.getAttackDirection() == DamageDirections.NONE)
        //{
        //    attackFrameCounter = 0;
        //}
        //else
        //{
        //    attackFrameCounter++;
        //}

        // If the frame for an attack has expired reset the attack state of the hero
        //if (attackFrameCounter >= NUMBER_OF_ATTACK_FRAMES)
        //{
        //    attackFrameCounter = 0;
        //    hero.resetAttack();
        //}

        //if (hero.isCharacterDead())
        //{
        //    restartGame();
        //}

        elapsedTime = System.nanoTime() - startTime;

        GameEventsLogger.getLogger().info("Time: " + elapsedTime);
    }

    /** Method that will be called upon loading the level
     *
     */
    @Override
    public void onLevelLoad()
    {
        Entity heroEntity = new Entity();
        Point spawnPosition = new Point(levelController.getDungeon().getRandomPointInDungeon());
        heroEntity.add(new Position(spawnPosition.x, spawnPosition.y, levelController.getDungeon()));
        heroEntity.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_RUN_L), CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_RUN_R), CharacterAnimations.getAnimation(CharacterAnimations.Animations.HERO_M_IDLE_L)));
        heroEntity.add(new Velocity());
        heroEntity.add(new Collisions());
        heroEntity.add(new PlayerControl(0.2f));
        engine.addEntity(heroEntity);

        for (int i = 0; i < 10; i++)
        {
            Entity entity = new Entity();
            spawnPosition = new Point(levelController.getDungeon().getRandomPointInDungeon());
            entity.add(new Position(spawnPosition.x, spawnPosition.y, levelController.getDungeon()));
            entity.add(new Animation(CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_RUN_L), CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_RUN_R), CharacterAnimations.getAnimation(CharacterAnimations.Animations.DEMON_IDLE_L)));
            entity.add(new EasyMonsterKi(0.1f));
            entity.add(new Velocity());
            entity.add(new Health(0.5f));
            entity.add(new Collisions());
            engine.addEntity(entity);
        }

        // Create monsters
        for (int i = 0; i < NUMBER_OF_EASY_MONSTERS; i++)
        {
           // monsters.add(new EasyMonster(hero));
        }

        for (int i = 0; i < NUMBER_OF_HARD_MONSTERS; i++)
        {
            //monsters.add(new HardMonster(hero));
        }

        //monsters.add(new MiniBoss(hero));
        
        //items.add(new DungeonItem(new RegularSword()));
        //items.add(new DungeonItem(new Shield(5, 3)));
        //items.add(new DungeonItem(new Potion()));
        //items.add(new DungeonItem(new Spell("Avada Kedavra")));
        //items.add(new DungeonItem(new Satchel<Weapon>(5, ItemType.WEAPON)));
        
        //Bow
        //items.add(new DungeonItem(new Bow(false, 0.4f)));
        
        //Staff
        //items.add(new DungeonItem(new Staff(true, 0.4f)));
        
        //int capacity = 10;
        //Chest<Item> chest1 = new Chest<>(capacity);
        //chest1.fillRandom();
        //chests.add(chest1);
        
        //Add items to the entity controller and set their level to the current dungeon
        //for(DungeonItem item : items)
        //{
        //	entityController.addEntity(item);
        //	item.setLocation(levelController.getDungeon());
        //	collisionHandler.addEntity(item.getPosition(), item);
        //}
        
        //Add chests to the entity controller and set their level to the current dungeon
        //for(Chest<Item> chest : chests)
        //{
        //	entityController.addEntity(chest);
        //	chest.setLocation(levelController.getDungeon());
        //}

        //if (!hud.addChestView(chests.get(0).getCapacity()))
        //{
        //    GameEventsLogger.getLogger().severe(LogMessages.GUI_ENTITY_NULL.toString());
        //}


        //Damage trap, not cloaked
        //traps.add(new Trap(0.8f, ActivationType.DAMAGE, false, -1, true, 15, 10, 15, -1, -1, 3f));
        
        //Teleport trap, cloaked, teleports immediately
        //traps.add(new Trap(0.8f, ActivationType.TELEPORT, true, -1, true, 1, 5, 10, -1, -1, 0));
        
        //Monster trap, not cloaked, 1 activation, spawns immediately
        //traps.add(new Trap(0.8f, ActivationType.MONSTER, false, 1, false, 2, 10, 15, -1, -1, 0));
        
        //for(Trap trap : traps)
        //{
        //	entityController.addEntity(trap);
        //	trap.setLevel(levelController.getDungeon());
        //}
        
        // Set the level of the hero to the current dungeon
        //hero.setLevel(levelController.getDungeon());

        // Add monsters to the entity controller and set their level to the current dungeon
        //for (Monster monster : monsters)
        //{
        //    entityController.addEntity(monster);
        //    monster.setLevel(levelController.getDungeon());
        //}

        //entityController.addEntity(questGiver);
        //questGiver.setLevel(levelController.getDungeon());

        GameEventsLogger.getLogger().info(LogMessages.LEVEL_LOADED.toString());
    }

    private void restartGame()
    {
        entityController.removeEntity(hero);
        entityController.removeEntity(questGiver);
        hud.removePlayerEntity();

        for (Monster monster : monsters)
        {
            entityController.removeEntity(monster);
        }
        for (DungeonItem item : items)
        {
            entityController.removeEntity(item);
            collisionHandler.removeEntity(item.getPosition(), item);
        }
        for (Chest<Item> chest : chests)
        {
            entityController.removeEntity(chest);
        }
        for (Trap trap : traps)
        {
            entityController.removeEntity(trap);
        }
        for (Projectile projectile : projectiles)
        {
        	entityController.removeEntity(projectile);
        }
        setup();
        onLevelLoad();
    }

    /**
     * Event handler for when hero dropped an item
     * Item will be added to dungeon item list and will be drawn in dungeon
     * @param droppedItem Item that is dropped by the hero
     */
    @Override
    public void onItemDropped(Item droppedItem)
    {
        if (droppedItem == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return;
        }
        this.items.add(new DungeonItem(droppedItem));
    }
}