package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;

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

    public void verificarMovimento(String movimento){
        HttpReturn mov = new HttpReturn(movimento);
        try {
            mov.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //funcao para retorno a camera
    public void retornoCAM(View v){
        super.onBackPressed();
    }

    //funçao mover para cima
    public void upmov(View v){
        verificarMovimento("cima");
    }

    //funçao mover para esquerda
    public void leftmov(View v){
        verificarMovimento("esquerda");
    }

    //funçao mover para direita
    public void rightmov(View v){
        verificarMovimento("direita");
    }

    //funçao mover para baixo
    public void downmov(View v){
        verificarMovimento("baixo");
    }

    public void confirmIP(View v){
        textView.setText(editText.getText());
        ip = textView.getText();
        System.out.println("IP confirmado: "+ip);
    }

}