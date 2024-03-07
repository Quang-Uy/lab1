package fpoly.edu.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataIntent();
        setTitleToolbar();
    }

    private void getDataIntent() {
        String strPhoneNumber = getIntent().getStringExtra("phone_number");

        TextView tv_user = findViewById(R.id.tv_user);
        tv_user.setText(strPhoneNumber);
    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Activity");
        }
    }
}