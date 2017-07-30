package mapp.com.sg.moiepicer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class History extends AppCompatActivity {

    ImageButton chevron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        chevron = (ImageButton) findViewById(R.id.chevron);
        chevron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chevron.setBackgroundResource(R.drawable.chevdown);
            }
        });

    }





}
