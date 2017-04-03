package dimak888.calcappdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    EditText inputs;
    Button goButton;
    TextView result;
    ArbeitenRPN schnelle = new ArbeitenRPN();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputs = (EditText) findViewById(R.id.input);
        goButton = (Button) findViewById(R.id.go_button);
        result = (TextView) findViewById(R.id.result);
        goButton.setOnClickListener(viewClickListener);
    }

    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String exp = inputs.getText().toString();
            exp = exp.replace("\\,", "\\.").replaceAll("[^\\.\\^\\*\\+\\-\\d/\\s]", "");
            schnelle.err = "";
            if (exp.isEmpty()){
                result.setText("Введите выражение.");
            }
            else {
                result.setText(schnelle.outputResult(exp));
            }
            }
    };
}