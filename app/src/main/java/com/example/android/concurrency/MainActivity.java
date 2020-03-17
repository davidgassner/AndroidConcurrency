package com.example.android.concurrency;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";
    private static final String MESSAGE_KEY = "message_key";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    //TODO: create a Handler Activity and make it a field of the MainActivity so it persists for the life of the MainActivity
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mLog.setText(R.string.lorem_ipsum);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //TODO: called whenever handler receives a message
                Bundle bundle = msg.getData();
                String message = bundle.getString(MESSAGE_KEY);
                log(message);
                displayProgressBar(false);

            }
        };
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {

        log("Running code");
        displayProgressBar(true);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: starting thread for 4 seconds");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //TODO: create an instance of message
                Message message = new Message();
                //TODO: Bundle is a collection of key-value pairs like a map
                Bundle bundle = new Bundle();
                bundle.putString(MESSAGE_KEY, "thread is complete");
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    //  Clear the output, called from the onClick event in the layout file
    public void clearOutput(View v) {
        mLog.setText("");
        scrollTextToEnd();
    }

    //  Log output to logcat and the screen
    private void log(String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @SuppressWarnings("unused")
    private void displayProgressBar(boolean display) {
        if (display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}