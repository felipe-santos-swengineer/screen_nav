package br.com.pratica.screen_nav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista_itens;
    public ArrayList<Carro> carros = new ArrayList<>();
    ArrayAdapter ad;
    Button btn_editar;
    Button btn_adicionar;
    public ArrayList<String> descricao_carros = new ArrayList<>();
    EditText text_id;
    String teste = "";
    int world_id ;

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "OnStart" , Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_editar = findViewById(R.id.btn_editar);
        btn_adicionar = findViewById(R.id.btn_adicionar);
        text_id = (EditText) findViewById(R.id.text_id);
        lista_itens = (ListView) findViewById(R.id.lista_itens);

        //carros = new ArrayList<>();
        //descricao_carros = new ArrayList<>();

            ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descricao_carros);
            lista_itens.setAdapter(ad);

            lista_itens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "Você Selecionou : " + carros.get(position).getNome(), Toast.LENGTH_SHORT).show();
                    teste = Integer.toString(carros.get(position).getId());
                    text_id.setText(teste);
                }
            });

        }


    public void onClickBtnEditar(View v){

        teste = text_id.getText().toString();
        boolean is_valid = true;
        for(int i = 0; i < teste.length(); i++){
            if(teste.charAt(i) != '1' && teste.charAt(i) != '2' && teste.charAt(i) != '3' && teste.charAt(i) != '4' && teste.charAt(i) != '5'
                    && teste.charAt(i) != '6' && teste.charAt(i) != '7' && teste.charAt(i) != '8' && teste.charAt(i) != '9' && teste.charAt(i) != '0'){
                is_valid = false;
            }
        }

        if(teste.equals("")){
            is_valid = false;
        }

        if(is_valid == true) {
            int id_teste = Integer.parseInt(teste);
            //Toast.makeText(MainActivity.this, Integer.toString(id_teste), Toast.LENGTH_SHORT).show();

            if (id_teste > carros.size()) {
                is_valid = false;
                Toast.makeText(MainActivity.this, "ID invalido", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(MainActivity.this, "ID invalido", Toast.LENGTH_SHORT).show();
        }

        if(is_valid == true) {
            int id_number = Integer.parseInt(teste);
            int index_id = id_number - 1;
            //Log.d("MainActivity", Integer.toString(world_id));
            world_id = id_number;
            Bundle params = new Bundle();
            params.putInt("id", id_number);
            params.putInt("index",index_id);
            params.putString("nome", carros.get(index_id).getNome());
            params.putInt("ano", carros.get(index_id).getAno());
            params.putString("placa", carros.get(index_id).getPlaca());
            params.putInt("Qitens",carros.size());
            params.putInt("adicionar", 0);

            Intent tela_editar = new Intent(this, MainActivity2.class);
            tela_editar.putExtras(params);
            startActivityForResult(tela_editar,1);
        }
    }

    public void onClickBtnAdicionar(View v){

        Bundle params = new Bundle();
        params.putInt("adicionar", 1);
        params.putInt("Qitens",carros.size());

        Intent tela_editar = new Intent(this, MainActivity2.class);
        tela_editar.putExtras(params);
        startActivityForResult(tela_editar,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int ano = data.getIntExtra("ano",0);
                int op = data.getIntExtra("op",0);
                String nome = data.getStringExtra("nome");
                String placa = data.getStringExtra("placa");
                Log.d("MainActivity", op + nome + ano + placa +"Aqui_f");

                if(op == 1) {
                    Log.d("MainActivity", "OP 0");
                    Carro carro_novo = new Carro(carros.size(), placa, nome, ano);
                    Log.d("MainActivity", carro_novo.getNome() + carro_novo.getAno() + carro_novo.getId() + carro_novo.getPlaca());
                    carros.add(carro_novo);
                    descricao_carros.add("ID: " + carro_novo.getId() + " Placa: " + carro_novo.getPlaca() + " Nome: " + carro_novo.getNome() + " Ano: " + carro_novo.getAno());
                    ad.notifyDataSetChanged();
                }

                if(op == 0){
                    Log.d("MainActivity", "OP 1");
                    world_id = world_id - 1;
                    carros.get(world_id).setAno(ano);
                    carros.get(world_id).setNome(nome);
                    carros.get(world_id).setPlaca(placa);
                    descricao_carros.set(world_id, "ID: " + carros.get(world_id).getId() + " Placa: " + carros.get(world_id).getPlaca() + " Nome: " +
                            carros.get(world_id).getNome() + " Ano: " + carros.get(world_id).getAno());
                    ad.notifyDataSetChanged();
                }
            }
            if(resultCode == RESULT_CANCELED){
            }
        }
    }
}