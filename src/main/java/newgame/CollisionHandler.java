package newgame;

import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.util.ArrayList;
import java.util.List;

/** Handler for collision between entities
 * @author Maxim Fründt
 */
public class CollisionHandler<T extends IHashableEntity>
{
    /* Size of the hash table */
    private static final int hashTableSize = 137;

    /* Hash table that contains entities between which the collision will be checked */
    private final List<T> entityHashTable;

    /** Instantiate new collision handler
     */
    public CollisionHandler()
    {
        entityHashTable = new ArrayList<>();

        for (int i = 0; i < hashTableSize; i++)
        {
            entityHashTable.add(null);
        }
    }

    /** Clear the hashtable
     */
    public void clear()
    {
        for (int i = 0; i < hashTableSize; i++)
        {
            entityHashTable.add(null);
        }
    }

    /** Add a new entity to the hash table
     *
     * @param key Key of the entity
     * @param entity Entity that will be added
     * @return True if add was successful, else false
     */
    public boolean addEntity(Point key, T entity)
    {
        if (key == null || entity == null)
        {
            return false;
        }

        boolean isEntityInserted = false;
        int hashIndex;
        int numberOfHashCollisions = 0;

        // Insert entity into hash table by trying until an empty space is found or every place has been tried
        while (!isEntityInserted)
        {
            hashIndex = calculateHash(key, numberOfHashCollisions);

            if (entityHashTable.get(hashIndex) != null)
            {
                numberOfHashCollisions++;
            }
            else
            {
                entityHashTable.set(hashIndex, entity);
                isEntityInserted = true;
            }

            if (numberOfHashCollisions == hashTableSize)
            {
                return false;
            }
        }

        return true;
    }

    /** Remove an existing entity from the hash table
     *
     * @param key Key of the entity
     * @param entity Entity that will be removed
     * @return True if remove was successful, else false
     */
    public boolean removeEntity(Point key, T entity)
    {
        if (key == null)
        {
            return false;
        }

        boolean isEntityDeleted = false;
        boolean areConsolidatedEntitiesDeleted = false;
        int hashIndex;
        int numberOfHashCollisions = 0;
        List<T> entities = new ArrayList<>();

        while (!isEntityDeleted || !areConsolidatedEntitiesDeleted)
        {
            hashIndex = calculateHash(key, numberOfHashCollisions);

            if (entityHashTable.get(hashIndex) == null)
            {
                // If the entity is not yet deleted and we find a null value the entity does not exist
                if (!isEntityDeleted)
                {
                    return false;
                }
                // Else we found all consolidated entities
                else
                {
                    areConsolidatedEntitiesDeleted = true;
                }
            }
            // If we found the first entity with the key, delete it
            else if (entityHashTable.get(hashIndex).getPosition() == key && entityHashTable.get(hashIndex) == entity)
            {
                entityHashTable.set(hashIndex, null);
                isEntityDeleted = true;
                numberOfHashCollisions++;
            }
            // Every other entity will be saved to be moved
            else
            {
                entities.add(entityHashTable.get(hashIndex));
                entityHashTable.set(hashIndex, null);
                numberOfHashCollisions++;
            }

            if (numberOfHashCollisions == hashTableSize)
            {
                return false;
            }
        }

        // Read entities that were consolidated from the removed entity
        for (T savedEntity : entities)
        {
            addEntity(savedEntity.getPosition(), savedEntity);
        }

        return true;
    }

    /** Get a list of all collision with the input key
     *
     * @param key Key that will be used to find collision
     * @return List of collisions
     */
    public List<T> getCollisions(Point key)
    {
        if (key == null)
        {
            return null;
        }

        boolean areEntitiesLeft = true;
        int hashIndex;
        int numberOfHashCollisions = 0;
        List<T> entities = new ArrayList<>();

        // While entities with the same position are left, add them to the list
        while (areEntitiesLeft)
        {
            hashIndex = calculateHash(key, numberOfHashCollisions);

            if (entityHashTable.get(hashIndex) != null)
            {
                if (entityHashTable.get(hashIndex).getPosition().x < key.x + 0.5
                    && entityHashTable.get(hashIndex).getPosition().x > key.x - 0.5
                    && entityHashTable.get(hashIndex).getPosition().y < key.y + 0.5
                    && entityHashTable.get(hashIndex).getPosition().y > key.y - 0.5)
                {
                    numberOfHashCollisions++;
                    entities.add(entityHashTable.get(hashIndex));
                }
                else
                {
                    numberOfHashCollisions++;
                }
            }
            else
            {
                areEntitiesLeft = false;
            }

            if (numberOfHashCollisions == hashTableSize)
            {
                areEntitiesLeft = false;
            }
        }

        return entities;
    }

    private int calculateHash(Point key, int numberOfHashCollisions)
    {
        if (key == null)
        {
            return -1;
        }

        // Hash value will be calculated as X_Coordinate ^ Y_Coordinate + i^2
        return (int)(((key.x + key.y) + Math.pow(numberOfHashCollisions, 2)) % hashTableSize);
    }
}
