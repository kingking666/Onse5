package gson;

/**
 * Created by ASUS on 2016/11/23.
 */

public class t_user
{
    private int count;
    private Integer uID;
    private String uSID;
    private String uCN;
    private String uCPN;
    private String uTN;
    private String uE;
    private String uPw;
    private String uN;
    private int uC;
    private Object uP;
    private String uS;
    private String uQ;
    private boolean uESt;
    private Object uT;
    private Object token;
    public t_user(){}
    public t_user(int count,Integer uID,String uSID,String uCN,String uCPN,String uTN,String uE,String uPw,String uN,int uC,Object uP,String uS,String uQ,boolean uESt,Object uT,Object token)
    {
        this.count=count;
        this.uID=uID;
        this.uSID=uSID;
        this.uCN=uCN;
        this.uCPN=uCPN;
        this.uTN=uTN;
        this.uE=uE;
        this.uPw=uPw;
        this.uN=uN;
        this.uC=uC;
        this.uP=uP;
        this.uS=uS;
        this.uQ=uQ;
        this.uESt=uESt;
        this.uT=uT;
        this.token=token;
    }
    public int getcount()
    {
        return count;
    }
    public void setcount()
    {
        this.count=count;
    }
    public Integer getuID()
    {
        return  uID;
    }
    public void setuID(Integer uID)
    {
        this.uID=uID;
    }
    public String getuSID()
    {
        return  uSID;
    }
    public void setuSID(String uSID)
    {
        this.uSID=uSID;
    }
    public String getuCN(){
        return uCN;
    }
    public void setuCN(String uCN)
    {
        this.uCN=uCN;
    }
    public String getuCPN(){
        return uCPN;
    }
    public void setuCPN(String uCPN)
    {
        this.uCPN=uCPN;
    }
    public String getuTN()
    {
        return uTN;
    }
    public void setuTN(String uTN)
    {
        this.uTN=uTN;
    }
    public String getuE()
    {
        return uE;
    }
    public void setuE(String uE)
    {
        this.uE=uE;
    }
    public String getuPw()
    {
        return uPw;
    }
    public void setuPw(String uPw)
    {
        this.uPw=uPw;
    }
    public String getuN()
    {
        return uN;
    }
    public void setuN(String uN)
    {
        this.uN=uN;
    }
    public int getuC()
    {
        return uC;
    }
    public void setuC(int uC)
    {
        this.uC=uC;
    }
    public Object getuP()
    {
        return uP;
    }
    public void setuP(String uP)
    {
        this.uP=uP;
    }
    public String getuS()
    {
        return uS;
    }
    public void setuS(String uS)
    {
        this.uS=uS;
    }
    public String getuQ()
    {
        return uQ;
    }
    public void setuQ(String uQ)
    {
        this.uQ=uQ;
    }
    public boolean getuESt(){return uESt;}
    public void setuESt(boolean uESt){this.uESt=uESt;}
    public Object getuT(){return uT;}
    public void setuT(Object uT){this.uT=uT;}
    public Object gettoken(){return token;}
    public void settoken(Object token){this.token=token;}
}
