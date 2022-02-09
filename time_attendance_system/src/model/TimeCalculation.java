package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeCalculation {

    public long timecal(String begintime, String finishtime){
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        Date t1 = null;
        Date t2 = null;
        long diffhour = 0;

        try {
            //String型のデータをDate型に変換し時間を計算する
            t1 = sdf.parse(begintime);
            t2 = sdf.parse(finishtime);

            long diff = t2.getTime() - t1.getTime();

            diffhour = diff / (60*1000);

        } catch (ParseException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return diffhour;
    }

}
