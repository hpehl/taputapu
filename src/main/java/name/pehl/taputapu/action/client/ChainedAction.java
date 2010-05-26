package name.pehl.taputapu.action.client;

import java.util.Iterator;

import com.google.gwt.user.client.Command;

/**
 * Class to execute one distinct action part of a {@link ChainedActionList}. The
 * underlying action has to be defined by overwriting the method
 * {@link #execute(Iterator, Context, Command, Command)}. At the end of this
 * method subclasses must either call
 * {@link #next(Iterator, Context, Command, Command)} to continue with the next
 * action or {@link #exit(Context, Command)} to cancel the execution of the
 * actions.
 * <p>
 * Used in GWT this enables the sequential execution of several asynchronous
 * actions.
 * 
 * @see ChainedActionList
 * @author $Author$
 * @version $Date$ $Revision:
 *          81886 $
 */
public abstract class ChainedAction
{
    /**
     * Overwrite to specify the underlying action. At the end of this method
     * subclasses must either call
     * {@link #next(Iterator, Context, Command, Command)} to continue with the
     * next action or {@link #exit(Context, Command)} to cancel the execution of
     * the actions.
     * 
     * @param iterator
     *            The iterator over the {@link ChainedActionList}
     * @param context
     *            Shared context between actions of a {@link ChainedActionList}
     * @param onSuccess
     *            Command which is called after the last action has finished.
     * @param onError
     *            Command which can be used to report an error.
     */
    public abstract void execute(final Iterator<ChainedAction> iterator, final Context context,
            final Command onSuccess, final Command onError);


    /**
     * Starts the next action. Calls {@link #exit(Context, Command)} with the
     * <code>onSuccess</code> command if this is the last action.
     * 
     * @param iterator
     *            The iterator over the {@link ChainedActionList}
     * @param context
     *            Shared context between actions of a {@link ChainedActionList}
     * @param onSuccess
     *            Command which is called after the last action has finished.
     * @param onError
     *            Command which can be used to report an error.
     */
    public final void next(final Iterator<ChainedAction> iterator, final Context context, final Command onSuccess,
            final Command onError)
    {
        if (iterator.hasNext())
        {
            ChainedAction nextAction = iterator.next();
            nextAction.execute(iterator, context, onSuccess, onError);
        }
        else
        {
            exit(context, onSuccess);
        }
    }


    /**
     * Calls the {@link Command#execute()} method of the specified command.
     * 
     * @param context
     *            Shared context between actions of a {@link ChainedActionList}
     * @param command
     */
    public final void exit(final Context context, final Command command)
    {
        command.execute();
    }
}
