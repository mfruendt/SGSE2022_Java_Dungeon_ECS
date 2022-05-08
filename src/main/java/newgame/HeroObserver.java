package newgame;

import newgame.items.Item;

public interface HeroObserver
{
    <T extends Item> void onItemDropped(T droppedItem);
}
