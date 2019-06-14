/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanics;

/**
 *
 * @author borys
 */
public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(Point p)
    {
        x=p.x;
        y=p.y;
    }
    public boolean isEaual(Point p)
    {
        return x==p.x && y==p.y;
    }
}
