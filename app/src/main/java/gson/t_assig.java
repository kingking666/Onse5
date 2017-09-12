package gson;

/**
 * Created by ASUS on 2016/11/21.
 */
//任务信息类
public class t_assig
{
    private Integer assigID;//任务ID
    private String assigT ;//任务类型
    private String assigCM;//任务具体信息
    private String assigRM;//任务大致信息
    private Object assigTi;//任务时限
    private String assigCPN;//发布者的联系电话
    private String assigS;//任务状态
    private Integer assigUserID ;//发布人ID
    private Integer asssigSUserID;//接单人ID
    public t_assig() {}
    public t_assig(Integer assigID,String assigT,String assigCM,String assigRM,Object assigTi,String assigCPN,String assigS,Integer assigUserID,Integer asssigSUserID)
    {
        super();
        this.assigID = assigID;
        this.assigT = assigT;
        this.assigCM=assigCM;
        this.assigRM=assigRM;
        this.assigTi=assigTi;
        this.assigCPN=assigCPN;
        this.assigS=assigS;
        this.assigUserID=assigUserID;
        this.asssigSUserID=asssigSUserID;
    }
    public Integer getAssigID()
    {
        return assigID;
    }
    public void setAssigID(Integer assigID)
    {
        this.assigID= assigID;
    }
    public String getAssigT()
    {
        return assigT;
    }
    public void setAssigT(String assigT)
    {
        this.assigT = assigT;
    }
    public String getAssigCM()
    {
        return assigCM;
    }
    public void setAssigCM(String assigCM)
    {
        this.assigCM = assigCM;
    }
    public String getAssigRM()
    {
        return assigRM;
    }
    public void setAssigRM(String assigCM)
    {
        this.assigRM = assigCM;
    }
    public Object getAssigTi()
    {
        return assigTi;
    }
    public void setAssigTi(Object assigTi)
    {
        this.assigTi=assigTi;
    }
    public String getAssigCPN()
    {
        return assigCPN;
    }
    public void setAssigCPN(String assigCPN)
    {
        this.assigCPN=assigCPN;
    }
    public String getAssigS()
    {
        return assigS;
    }
    public void setAssigS(String assigS)
    {
        this.assigS=assigS;
    }
    public Integer getAssigUserID(){return assigUserID;}
    public void setAssigUserID(Integer assigUserID){this.assigUserID=assigUserID;}
    public Integer getAsssigSUserID(){return asssigSUserID;}
    public void setAsssigSUserID(Integer asssigSUserID){this.asssigSUserID=asssigSUserID;}
}


