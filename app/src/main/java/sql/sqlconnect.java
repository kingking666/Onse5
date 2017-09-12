package sql;

/**
 * Created by ASUS on 2016/11/18.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class sqlconnect
{
    // 表示定义数据库的用户名
    private final String USERNAME = "root";
    // 定义数据库的密码
    private final String PASSWORD = "wt705705";
    // 定义数据库的驱动信息
    private final String DRIVER = "com.mysql.jdbc.Driver";
    // 定义访问数据库的地址
    private final String URL = "jdbc:mysql://583261b03b67f.gz.cdb.myqcloud.com:13510/test";
    // 定义数据库的链接
    private Connection connection;
    // 定义sql语句的执行对象
    private PreparedStatement pstmt;
    // 定义查询返回的结果集合
    private ResultSet resultSet;
    public sqlconnect() {
        try {
            Class.forName(DRIVER);
            System.out.println("注册驱动成功!!");
        } catch (Exception e) {}
    }

    // 定义获得数据库的链接
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("数据库连接成功！！");
        } catch (SQLException e)
        {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }
    public void closeConnection() // 关闭数据库 关闭对象，释放句柄
    {
        System.out.println("Close connection to database..");
        try {
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Close connection successful");
    }
    /*完成对数据库的表的添加删除和修改的操作*/
    public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException
    {
        boolean flag = false;
        int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        if (params != null && !params.isEmpty())
        {
            for (int i = 0; i < params.size(); i++)
            {
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        return flag;
    }

    /*查询返回单条记录*/
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty())
        {
            for (int i = 0; i < params.size(); i++)
            {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();// 返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();// 获得列的名称1,2,3
        while (resultSet.next()) {
            for (int i = 0; i < col_len; i++)
            {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) //当列是非空但没输入数据时
                {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        return map;
    }

    /*查询返回多行记录*/
    public List<Map<String, Object>> findMoreResult(String sql,List<Object> params) throws SQLException
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty())
        {
            for (int i = 0; i < params.size(); i++)
            {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next())
        {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++)
            {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null)
                {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }
}




