package com.e.myfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.myfirebase.model.Heroe;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Spinner opciones_heroes;
    Spinner segmento_personas;
    Spinner mySpinnerHeroes;
    Spinner mySpinnerPersonas;
    int cuentaTotalVotos;


    private List<Heroe> listPerson = new ArrayList<Heroe>();
    ArrayAdapter<Heroe> arrayAdapterPersona;

    EditText txtGusto, txtCambio, txtAparicion;
    String txtNombre, txtPersona;
    ListView listV_personas;

    //PARAMETROS PARA TRABAJAR CON FIREBASE---------------------------------
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        segmento_personas = (Spinner) findViewById(R.id.id_spiner_segmento);
        ArrayAdapter<CharSequence> adapt = arrayAdapterPersona.createFromResource(this, R.array.SegmentoPersonas, android.R.layout.simple_spinner_item);
        segmento_personas.setAdapter(adapt);


        opciones_heroes = (Spinner) findViewById(R.id.id_spiner_heroes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.MarvelHeroes, android.R.layout.simple_spinner_item);
        opciones_heroes.setAdapter(adapter);

        mySpinnerPersonas = (Spinner) findViewById(R.id.id_spiner_segmento);
        txtPersona = mySpinnerPersonas.getSelectedItem().toString();
        mySpinnerPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtPersona = mySpinnerPersonas.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mySpinnerHeroes = (Spinner) findViewById(R.id.id_spiner_heroes);
        txtNombre = mySpinnerHeroes.getSelectedItem().toString();
        mySpinnerHeroes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtNombre = mySpinnerHeroes.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtGusto = findViewById(R.id.txt_gusto);
        txtCambio = findViewById(R.id.txt_cambio);
        txtAparicion = findViewById(R.id.txt_aparicion);


        listV_personas = findViewById(R.id.lv_datosPersonas);
        inicializarFirebase();
        listarDatos();
    }

    //INICIALIZAR FIREBASE----------------------------------------------
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String tGusto = txtGusto.getText().toString();
        String tCambio = txtCambio.getText().toString();
        String tAparicion = txtAparicion.getText().toString();


        switch (item.getItemId()) {
            case R.id.icon_add: {
                if (tGusto.equals("") || tCambio.equals("") || tAparicion.equals("")) {
                    validacion();
                } else {
                    Heroe p = new Heroe();
                    p.setUid(UUID.randomUUID().toString());
                    p.setGusto(tGusto);
                    p.setCambio(tCambio);
                    p.setAparicion(tAparicion);
                    p.setNombre(txtNombre);

                    databaseReference.child("ENDGAME").child(txtPersona).child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Gracias por responder", Toast.LENGTH_LONG).show();
                    refrescarBoxes();
                    break;
                }
            }
            case R.id.icon_save: {
                Toast.makeText(this, "Guardar", Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.icon_delete: {
                Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                break;
            }

            default:
                break;
        }
        return true;
    }

    //VISUALIZACION DE DATOS -----------------------------------------------------
    private void listarDatos() {
        databaseReference.child("ENDGAME").child(txtPersona).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Heroe p = objSnaptshot.getValue(Heroe.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Heroe>(MainActivity.this, android.R.layout.simple_list_item_1, listPerson);
                    listV_personas.setAdapter(arrayAdapterPersona);

                    arrayAdapterPersona.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void validacion() {
        String gusto = txtGusto.getText().toString();
        String cambio = txtCambio.getText().toString();
        String aparicion = txtAparicion.getText().toString();

        if (gusto.equals("")) {
            txtGusto.setError("Required");
        }

        if (cambio.equals("")) {
            txtCambio.setError("Required");
        }

        if (aparicion.equals("")) {
            txtAparicion.setError("Required");
        }

    }

    private void refrescarBoxes() {
        txtGusto.setText("");
        txtCambio.setText("");
        txtAparicion.setText("");
        //txtNombre = null;
        //txtPersona = null;


    }
}
