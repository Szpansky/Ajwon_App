package com.apps.szpansky.ajwon_app.MainBrowsing;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.apps.szpansky.ajwon_app.R;

//activity for test
//TODO setup from simple activity class

public class ClientsActivity extends AppCompatActivity {
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view);

        b = getIntent().getExtras();
        long id;
        if (b != null) {
            id = b.getLong("catalogId");
        }
        addData();
    }

    private void addData() {
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
