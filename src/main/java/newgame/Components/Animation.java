package newgame.Components;

import com.badlogic.ashley.core.Component;

public class Animation implements Component
{
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationLeft;
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRight;
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdle;

    public Animation(de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationLeft, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRight, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdle)
    {
        this.animationLeft = animationLeft;
        this.animationRight = animationRight;
        this.animationIdle = animationIdle;
    }
}
