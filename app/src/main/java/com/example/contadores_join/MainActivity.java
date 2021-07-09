package com.example.contadores_join;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private TextView tiempo;
    private Button btnIniciar;
    private Button btnParar;
    private Button btnContinuar;
    private Thread t1;
    private RelojDigital r;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiempo = findViewById(R.id.tiempo);
        btnIniciar = findViewById(R.id.buttonIniciar);
        btnParar = findViewById(R.id.buttonParar);
        btnContinuar = findViewById(R.id.buttonContinuar);
        progressBar = findViewById(R.id.progress);

        btnIniciar.setOnClickListener(v -> {
            this.btnIniciar.setEnabled(false);
            this.btnContinuar.setEnabled(false);
            this.btnParar.setEnabled(true);
            r = new RelojDigital(0,0,0);
            r.addObserver(this);
            t1 = new Thread(r);
            t1.start();
            progressBar.setVisibility(View.VISIBLE);

        });

        btnParar.setOnClickListener(v -> {
            btnContinuar.setEnabled(true);
            btnIniciar.setEnabled(true);
            this.btnIniciar.setEnabled(true);
            t1.interrupt();
            progressBar.setVisibility(View.INVISIBLE);
        });

        btnContinuar.setOnClickListener( v->{
            btnContinuar.setEnabled(false);
            btnIniciar.setEnabled(false);
            r = new RelojDigital(r.getHoras(),r.getMinutos(), r.getSegundos());
            r.addObserver(this);
            t1 = new Thread(r);
            t1.start();
            progressBar.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void update(Observable o, Object arg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tiempo.setText(String.valueOf(arg));
            }
        });

    }
}