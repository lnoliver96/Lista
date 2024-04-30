package otoni.luan.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import otoni.luan.lista.R;
import otoni.luan.lista.adapter.MyAdapter;
import otoni.luan.lista.model.MyItem;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST =1;

    List<MyItem> itens = new ArrayList<>();

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

        RecyclerView rvItens = findViewById(R.id.rvItens);

        myAdapter = new MyAdapter(this,itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == NEW_ITEM_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                myItem.photo = data.getData();

                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}