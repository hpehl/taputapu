package name.pehl.taputapu.client.ui.animation;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public final class AnimationUtils
{
    private AnimationUtils()
    {
    }


    /**
     * Flips two widgets in a layout panel using a smooth animation.
     * 
     * @param layoutPanel
     *            the animated layout
     * @param show
     *            the widget to show
     * @param hide
     *            the widget to hide
     * @param direction
     *            controls the direction of the animation
     */
    public static void flip(final LayoutPanel layoutPanel, final Widget show, Direction direction)
    {
        // saftey checks
        if (layoutPanel == null || show == null)
        {
            return;
        }
        final Widget current = layoutPanel.getWidget(0);
        if (current == show)
        {
            return;
        }

        // Initialize the layout.
        layoutPanel.add(show);
        switch (direction)
        {
            case LEFT_TO_RIGHT:
                layoutPanel.setWidgetLeftWidth(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(show, -100, Unit.PCT, 100, Unit.PCT);
                break;
            case RIGHT_TO_RIGHT:
                layoutPanel.setWidgetLeftWidth(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(show, 100, Unit.PCT, 100, Unit.PCT);
                break;
            case TOP_TO_BOTTOM:
                layoutPanel.setWidgetTopHeight(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(show, -100, Unit.PCT, 100, Unit.PCT);
                break;
            case BOTTOM_TO_TOP:
                layoutPanel.setWidgetTopHeight(current, 0, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(show, 100, Unit.PCT, 100, Unit.PCT);
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
                layoutPanel.setWidgetLeftWidth(show, 0, Unit.PCT, 100, Unit.PCT);
                break;
            case RIGHT_TO_RIGHT:
                layoutPanel.setWidgetLeftWidth(current, -100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetLeftWidth(show, 0, Unit.PCT, 100, Unit.PCT);
                break;
            case TOP_TO_BOTTOM:
                layoutPanel.setWidgetTopHeight(current, 100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(show, 0, Unit.PCT, 100, Unit.PCT);
                break;
            case BOTTOM_TO_TOP:
                layoutPanel.setWidgetTopHeight(current, -100, Unit.PCT, 100, Unit.PCT);
                layoutPanel.setWidgetTopHeight(show, 0, Unit.PCT, 100, Unit.PCT);
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
}
