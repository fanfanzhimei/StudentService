package com.zhi.studentservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 开启服务 根据学号查询学生姓名
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private IStudent iStudent;
    private ServiceConnection conn = new StudentServiceConnection();

    private EditText mEtNo;
    private Button mBtnQuery;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtNo = (EditText) findViewById(R.id.et_no);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mTvResult = (TextView) findViewById(R.id.tv_result);

        mBtnQuery.setOnClickListener(this);

        Intent intent = new Intent(MainActivity.this, StudentService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                query();
                break;
        }
    }

    private void query() {
        Editable editable = mEtNo.getText();
        if (null == editable || (null != editable && "".equals(editable.toString().trim()))) {
            Toast.makeText(MainActivity.this, R.string.str_should_input, Toast.LENGTH_SHORT).show();
            return;
        }

        int no = Integer.valueOf(editable.toString().trim());

        String name = iStudent.getName(no);
        if("".equals(name)){
            Toast.makeText(MainActivity.this, R.string.str_have_not_result, Toast.LENGTH_SHORT).show();
            return;
        }
        mTvResult.setText(name);
    }

    public class StudentServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iStudent = (IStudent) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iStudent = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}