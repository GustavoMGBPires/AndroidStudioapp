package com.example.trab2_gustavogil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    //Declarando os elemetos
    private Spinner spBandeiras;
    private EditText edtConsumokwh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Inicio do codigo
        spBandeiras = findViewById(R.id.spBandeiras);
        edtConsumokwh = findViewById(R.id.edtConsumokw);

        //Definindo a Lista
        ArrayList lista = new ArrayList<>();
        lista.add("Bandeiras!");
        lista.add("Amarela");
        lista.add("Azul");
        lista.add("Vermelha");

        //Definir Adapter
        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                lista);
        spBandeiras.setAdapter(adapter);
    }
    //metodo do bnt
    public void onClick(View v){
        if (edtConsumokwh.getText().toString().isEmpty()){
            Alerta("Informe o Consumo de kW/h!");
            edtConsumokwh.requestFocus();

        } else if (spBandeiras.getSelectedItemPosition() == 0) {
            Alerta("Selecione uma Bandeira!");

        }else {
            Integer consumokhm = Integer.parseInt(edtConsumokwh.getText().toString());
            float consumofinal = (float) (consumokhm * 1.09898);
            double TB , vb = 0 , TIP =0 , calculofinal =0;

             //Calculo taxa Bandeiras
            if (spBandeiras.getSelectedItemPosition() == 1){
                //Bandeira Amarela
                TB = (consumofinal * 2.72)/100;
            }else if (spBandeiras.getSelectedItemPosition() == 2){
                //Bandeira Azul
                TB = (consumofinal * 0.58)/100;
            }else {
                //Bandeira Vermelha
                TB = (consumofinal * 7.16)/100;
            }

            //Calculo iluminação publica
            TIP = (consumofinal * 37.8)/100;

            if (TIP < 13){
                vb = 13.00; //valor minimo
            }else if (TIP > 40){
                vb = 40.00; // valor max
            }else{
                vb = TIP ;// Valor entre 13 e 40.
            }
            //Calculo Final da conta
            calculofinal = consumofinal + TB + vb;

            Alerta("Consumo Total: "+ String.format("%.2f",consumofinal)+
                    "\n Taxa da Bandeira: "+ String.format("%.2f",TB)+
                    "\n Taxa de Iluminação Publica: "+ String.format("%.2f",vb)+
                    "\n Valor Total da Conta: " + String.format("%.2f",calculofinal));
        }
    }
    //Metodo do professor para os alerta dialog
    private void Alerta(String msg){
        AlertDialog.Builder a = new AlertDialog.Builder(this);
        a.setTitle(" APP Conta de Energia");
        a.setMessage(msg);
        a.setCancelable(false);
        a.setPositiveButton("Ok",null);

        a.show();
    }
}