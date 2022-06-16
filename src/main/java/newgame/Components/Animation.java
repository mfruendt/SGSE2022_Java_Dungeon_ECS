package newgame.Components;

import com.badlogic.ashley.core.Component;

/** Component used to animate entities
 * @author Maxim Fründt
 */
public class Animation implements Component
{
    /** Animation for running to the left */
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunLeft;
    /** Animation for running to the right */
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunRight;
    /** Animation for idling to the left */
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleLeft;
    /** Animation for idling to the right */
    public de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleRight;
    /** Animation that is currently active */
    public ActiveAnimation activeAnimation;

    /** Create new animation component
     *
     * @param animationRunLeft Animation for running to the left
     * @param animationRunRight Animation for running to the right
     * @param animationIdleLeft Animation for idling to the left
     * @param animationIdleRight Animation for idling to the right
     */
    public Animation(de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunLeft, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationRunRight, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleLeft, de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation animationIdleRight)
    {
        this.animationRunLeft = animationRunLeft;
        this.animationRunRight = animationRunRight;
        this.animationIdleLeft = animationIdleLeft;
        this.animationIdleRight = animationIdleRight;
    }

    /** Animations that can be active
     * @author Maxim Fründt
     */
    public enum ActiveAnimation
    {
        RUN_RIGHT,
        RUN_LEFT,
        IDLE_RIGHT,
        IDLE_LEFT
    }
}
