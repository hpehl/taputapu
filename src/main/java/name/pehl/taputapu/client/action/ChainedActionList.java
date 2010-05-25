package name.pehl.taputapu.client.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Class to execute several {@link ChainedAction}s in a sequence. Used in GWT
 * this enables the sequential execution of several asynchronous actions.
 * Therefore the asynchronous action must be wrapped inside a
 * {@link ChainedAction}, calling
 * {@link ChainedAction#next(Iterator, Context, Command, Command)} in the
 * {@link AsyncCallback#onSuccess(Object)} method:
 * 
 * <pre>
 * final Context context = new Context();
 * final FooServiceAsync fooService = GWT.create(FooService.class);
 * 
 * ChainedAction firstAction = new ChainedAction()
 * {
 *     &#64;Override
 *     public void execute(final Iterator&lt;ChainedAction&gt; iterator, final Context context, 
 *             final Command onSuccess, final Command onError)
 *     {
 *         fooService.method1(new AsyncCallback&lt;String&gt;()
 *         {
 *             public void onSuccess(String result)
 *             {
 *                 // process result...
 *                 next(iterator, context, onSuccess, onError);
 *             }
 *             public void onFailure(Throwable caught) 
 *             {
 *                 // evaluate the error, modify context, ...
 *                 exit(context, onError);
 *             }
 *         }
 *     });
 * };
 * 
 * ChainedAction secondAction = new ChainedAction()
 * {
 *     &#64;Override
 *     public void execute(final Iterator&lt;ChainedAction&gt; iterator, final Context context, 
 *             final Command onSuccess, final Command onError)
 *     {
 *         fooService.method2(new AsyncCallback&lt;String&gt;()
 *         {
 *             public void onSuccess(String result)
 *             {
 *                 // process result...
 *                 next(iterator, context, onSuccess, onError);
 *             }
 *             public void onFailure(Throwable caught) 
 *             {
 *                 // evaluate the error, modify context, ...
 *                 exit(context, onError);
 *             }
 *         }
 *     });
 * };
 * 
 * new ChainedActionList(firstAction, secondAction).start(context, 
 *     new Command()
 *     {
 *         &#64;Override
 *         public void execute()
 *         {
 *             // All actions run successfully
 *         }
 *     },
 *     new Command()
 *     {
 *         &#64;Override
 *         public void execute()
 *         {
 *             // Somme error occurred
 *         }
 *     }
 * );
 * 
 * </pre>
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          81886 $
 */
public class ChainedActionList
{
    private List<ChainedAction> actions;


    // ----------------------------------------------------------- constructors

    /**
     * Created a new instance with the specified {@link ChainedAction}s
     * 
     * @param actions
     */
    public ChainedActionList(ChainedAction... actions)
    {
        this.actions = new ArrayList<ChainedAction>();
        if (actions != null)
        {
            this.actions.addAll(Arrays.asList(actions));
        }
    }


    // ----------------------------------------------- methods with one command

    /**
     * Starts the execution of the actions specified in
     * {@link ChainedActionList#ChainedActionList(ChainedAction...)}. This
     * method creates an empty {@link Context} and calls
     * {@link #start(Context, Command, Command)} using <code>command</code> for
     * both the <code>onSuccess</code> and <code>onError</code> parameter.
     * 
     * @param command
     *            Command which is called after the last action has finished or
     *            when {@link ChainedAction#exit(Command)} was called. Must not
     *            be null!
     */
    public final void start(final Command command)
    {
        start(new Context(), command, command);
    }


    /**
     * Starts the execution of the actions specified in
     * {@link ChainedActionList#ChainedActionList(ChainedAction...)}. This
     * method calls {@link #start(Context, Command, Command)} using
     * <code>command</code> for both the <code>onSuccess</code> and
     * <code>onError</code> parameter.
     * 
     * @param context
     *            Context for sharing data between {@link ChainedAction}s.
     * @param command
     *            Command which is called after the last action has finished or
     *            when {@link ChainedAction#exit(Command)} was called. Must not
     *            be null!
     */
    public final void start(final Context context, final Command command)
    {
        start(context, command, command);
    }


    // ---------------------------------------------- methods with two commands

    /**
     * Starts the execution of the actions specified in
     * {@link ChainedActionList#ChainedActionList(ChainedAction...)}. This
     * method creates an empty {@link Context} and calls
     * {@link #start(Context, Command, Command)}
     * 
     * @param onSuccess
     *            Command which is called after the last action has finished or
     *            when {@link ChainedAction#exit(Command)} was called. Must not
     *            be null!
     * @param onError
     *            Command which can be used to report an error. If
     *            <code>null</code> <code>onSuccess</code> is used.
     */
    public final void start(final Command onSuccess, final Command onError)
    {
        start(new Context(), onSuccess, onError);
    }


    /**
     * Starts the execution of the actions specified in
     * {@link ChainedActionList#ChainedActionList(ChainedAction...)}.
     * 
     * @param context
     *            Context for sharing data between {@link ChainedAction}s.
     * @param onSuccess
     *            Command which is called after the last action has finished or
     *            when {@link ChainedAction#exit(Command)} was called. Must not
     *            be null!
     * @param onError
     *            Command which can be used to report an error. If
     *            <code>null</code> <code>onSuccess</code> is used.
     */
    public final void start(final Context context, final Command onSuccess, final Command onError)
    {
        assert onSuccess != null;

        if (actions.isEmpty())
        {
            onSuccess.execute();
        }
        else
        {
            Iterator<ChainedAction> iterator = actions.iterator();
            ChainedAction firstAction = iterator.next();
            firstAction.execute(iterator, context, onSuccess, onError == null ? onSuccess : onError);
        }
    }
}
