package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class mainAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);
    }
    //funcao teste
    public void mathwe(View v){
        Intent intent = new Intent (mainAction.this, CameraActivity.class);
        startActivity(intent);

        System.out.println("Ola galera zou matheus moredi");
    }
}

    public void upmov(View v){
        System.out.println("Foi pra cima");
    }

    public void leftmov(View v){
        System.out.println("Foi pra esquerda");
    }

    public void rightmov(View v){
        System.out.println("Foi pra direita");
    }

    public void downmov(View v){
        System.out.println("Foi pra baixo");
    }