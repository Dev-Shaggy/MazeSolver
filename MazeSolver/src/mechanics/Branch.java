/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author borys
 */
public class Branch {
    
    public List<Path> Paths;
    public boolean finished;
    
    public Branch()
    {
        Paths = new ArrayList<>();
        finished= false;
    }
    public void AddPath(Point p)
    {
        Paths.add(new Path(p));
    }
    public void EndBranch()
    {
        finished=true;
    }
}
