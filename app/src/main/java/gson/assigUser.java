package gson;

import android.app.UiAutomation;

/**
 * Created by ASUS on 2016/11/23.
 */

public class assigUser
{
    private Integer assigID;
    private Integer uID;
    private int assigM;
    private Object uT;
    public assigUser(){}
    public assigUser(Integer assigID,Integer uID,int assigM,Object uT)
    {
        super();
        this.assigID=assigID;
        this.uID=uID;
        this.assigM=assigM;
        this.uT=uT;
    }
    public Integer getAssigID()
    {
        return assigID;
    }
    public void setAssigID(Integer assigID)
    {
        this.assigID=assigID;
    }
    public Integer getuID()
    {
        return uID;
    }
    public void setuID(Integer uID)
    {
        this.uID=uID;
    }
    public int getAssigM()
    {
        return  assigM;
    }
    public void setAssigM(int assigM)
    {
        this.assigM=assigM;
    }
    public Object getuT()
    {
        return uT;
    }
    public void setuT(Object uT)
    {
        this.uT=uT;
    }

}
