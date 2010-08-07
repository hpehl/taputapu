package name.pehl.taputapu.ui.client.animation;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Various animation methods.
 * 
 * @author $Author$
 * @version $Date$ $Revision$
 */
public final class AnimationUtils
{
    private AnimationUtils()
    {
    }


    /**
     * Flips the first widget of <code>layoutPanel</code> with
     * <code>widget</code> using a smooth animation.
     * 
     * @param layoutPanel
     *            the animated layout
     * @param widget
     *            the widget to show
     * @param direction
     *            controls the direction of the animation
     */
    public static void flip(final LayoutPanel layoutPanel, final Widget widget, Direction direction)
    {
        // saftey checks
        if (layoutPanel == null || widget == null)
        {
            return;
        }
        final Widget current = layoutPanel.getWidget(0);
        if (current == widget)
        {
            return;
        }

        // Initialize the layout.
        layoutPanel.add(widget);
        switch (direction)
        {
            case LEFT_TO_RIGHT:
                layoutPanel.setWidgetLeftWidth(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(widget, -100, Unit.PCT, 100, Unit.PCT);
                break;
            case RIGHT_TO_LEFT:
                layoutPanel.setWidgetLeftWidth(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(widget, 100, Unit.PCT, 100, Unit.PCT);
                break;
            case TOP_TO_BOTTOM:
                layoutPanel.setWidgetTopHeight(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(widget, -100, Unit.PCT, 100, Unit.PCT);
                break;
            case BOTTOM_TO_TOP:
                layoutPanel.setWidgetTopHeight(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(widget, 100, Unit.PCT, 100, Unit.PCT);
                break;
            default:
                break;
        }
        layoutPanel.forceLayout();

        // Slide into view.
        switch (direction)
        {
            case LEFT_TO_RIGHT:
                layoutPanel.setWidgetLeftWidth(current, 100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(widget, 0, Unit.PCT, 100, Unit.PCT);
                break;
            case RIGHT_TO_LEFT:
                layoutPanel.setWidgetLeftWidth(current, -100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(widget, 0, Unit.PCT, 100, Unit.PCT);
                break;
            case TOP_TO_BOTTOM:
                layoutPanel.setWidgetTopHeight(current, 100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(widget, 0, Unit.PCT, 100, Unit.PCT);
                break;
            case BOTTOM_TO_TOP:
                layoutPanel.setWidgetTopHeight(current, -100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(widget, 0, Unit.PCT, 100, Unit.PCT);
                break;
            default:
                break;
        }
        layoutPanel.animate(500, new Layout.AnimationCallback()
        {
            public void onAnimationComplete()
            {
                // Remove the old widget when the animation completes.
                layoutPanel.remove(current);
            }


            public void onLayout(Layer layer, double progress)
            {
            }
        });
    }

    public enum Direction
    {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP
    }
}
