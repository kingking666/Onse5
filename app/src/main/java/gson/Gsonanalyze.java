package gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * Created by ASUS on 2016/11/23.
 */
public class Gsonanalyze
{
    List list = new ArrayList();
    public String Gsonanalysis(List<Map<String, Object>> list) throws SQLException
    {
        //创建一个Gson对象
        Gson gson = new Gson();
        this.list = list;
        //把集合对象转换成Json字符串
        String jsonStr = gson.toJson(this.list);
        return jsonStr;

    }
    public Iterator getword(String jsonStr)
    {
        //创建一个JsonParser
        JsonParser parser = new JsonParser();
        //通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
        JsonElement el = parser.parse(jsonStr);
        //把JsonElement对象转换成JsonArray
        JsonArray jsonArray = null;
        if (el.isJsonArray())
        {
            jsonArray = el.getAsJsonArray();
        }
        return jsonArray.iterator();
    }
    public JsonArray getarray(String jsonStr)
    {
        //创建一个JsonParser
        JsonParser parser = new JsonParser();
        //通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
        JsonElement el = parser.parse(jsonStr);
        //把JsonElement对象转换成JsonArray
        JsonArray jsonArray = null;
        if (el.isJsonArray())
        {
            jsonArray = el.getAsJsonArray();
        }
        return jsonArray;
    }
}

