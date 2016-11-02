package com.zhi.studentservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Administrator on 2016/11/2.
 */
public class StudentService extends Service{
    private static final String names[] = {"张小娴", "孟静娴", "浣碧", "流朱", "壁石", "孙妙清",
            "颂芝", "松子", "小允子", "玉娆"};

    private IBinder binder = new StudentBinder();

    public String query(int no) {
        if(no > 0 && no < 10){
            return names[no-1];
        }
        return "";
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class StudentBinder extends Binder implements IStudent{
        @Override
        public String getName(int no) {
            return query(no);
        }
    }

}
