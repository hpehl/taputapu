package name.pehl.taputapu.timer.client;

/**
 * Simple handle used in {@link TimerService}.
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          82669 $
 */
public class TimerHandle
{
    private static int nextId = 0;
    private final long id;


    /**
     * Construct a new instance of this class and creates an unique identifier.
     */
    public TimerHandle()
    {
        this.id = nextId;
        nextId++;
    }


    /**
     * Based on {@link #getId()}.
     * 
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        TimerHandle other = (TimerHandle) obj;
        if (id != other.id)
        {
            return false;
        }
        return true;
    }


    /**
     * Based on {@link #getId()}.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }


    /**
     * @return the unique identifier of this handle.
     */
    public long getId()
    {
        return id;
    }
}
