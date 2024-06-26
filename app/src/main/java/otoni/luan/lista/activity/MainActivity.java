package otoni.luan.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import otoni.luan.lista.R;
import otoni.luan.lista.adapter.MyAdapter;
import otoni.luan.lista.model.MainActivityViewModel;
import otoni.luan.lista.model.MyItem;
import otoni.luan.lista.util.Util;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST =1;

    MyAdapter myAdapter;
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

        //Obtem o fab
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        //Seta o ouvidor de cliques
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navega  para NewItemActivity
                Intent i = new Intent(MainActivity.this,NewItemActivity.class);
                //intent é executado usando um metodo que assume que a Activity irá retornar dados
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });

        //Obtem o RecyclerView
        RecyclerView rvItens = findViewById(R.id.rvItens);

        //Obtem viewmodel
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //a lista de itens é obtida a partir do ViewModel
        List<MyItem> itens = vm.getItens();


        //Cria o MyAdapter
        myAdapter = new MyAdapter(this,itens);
        //seta o myAdapter no recycleView
        rvItens.setAdapter(myAdapter);

        //Indica que nao ha variacao de tamanho entre os itens
        rvItens.setHasFixedSize(true);

        //Cria um gerenciador de layout do tipo linear
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // seta o gerenciador de layout no recyclerView
        rvItens.setLayoutManager(layoutManager);

        //cria um decorador  para a lista
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        // seta o decorador no recyclerView
        rvItens.addItemDecoration(dividerItemDecoration);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //Verifica a rota
        if (requestCode == NEW_ITEM_REQUEST){
            //verifica se codigo resultou em sucesso
            if(resultCode == Activity.RESULT_OK){
                //cria novo objeto MyItem
                MyItem myItem = new MyItem();
                //Obtem os dados retornados por NewItemActivity e armazena em myItem
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                //excessão dispara se o arquivo de imagem nao for encontrado
                try{
                    //Obtem Imagem e guarda em um Bitmap
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI,100,100);
                    //guarda bitmp em um objeto myItem
                    myItem.photo = photo;
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                //Obtem viewmodel
                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                //a lista de itens é obtida a partir do ViewModel
                List<MyItem> itens = vm.getItens();


                //armazena item em uma lista de itens
                itens.add(myItem);
                //notifica o adapter e faz com que RecycleView atualize e exiba o novo item
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}