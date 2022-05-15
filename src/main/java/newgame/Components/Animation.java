package newgame.Components;

import com.badlogic.ashley.core.Component;

public class Animation implements Component
{
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunLeft;
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunRight;
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleLeft;
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleRight;
    public ActiveAnimation activeAnimation;

    public Animation(de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunLeft, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunRight, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleLeft, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleRight)
    {
        this.animationRunLeft = animationRunLeft;
        this.animationRunRight = animationRunRight;
        this.animationIdleLeft = animationIdleLeft;
        this.animationIdleRight = animationIdleRight;
    }

    public enum ActiveAnimation
    {
        RUN_RIGHT,
        RUN_LEFT,
        IDLE_RIGHT,
        IDLE_LEFT
    }
}
