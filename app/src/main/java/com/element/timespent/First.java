package com.element.timespent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class First extends AppCompatActivity implements CustomResultReceiver.Receiver {

    //Views
    TextView headerTextView;
    TextView timeTextView;
    TextView timeSpentTextView;
    Button changeActivityButton;
    String timeSpentOnLastActivity;
    String timeSpentOnThisActivity;
    CustomResultReceiver resultReceiver;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        startTime = System.currentTimeMillis();

        //Fetching the views
        headerTextView = (TextView) this.findViewById(R.id.text_page_header);
        timeTextView = (TextView) this.findViewById(R.id.time_spent_text);
        timeSpentTextView = (TextView) this.findViewById(R.id.time_spent);
        changeActivityButton = (Button) this.findViewById(R.id.change_activity_button);

        headerTextView.setText("Welcome!");
        timeTextView.setText("Time spent on 2nd page:");
        changeActivityButton.setOnClickListener(changeActivityButtonListener);

        resultReceiver = new CustomResultReceiver(new Handler());
        resultReceiver.setReceiver(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            timeSpentOnLastActivity = bundle.getString("spentTime");
        } else {
            timeSpentOnLastActivity = "00 : 00 : 00";
        }

        timeSpentTextView.setText(timeSpentOnLastActivity);
    }

    private View.OnClickListener changeActivityButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent serviceIntent = new Intent(First.this, TimeSpentService.class);
            serviceIntent.putExtra("receiver", resultReceiver);
            serviceIntent.putExtra("startTime", startTime);
            startService(serviceIntent);
        }
    };

    @Override
    public void onBackPressed() {
        Intent minimize = new Intent(Intent.ACTION_MAIN);
        minimize.addCategory(Intent.CATEGORY_HOME);
        minimize.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(minimize);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        timeSpentOnThisActivity = resultData.getString("time");
        Intent intent = new Intent(First.this.getApplicationContext(), Second.class);
        intent.putExtra("timeSpent", timeSpentOnThisActivity);
        startActivity(intent);
    }
}
