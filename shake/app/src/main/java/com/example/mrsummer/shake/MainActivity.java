package com.example.mrsummer.shake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    //sensor管理器
    private SensorManager sensorManager;
    //震动
    private Vibrator mVibrator;
    private TextView start;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;


    @SuppressLint("WrongConstant")
    public void stop(View view){
        mediaRecorder.stop();
        start.setVisibility(View.GONE);
        mediaRecorder.release();//释放资源
        Toast.makeText(MainActivity.this, "录制完成", 0).show();
    }

    public void play(View view){
        try {
            //这个是录音的存储位置和名字
            String path= Environment.getExternalStorageDirectory().
                    getAbsolutePath()+"/bb.amr";
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=(TextView) findViewById(R.id.start);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mVibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        mediaPlayer=new MediaPlayer();
        //通过MediaRecorder录制音频
        //1.创建
        mediaRecorder=new MediaRecorder();
        //2.调用MediaRecorder对象的方法来设置声音来源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //3.设置录制的音频格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //4.设置编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //5.设置保存路径
        mediaRecorder.setOutputFile
                (Environment.getExternalStorageDirectory().
                        getAbsolutePath()+"/bb.amr");
        //6.进入准备录制的状态
        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        //传感器的绑定
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //传感器的解除绑定
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @SuppressLint({"ShowToast", "WrongConstant"})
    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                if(Math.abs(event.values[0])>15||Math.abs(event.values[1])>15||Math.abs(event.values[2])>15){
                    mVibrator.vibrate(100);
                    System.out.println("111111111111111111111");
                    start.setText("开始录制中...");
                    System.out.println("222222222222222");
                    Toast.makeText(MainActivity.this, "开始录制", 0).show();
                    mediaRecorder.start();
                }
                break;
            default:
                break;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }


}
