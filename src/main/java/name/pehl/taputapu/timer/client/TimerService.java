package name.pehl.taputapu.timer.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

/**
 * Class for managing {@link Timer} instances. Timer instances can be started
 * using the methods {@link #schedule(int, Command)} and
 * {@link #repeat(int, Command)}. These methods return a
 * {@linkplain TimerHandle handle} which can be used later on to stop the timer
 * using the method {@link #cancel(TimerHandle)}.
 * <p>
 * The code to be executed must be specified using a {@link Command} instance:
 * 
 * <pre>
 * TimerService timerService = ...;
 * TimerHandle handle = timerService.repeat(5000, new Command()
 * {
 *     {@code @}Override
 *     public void execute()
 *     {
 *         ...
 *     }
 * });
 * ...
 * timerService.cancel(handle);
 * </pre>
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          82669 $
 */
public final class TimerService
{
    private Map<TimerHandle, Timer> timers;


    public TimerService()
    {
        timers = new HashMap<TimerHandle, Timer>();
    }


    /**
     * Schedules a timer to elapse in the future. The code to be executed must
     * be specified using a {@link Command} instance.
     * 
     * @see Timer#schedule(int)
     * @param delayMillis
     *            how long to wait before the timer elapses, in milliseconds
     * @param command
     *            the code to be executed
     * @return the unique timer handle
     */
    public TimerHandle schedule(final int delayMillis, final Command command)
    {
        return scheduleInternal(delayMillis, command, false);
    }


    /**
     * Schedules a timer that elapses repeatedly. The code to be executed must
     * be specified using a {@link Command} instance.
     * 
     * @see Timer#scheduleRepeating(int)
     * @param periodMillis
     *            how long to wait before the timer elapses, in milliseconds,
     *            between each repetition
     * @param command
     *            the code to be executed
     * @return the unique timer handle
     */
    public TimerHandle repeat(final int periodMillis, final Command command)
    {
        return scheduleInternal(periodMillis, command, true);
    }


    private TimerHandle scheduleInternal(final int interval, final Command command, final boolean repeat)
    {
        if (command != null)
        {
            TimerHandle handle = new TimerHandle();
            Timer timer = new Timer()
            {
                @Override
                public void run()
                {
                    command.execute();
                }
            };
            timers.put(handle, timer);
            if (repeat)
            {
                timer.scheduleRepeating(interval);
            }
            else
            {
                timer.schedule(interval);
            }
            return handle;
        }
        return null;
    }


    /**
     * Cancels the timer specified by <code>handle</code>.
     * 
     * @see Timer#cancel()
     * @param handle
     *            The handle which was returned by
     *            {@link #schedule(int, Command)} or
     *            {@link #repeat(int, Command)}.
     * @return the canceled timer
     */
    public Timer cancel(TimerHandle handle)
    {
        Timer timer = timers.remove(handle);
        if (timer != null)
        {
            timer.cancel();
        }
        return timer;
    }


    /**
     * Cancels all timer managed by this TimerService.
     * 
     * @see Timer#cancel()
     */
    public void cancelAll()
    {
        for (Iterator<Entry<TimerHandle, Timer>> iter = timers.entrySet().iterator(); iter.hasNext();)
        {
            Entry<TimerHandle, Timer> entry = iter.next();
            entry.getValue().cancel();
            iter.remove();
        }
    }
}
