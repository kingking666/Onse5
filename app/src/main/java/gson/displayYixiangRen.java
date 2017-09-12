package gson;

public class displayYixiangRen {

	    private Integer uID;
	    private String uN;
	    private int assigM;
	    private Object uT;
	    public displayYixiangRen(){}
	    public displayYixiangRen(Integer uID,String uN,int assigM,Object uT)
	    {
	        super();
	        this.uID=uID;
	        this.uN=uN;
	        this.assigM=assigM;
	        this.uT=uT;
	    }
	    public Integer getuID()
	    {
	    	return uID;
	    }
	    public void setuID(Integer uID)
	    {
	    	this.uID=uID;
	    }
	    public String getuN()
	    {
	    	return uN;
	    }
	    public void setuN(String uN)
	    {
	    	this.uN=uN;
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


