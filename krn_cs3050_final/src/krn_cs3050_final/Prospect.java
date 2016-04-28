/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krn_cs3050_final;

/**
 *
 * @author Nathan
 */
public class Prospect 
{
    private int start;
    private int end;
    private int increase;
    
    Prospect()
    {
        start = 0;
        end = 0;
        increase = 0;
    }
    Prospect(int start)
    {
        this.start = start;
        end = 0;
        increase = 0;
    }
    
    public int getStart()
    {
        return start;
    }
    public void setStart(int num)
    {
        start = num;
    }
    public int getEnd()
    {
        return end;
    }
    public void setEnd(int num)
    {
        end = num;
    }
    public int getIncrease()
    {
        return increase;
    }
    public void setIncrease(int num)
    {
        increase = num;
    }
    public void add()
    {
        start = 0;
        end = 0;
        increase = 0;
    }
}
