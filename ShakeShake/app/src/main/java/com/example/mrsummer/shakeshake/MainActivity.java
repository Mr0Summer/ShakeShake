package com.example.mrsummer.shakeshake;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener{
        //定义界面上的两个按钮
        Button start, stop;
        //系统的音频文件
        File soundFile;
        MediaRecorder mRecorder;

        @Override
    public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //获取按钮
            start = (Button) findViewById(R.id.start);
            stop = (Button) findViewById(R.id.stop);
            //为按钮绑定监听事件
            start.setOnClickListener(this);
            stop.setOnClickListener(this);
        }

     @Override
     public void onDestroy() {

            if(soundFile != null && soundFile.exists()) {
                //停止录音
                mRecorder.stop();
                //释放资源
                mRecorder.release();
                mRecorder = null;
            }
         super.onDestroy();
     }

    @Override
    public void onClick(View source) {

            switch (source.getId())
            {
                //click start
                case R.id.start:
                    if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        Toast.makeText(MainActivity.this,"SD卡不存在，请插入SD卡！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try
                    {//创建保存录音的文件
                        soundFile = new File(Environment.getExternalStorageDirectory().getCanonicalFile()+"/sound.amr");
                        mRecorder = new MediaRecorder();
                        //设置录音声源
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        //设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        //设置声音编码格式
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mRecorder.setOutputFile(soundFile.getAbsolutePath());
                        mRecorder.prepare();
                        //开始录音
                        mRecorder.start();


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case R.id.stop:
                    if(soundFile != null && soundFile.exists())
                    {
                        //停止录音
                        mRecorder.stop();
                        //释放资源
                        mRecorder.release();
                        mRecorder = null;
                    }
                    break;

            }



    }
}


