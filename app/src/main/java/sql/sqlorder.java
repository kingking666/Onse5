package sql;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import gson.Gsonanalyze;
import gson.t_user;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Iterator;

public class sqlorder
{
    String sql = "";
    List<Object> params = new ArrayList<Object>();
    public void ZhuCe(Object Tel,Object Password,Object uT)//用户注册向数据库添加信息
    {
        sql="insert into t_user (uCPN,uPw,uT,uC,uSt) values(?,?,?,?,?)";
        params.add(Tel);
        params.add(Password);
        params.add(uT);
        int uC=100;//设置初始信誉度
        params.add(uC);
        boolean uSt=true;
        params.add(uSt);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public boolean uCPNisExist(Object uCPN) throws SQLException//验证此手机号是否已经被注册
    {
        boolean flag=false;
        sql="select * from t_user where uCPN=?";
        params.add(uCPN);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        Map<String, Object> map = new HashMap<String, Object>();;
        map = sqlconnect.findSimpleResult(sql,params);
        if(map!=null&&!map.isEmpty())
        {
            flag=true;
        }
        sqlconnect.closeConnection();
        return flag;
    }
    public boolean uQQisExist(Object uE) throws SQLException//验证此邮箱是否已经被注册
    {
        boolean flag=false;
        sql="select * from t_user where uE=?";
        params.add(uE);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        Map<String, Object> map = new HashMap<String, Object>();;
        map = sqlconnect.findSimpleResult(sql,params);
        if(map!=null&&!map.isEmpty())
        {
            flag=true;
        }
        sqlconnect.closeConnection();
        return flag;
    }
    public boolean Login(String uCPN,String password)throws SQLException//验证登录账号与密码是否对应
    {
        boolean flag=false;
        sql="select * from t_user where uCPN=? and uPw=?";
        params.add(uCPN);
        params.add(password);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        Map<String, Object> map = new HashMap<String, Object>();;
        map = sqlconnect.findSimpleResult(sql,params);
        if(map!=null&&!map.isEmpty())
        {
            flag=true;
        }
        sqlconnect.closeConnection();
        return flag;
    }
    public void updateLogin(Object uT,Object uCPN)//用户登录更新时间戳
    {
        sql="update t_user set uT=? where uCPN=?";
        params.add(uT);
        params.add(uCPN);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void CheckShenfen(Object uCPN)//确定身份认证
    {
        sql="update t_user set uESt='true' where uCPN=?";
        params.add(uCPN);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void ChangeFormation(Object uSID,Object uCN,Object uTN,Object uE,Object uN,Object uS,Object uQ,Object uCPN)//完善用户的其他详细信息
    {
        sql="update t_user set uSID=?,uCN=?,uTN=?,uE=?,uN=?,uS=?,uQ=? where uCPN=?";
        params.add(uSID);
        params.add(uCN);
        params.add(uTN);
        params.add(uE);
        params.add(uN);
        params.add(uS);
        params.add(uQ);
        params.add(uCPN);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void addbasicFormation(Object uN,Object uS,Object uQ,Object uCPN)//完善用户的基础详细信息
    {
        sql="update t_user set uN=?,uS=?,uQ=? where uCPN=?";
        params.add(uN);
        params.add(uS);
        params.add(uQ);
        params.add(uCPN);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public List<Map<String, Object>> allFormation(Object uCPN) throws SQLException//显示个人信息(个人中心）
    {
        Integer uID = new sqlorder().finduID(uCPN);
        sql="select uSID,uCN,uTN,uE,uN,uP,uS,uQ,uCPN,uC from t_user where uID=?";
        params.add(uID);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql,params);
        sqlconnect.closeConnection();
        return list;
    }
    public Integer finduID(Object uCPN) throws SQLException//查询用户ID
    {
        sql="select uID from t_user where uCPN= ?";
        params.add(uCPN);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        String jsonSTR=new Gsonanalyze().Gsonanalysis(list);
        Iterator it = new Gsonanalyze().getword(jsonSTR);
        gson.t_user field = new t_user();
        Gson gson = new Gson();
        while (it.hasNext()) {
            JsonElement ee = (JsonElement) it.next();
            field = gson.fromJson(ee, t_user.class);
        }
        sqlconnect.closeConnection();
        return field.getuID();
    }
    public String finduPw(Object uCPN)throws SQLException//查询用户密码
    {
        sql="select uPw from t_user where uCPN=?";
        params.add(uCPN);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        String jsonSTR=new Gsonanalyze().Gsonanalysis(list);
        Iterator it = new Gsonanalyze().getword(jsonSTR);
        gson.t_user field = new t_user();
        Gson gson = new Gson();
        while (it.hasNext()) {
            JsonElement ee = (JsonElement) it.next();
            field = gson.fromJson(ee, t_user.class);
        }
        sqlconnect.closeConnection();
        return field.getuPw();
    }
    public void FabuRenwu(Object assigT,Object assigCM,Object assigRM,Object assigTi,Object assigCPN,Object uCPN)//参数为任务单的信息
    {
        try{
            Integer assigUserID = new sqlorder().finduID(uCPN);
            sql="insert into t_assig(assigT,assigCM,assigRM,assigTi,assigCPN,assigUserID,assigS) values(?,?,?,?,?,?,?)";

            params.add(assigT);
            params.add(assigCM);
            params.add(assigRM);
            params.add(assigTi);
            params.add(assigCPN);
            params.add(assigUserID );
            String  assigS="可接";
            params.add(assigS);
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public List<Map<String, Object>> allAssig(String assigS,String page) throws SQLException//查询所有未接任务
    {
        int lim=(Integer.parseInt(page)-1)*2;
        sql="select uID,uN,assigID,assigT,assigRM,assigTi,assigCPN from t_user,t_assig where t_user.uID=t_assig.assigUserID and assigS=? limit ?,15";
        params.add(assigS);
        params.add(lim);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql,params);
        sqlconnect.closeConnection();
        return list;
    }
    public List<Map<String, Object>> SoSuo(String assigT,String assigS,String page) throws SQLException//按类别搜索任务
    {
        int lim=(Integer.parseInt(page)-1)*2;
        sql="select uID,uN,assigID,assigT,assigRM,assigTi,assigCPN from t_user,t_assig where t_user.uID=t_assig.assigUserID and assigT=? and assigS=? limit ?,15";
        params.add(assigT);
        params.add(assigS);
        params.add(lim);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        sqlconnect.closeConnection();
        return list;
    }
    public List<Map<String, Object>> ChakanXiangxi(Object assigID) throws SQLException//具体查看某个单
    {
        sql="select uID,uTN,assigT,assigRM,assigTi,assigCPN from t_user,t_assig where t_user.uID=t_assig.assigUserID and assigID=?";
        params.add(assigID);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        sqlconnect.closeConnection();
        return list;
    }
    public void YiXiang(Object assigID,Object assigM,Object uT,Object uCPN)//用户有意向接单把意向者信息放入数据库
    {
        try
        {
            Integer uID = new sqlorder().finduID(uCPN);
            sql="insert into assiguser(assigID,uID,assigM,uT) values(?,?,?,?)";
            params.add(assigID);
            params.add(uID);
            params.add(assigM);
            params.add(uT);
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public boolean YiXiangExist(Object assigID,Object assigSUserID) throws SQLException//验证是否有重复意向
    {
        boolean flag=true;
        sql="select * from t_assig where assigID=? and assigSUserID=?";
        params.add(assigID);
        params.add(assigSUserID);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        Map<String, Object> map = new HashMap<String, Object>();;
        map = sqlconnect.findSimpleResult(sql,params);
        if(map!=null&&!map.isEmpty())
        {
            flag=false;
        }
        sqlconnect.closeConnection();
        return flag;
    }
    public List<Map<String, Object>> SoRen(Object assigID) throws SQLException//搜索有谁接了某个单
    {
        sql="select t_user.uID,uN,assigM,assiguser.uT from t_user,assiguser where t_user.uID=assiguser.uID and assigID=?";
        params.add(assigID);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        sqlconnect.closeConnection();
        return list;
    }
    public void XuanRen(Object assigSUserID,Object assigID)//发布者选择接单人
    {
        try
        {
            sql="update t_assig set assigSUserID=? where assigID=?";
            params.add(assigSUserID);
            params.add(assigID);
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void JingXingRenwu(Object assigID)//进行任务
    {
        sql="update t_assig set assigS='正在进行中' where assigID=?";
        params.add(assigID);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void WanchengRenwu(Object assigID)//完成任务
    {
        sql="update t_assig set assigS='已完成' where assigID=?";
        params.add(assigID);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void GuoQiRenwu(Object assigID)//过期任务
    {
        sql="update t_assig set assigS='已过时间期限' where assigID=?";
        params.add(assigID);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public void QuxiaoRenwu(Object assigID)//取消任务
    {
        sql="delete * from t_assig where assigID=?";
        params.add(assigID);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
    public List<Map<String, Object>> MindAssig(Object uCPN) throws  SQLException//查询该用户的发单记录
    {
        Integer assigUserID = new sqlorder().finduID(uCPN);
        sql="select * from t_assig where assigUserID=?";
        params.add(assigUserID);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        sqlconnect.closeConnection();
        return list;
    }
    public List<Map<String, Object>> MinegetAssig(Object uCPN) throws  SQLException//查询该用户的接单记录
    {
        Integer assigSUserID = new sqlorder().finduID(uCPN);
        sql="select * from t_assig where assigSUserID=?";
        params.add(assigSUserID);
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = sqlconnect.findMoreResult(sql, params);
        sqlconnect.closeConnection();
        return list;
    }
    public boolean  assigSisExist() throws SQLException//验证此单是否已经被接
    {
        boolean flag=false;
        sql="select * from t_assig where assigS='正在进行中' or assigS='已完成' or assigS='已过时间期限'";
        sqlconnect sqlconnect = new sqlconnect();
        sqlconnect.getConnection();
        Map<String, Object> map = new HashMap<String, Object>();;
        map = sqlconnect.findSimpleResult(sql,null);
        if(map!=null)
        {
            flag=true;
        }
        sqlconnect.closeConnection();
        return flag;
    }
    /*public void deleteAssigUser(Object j,Object assigID)//删除没有成功接单的意向人在领取表的信息
    {
        sql="delete * from assigUser where =? and assigID=?";
        params.add("j");
        params.add(assigID);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }*/
    public void changePassword(Object password,Object uCPN)//重设密码
    {
        sql="update t_user set uPw=? where uCPN=?";
        params.add(password);
        params.add(uCPN);
        try
        {
            sqlconnect sqlconnect = new sqlconnect();
            sqlconnect.getConnection();
            boolean flag = sqlconnect.updateByPreparedStatement(sql, params);
            sqlconnect.closeConnection();
        }
        catch (Exception e) {}
    }
}


   /* sqlorde m=new sqlorde();
    Iterator it=m.sosu();
    assigUser/t_assig field = null;
    Gson gson = new Gson();
while (it.hasNext()) {
        JsonElement ee = (JsonElement) it.next();
        field = gson.fromJson(ee,MyField.class);
        System.out.print(field.getId());
        System.out.print(field.getName());
        System.out.println(field.getTel());*/
