package com.example.network.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.network.activity.SocketActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * MessageTransmit
 *
 * @author lao
 * @date 2019/5/26
 */
public class MessageTransmit implements Runnable{
    private static final String TAG = "MessageTransmit";
    private static final String SOCKET_IP = "120.24.62.225";        //socket服务器IP和端口
    private static final int SOCKET_PORT = 51000;
    private BufferedReader mReader = null;          //缓存读取器对象
    private OutputStream mWriter = null;        //输出流对象

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            //命令套接字连接指定地址的指定端口
            socket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
            //根据套接字的输入流，构建缓存读取器
            mReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //获得套接字的输出流
            mWriter = socket.getOutputStream();
            //启动一条子线程来读取服务器的返回数据
            new RecvThread().start();
            //为当前线程初始化消息队列
            Looper.prepare();
            //让线程的消息队列开始运行，之后就可以接受消息了
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建一个发送处理器对象,让App向后台服务器发送消息
    public Handler mSendHandler = new Handler() {

      @Override
        public void handleMessage(Message msg) {
          Log.d(TAG, "handleMessage: " + msg.obj);
          //换行符相当于回车键，表示我写好的发送出去
          String send_msg = msg.obj.toString() + "\n";
          try {
              //往输出流对象中写入数据
              mWriter.write(send_msg.getBytes("UTF-8"));
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
    };


    //定义消息接受子线程，让App从后台服务器接受消息
    private class RecvThread extends Thread {
        @Override
        public void run() {
            try {
                String content;
                //读取到来自服务器的数据
                while ((content = mReader.readLine()) != null) {
                    //获得默认的一个消息对象
                    Message msg = Message.obtain();
                    msg.obj = content;
                    //通知SocketActivity收到消息
                    SocketActivity.mHandler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
