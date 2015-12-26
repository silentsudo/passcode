package in.ashish29agre.passcodesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import in.ashish29agre.passcode.PasscodeCallback;
import in.ashish29agre.passcode.PasscodeView;

public class MainActivity extends AppCompatActivity {

    private PasscodeView passcodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

    }

    private void initViews() {
        passcodeView = (PasscodeView) findViewById(R.id.passcode_view);
        passcodeView.setPassCodeCount(4);
        passcodeView.setDrawableSize(48);
        passcodeView.setPasscodeItemMargin(8);
        passcodeView.clearAndShow();
        passcodeView.setPasscodeCallback(new PasscodeCallback() {
            @Override
            public void onComplete(CharSequence sequence) {
                if(sequence.equals("4728")) {
                    Toast.makeText(MainActivity.this, "Passcode verified", Toast.LENGTH_SHORT).show();
                    passcodeView.hideSoftKeyboard();
                } else {
                    Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
