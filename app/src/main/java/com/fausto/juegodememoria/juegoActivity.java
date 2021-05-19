package com.fausto.juegodememoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class juegoActivity extends AppCompatActivity {
    private static final int NUM_CASILLAS =16;

    private ImageButton b11;
    private ImageButton b12;
    private ImageButton b13;
    private ImageButton b14;
    private ImageButton b21;
    private ImageButton b22;
    private ImageButton b23;
    private ImageButton b24;
    private ImageButton b31;
    private ImageButton b32;
    private ImageButton b33;
    private ImageButton b34;
    private ImageButton b41;
    private ImageButton b42;
    private ImageButton b43;
    private ImageButton b44;
    private TextView text_puntuacion;

    //variables para el juego
    private ImageButton[] tablero;
    private int[] imagenes;
    private int imagenFondo;
    private int puntuacion;
    private int aciertos;

    private ArrayList<Integer> arrayBarajado;
    private ImageButton imagenvista;
    private int seleccion1;
    private int selecccion2;
    private boolean tableroBloqueado;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        b11 = findViewById(R.id.imageB11);
        b12 = findViewById(R.id.imageB12);
        b13 = findViewById(R.id.imageB13);
        b14 = findViewById(R.id.imageB14);
        b21 = findViewById(R.id.imageB21);
        b22 = findViewById(R.id.imageB22);
        b23 = findViewById(R.id.imageB23);
        b24 = findViewById(R.id.imageB24);
        b31 = findViewById(R.id.imageB31);
        b32 = findViewById(R.id.imageB32);
        b33 = findViewById(R.id.imageB33);
        b34 = findViewById(R.id.imageB34);
        b41 = findViewById(R.id.imageB41);
        b42 = findViewById(R.id.imageB42);
        b43 = findViewById(R.id.imageB43);
        b44 = findViewById(R.id.imageB44);
        text_puntuacion = findViewById(R.id.text_puntuacion);

        // Meter las imagenes en el array tablero
        tablero = new ImageButton[]{
                b11,
                b12,
                b13,
                b14,
                b21,
                b22,
                b23,
                b24,
                b31,
                b32,
                b33,
                b34,
                b41,
                b42,
                b43,
                b44,
        };

        //Referencia a la imagen de fondo
        imagenFondo = R.drawable.fondo;

        //Referencias a las imagenes
        imagenes = new int[]{
                R.drawable.la0,
                R.drawable.la1,
                R.drawable.la2,
                R.drawable.la3,
                R.drawable.la4,
                R.drawable.la5,
                R.drawable.la6,
                R.drawable.la7,

        };
iniciandoJuego();
    }

    public void iniciandoJuego(){
    puntuacion = 0;
    aciertos = 0;
    text_puntuacion.setText(getString(R.string.txt_puntuacion) + Integer.toString(puntuacion));
     tableroBloqueado = false;
    imagenvista = null;
    seleccion1 = -1;
    selecccion2 = -1;
        arrayBarajado = barajarImagenes();

        //Muestra las imagenes del tablero
        for(int i=0; i<NUM_CASILLAS;i++ ){
            tablero[i].setImageResource(imagenes[arrayBarajado.get(i)]);

        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<NUM_CASILLAS;i++ ) {
                    tablero[i].setImageResource(imagenFondo);
                }
            }
        }, 500);

        //añadir listener a las Imagenes
        for(int i=0; i<NUM_CASILLAS;i++ ) {

            tablero[i].setEnabled(true);
            final int j = i;
            tablero[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
               if(tableroBloqueado == false){
                   comprobar(j, tablero[j]);
               }
                }
            });

        }

        }

        public void comprobar(int i, final ImageButton imageButton){
        // ninguna imagen seleccionada
            if(imagenvista == null){
                imagenvista = imageButton;
                imagenvista.setImageResource(imagenes[arrayBarajado.get(i)]);
                imagenvista.setEnabled(false);
                seleccion1 = arrayBarajado.get(i);
            }else{//Dos imagenes seleccionadas
                tableroBloqueado = true;
                imageButton.setImageResource(imagenes[arrayBarajado.get(i)]);
                imageButton.setEnabled(false);
                selecccion2 = arrayBarajado.get(i);
                //compruebo si son la misma imagen
                if(seleccion1 == selecccion2){
                    imagenvista = null;
                    tableroBloqueado = false;
                    aciertos++;
                    puntuacion++;
                    text_puntuacion.setText(getString(R.string.txt_puntuacion) + Integer.toString(puntuacion));

                    //comprobar si ya está todas las imagenses destapadas

                    if(aciertos == imagenes.length){
                        Toast.makeText(this,"Has ganado", Toast.LENGTH_LONG).show();

                    }
                }else{//No son la misma imagen

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imagenvista.setImageResource(imagenFondo);
                            imagenvista.setEnabled(true);
                            imageButton.setImageResource(imagenFondo);
                            imageButton.setEnabled(true);
                            tableroBloqueado = false;
                            imagenvista = null;
                        }
                    },500);

                }
            }
        }

    private ArrayList<Integer> barajarImagenes(){
        ArrayList<Integer> listabarajada = new ArrayList<Integer>();
        for(int i = 0 ; i<NUM_CASILLAS;i++){
            listabarajada.add(i%NUM_CASILLAS /2);

        }
        Log.d("Listabarajada", Arrays.toString(listabarajada.toArray()));
        Collections.shuffle(listabarajada);
        Log.d("Listabarajada", Arrays.toString(listabarajada.toArray()));
        return listabarajada;
    }

    public void salirJuego(View view) {
        finish();

    }

    public void reiniciarJuego(View view) {
        iniciandoJuego();
    }





    }