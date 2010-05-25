package name.pehl.taputapu.client.action;

import java.util.Iterator;

import com.google.gwt.dev.shell.CloseButton.Callback;
import com.google.gwt.user.client.Command;

/**
 * Class to execute one distinct action in {@link ChainedActionList}. The
 * underlying action must be performed by overwriting the method
 * {@link #execute(Iterator, Context, Command, Command)}. In this method
 * subclasses must either call
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
     * Abgeleitet Methoden müssen hier die eigentliche Aktion implementieren. Am
     * Ende der Methode muss entweder {@link #next(Iterator, Context, Callback)}
     * aufgerufen werden, um die nächste Aktion zu starten oder
     * {@link #exit(Callback)} um die Ausführung abzubrechen.
     * 
     * @param iterator
     * @param context
     *            Gemeinsamer Datenpool zum Austausch von Informationen
     * @param finished
     *            Callback der nach der letzten Aktion oder bei Abbruch
     *            aufgerufen wird.
     */
    public abstract void execute(final Iterator<ChainedAction> iterator, final Context context,
            final Command onSuccess, final Command onError);


    /**
     * Startet die nächste Aktion oder ruft {@link #exit(Callback)} auf, wenn
     * diese Aktion die letzte in der Kette ist.
     * 
     * @param iterator
     * @param context
     *            Gemeinsamer Datenpool zum Austausch von Informationen
     * @param finished
     *            Callback der nach der letzten Aktion oder bei Abbruch
     *            aufgerufen wird.
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
     * Ruft {@link Callback#onSuccess()} des übergebenen Callbacks auf.
     * 
     * @param command
     *            Callback der nach der letzten Aktion oder bei Abbruch
     *            aufgerufen wird.
     */
    public final void exit(final Context context, final Command command)
    {
        command.execute();
    }
}
