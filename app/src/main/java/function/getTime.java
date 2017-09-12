package function;

/**
 * Created by ASUS on 2016/11/26.
 */
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class getTime
{
    public String gettime(String h)
    {

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        if(h.equals("0.5h"))
        {
                if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2)
                {
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>28)
                {
                    date-=28;
                    month+=1;
                }
                else if(date>=29)
                {
                    date-=28;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>30)
                {
                    date-=30;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }

        }
        else if(h.equals("1h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=1;
                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date > 28) {
                    date -= 28;
                    month += 1;
                } else if (date > 29) {
                    date -= 29;
                    month += 1;
                }
                if (month >12) {
                    month -= 12;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=1;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>30)
                {
                    date-=30;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=1;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=11;
                    year+=1;
                }
            }
        }
        else if(h.equals("1.5h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                minute += 30;
                hour+=1;
                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >12) {
                    month -= 12;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=1;
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>30)
                {
                    date-=30;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                minute += 30;
                hour+=1;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("2h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=2;
                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month > 12) {
                    month -= 12;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=2;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=2;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=2;
                    year+=1;
                }
            }
        }
        else if(h.equals("2.5h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=2;
                minute+=30;
                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >= 12) {
                    month -= 11;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=2;
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=2;
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("3h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=3;

                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month > 12) {
                    month -= 12;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=3;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=3;

                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("3.5h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=3;
                minute+=30;
                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month > 12) {
                    month -= 12;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=3;
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=3;
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("4h"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=4;

                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >12) {
                    month -= 11;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=4;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=4;

                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("5分钟以内"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                minute+=5;

                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >12) {
                    month -= 11;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                minute+=5;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                minute+=5;

                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals( "15分钟以内"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                minute+=15;

                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >12) {
                    month -= 11;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                minute+=15;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                minute+=15;

                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("30分钟以内"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                minute+=30;

                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >12) {
                    month -= 11;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                minute+=30;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                minute+=30;

                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }
        else if(h.equals("一个小时以内"))
        {
            if((year % 4 == 0 && ( year % 100 != 0 || year % 400 == 0))&&month==2) {
                hour+=1;

                if (minute >= 60) {
                    minute -= 60;
                    hour += 1;
                }
                if (hour >= 24) {
                    hour -= 24;
                    date += 1;
                }
                if (date >= 28) {
                    date -= 27;
                    month += 1;
                } else if (date >= 29) {
                    date -= 28;
                    month += 1;
                }
                if (month >12) {
                    month -= 11;
                    year += 1;
                }
            }
            else if(month==4||month==6||month==9||month==11)
            {
                hour+=1;
                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>=30)
                {
                    date-=29;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
            else
            {
                hour+=1;

                if(minute>=60)
                {
                    minute-=60;
                    hour+=1;
                }
                if(hour>=24)
                {
                    hour-=24;
                    date+=1;
                }
                if(date>31)
                {
                    date-=31;
                    month+=1;
                }
                if(month>12)
                {
                    month-=12;
                    year+=1;
                }
            }
        }





        /*System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);*/
       // return hehe;
        return  new String(year + "-"+ month + "-" + date +  "-" +hour + ":"+minute + ":" + second);
    }
    public int gethour()
    {
        Calendar c = Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }
    /*public long getTimeStamp()
    {
        return System.currentTimeMillis()/1000;//用于获取时间戳
    }
    public static void main(String[] args)
    {
        getTime time=new getTime();
        time.gettime();
    }*/
}
