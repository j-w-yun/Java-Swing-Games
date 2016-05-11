import java.util.*;
import java.awt.*;

// Trust me, java. Let me do my thang
@SuppressWarnings("unchecked")
public class GameState
{
	private HashMap state;
	// Reference holder
	private Key[][] keys;

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

	public void add(Key key, Color value)
	{
		state.put(key, value);
	}

	public void delete(Key key)
	{
		state.remove(key);
	}

	public Key acquireKey(int x, int y)
	{
		return keys[x][y];
	}

	public boolean occupied(Key key)
	{
		return state.containsKey(key);
	}

	public int length()
	{
		return state.keySet().size();
	}

	public Set getKeys()
	{
		return state.keySet();
	}

	public void clear()
	{
		state.clear();
	}

}

class Key
{
	public final int x;
	public final int y;

	public Key(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}