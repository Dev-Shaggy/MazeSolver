package mechanics;

import java.util.ArrayList;
import java.util.List;

public class Path {
    
    private final List<Point> way;
    private boolean end;
    
    public Path(Point p)
    {
        way = new ArrayList<>();
        way.add(p);
        end=false;
    }
    public Point GetNode()
    {
        return way.get(0);
    }
    public Point GetLast()
    {
        return way.get(way.size()-1);
    }
    public Point GetBody(int i)
    {
        return way.get(i);
    }
    public int GetLenght()
    {
        return way.size();
    }
    public void AddStep(Point p)
    {
        way.add(p);
    }
    public boolean IsEnd()
    {
        return end;
    }
    public void EndWay()
    {
        end=true;
    }

}
