package name.pehl.taputapu.action.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple context for sharing data between {@link ChainedAction}s.
 * 
 * @author $Author$
 * @version $Date$ $Revision:
 *          81886 $
 */
public class Context
{
    private Map<String, Object> data;


    public Context()
    {
        data = new HashMap<String, Object>();
    }


    @SuppressWarnings("unchecked")
    public <T> T get(String key)
    {
        return (T) data.get(key);
    }


    public <T> void put(String key, T value)
    {
        data.put(key, value);
    }
}
