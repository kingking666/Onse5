package gson;

public class displayAssig {
	private Integer uID;
	private String uN;
	private String uTN;
	private Integer assigID;//任务ID
	private String assigT ;//任务类型
	private String assigCM;//任务具体信息
	private String assigRM;//任务大致信息
	private Object assigTi;//任务时限
	private String assigCPN;//发布者的联系电话


	public displayAssig() {}
	public displayAssig(Integer uID,String uN,String uTN,Integer assigID,String assigT,String assigCM,String assigRM,Object assigTi,String assigCPN)
	{
		super();
		this.uID=uID;
		this.uN=uN;
		this.uTN=uTN;
		this.assigID = assigID;
		this.assigT = assigT;
		this.assigCM = assigCM;
		this.assigRM=assigRM;
		this.assigTi=assigTi;
		this.assigCPN=assigCPN;

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
	public String getuTN()
	{
		return uTN;
	}
	public void setuTN(String uTN)
	{
		this.uTN=uTN;
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
	public void setAssigRM(String assigRM)
	{
		this.assigRM = assigRM;
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
}