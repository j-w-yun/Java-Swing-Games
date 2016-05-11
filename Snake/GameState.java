import java.util.*;
import java.awt.*;

/**
*	@author Jaewan Yun (Jay50@pitt.edu)
*	@version 1.0.0
*/

// Trust me, java. Let me do my thang
@SuppressWarnings("unchecked")
public class GameState
{
	private volatile HashMap state;
	// Reference holder
	private Key[][] keys;

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public GameState(int x, int y)
	{
		state = new HashMap();
		keys = new Key[x][y];
		for(int j = 0; j < x; j++)
		{
			for(int k = 0; k < y; k++)
			{
				keys[j][k] = new Key(j, k);
			}
		}
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized void add(Key key, Color value)
	{
		state.put(key, value);
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized void delete(Key key)
	{
		state.remove(key);
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized Key acquireKey(int x, int y)
	{
		return keys[x][y];
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized boolean occupied(Key key)
	{
		return state.containsKey(key);
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized int length()
	{
		return state.keySet().size();
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized Set getKeys()
	{
		return state.keySet();
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public synchronized void clear()
	{
		state.clear();
	}

}

class Key
{
	public final int x;
	public final int y;

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public Key(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}