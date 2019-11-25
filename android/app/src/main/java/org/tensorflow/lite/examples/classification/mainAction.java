package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class mainAction extends AppCompatActivity {

    //herdam de view por isso eh possivel o casting
    private Button buttonConfirm;
    private EditText editText;
    private TextView textView;
    private CharSequence ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);

        //att 25/11

        buttonConfirm = (Button) findViewById(R.id.button6); //botao de confirmar o que foi escrito no text
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);



    }

    //funcao para retorno a camera
    public void retornoCAM(View v){
        super.onBackPressed();
        System.out.println("Ola galera zou matheus moredi");
    }

    //funçao mover para cima
    public void upmov(View v){
        textView.setText("Foi pra cima");
    }

    //funçao mover para esquerda
    public void leftmov(View v){
        textView.setText("Foi pra esquerda");
    }

    //funçao mover para direita
    public void rightmov(View v){
        textView.setText("Foi pra direita");
    }

    //funçao mover para baixo
    public void downmov(View v){
        textView.setText("Foi pra baixo");
    }

    public void confirmIP(View v){
        textView.setText(editText.getText());
        ip = textView.getText();
        System.out.println("IP confirmado: "+ip);
    }
}

