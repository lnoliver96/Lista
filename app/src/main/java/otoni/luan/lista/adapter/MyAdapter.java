package otoni.luan.lista.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import otoni.luan.lista.R;
import otoni.luan.lista.activity.MainActivity;
import otoni.luan.lista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens){
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Obtem o inflador de layout
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        //cria os elementos de interface referentes a um item e os guarda em uma View
        View v = inflater.inflate(R.layout.item_list,parent,false);
        //O objeto view é guardado dentro de um objeto MyViewHolder
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //obtem o item que vai ser usado para preencher a UI
        MyItem myItem = itens.get(position);

        //Obtem o objeto view armazenado no ViewHolder
        View v = holder.itemView;

        //preenche UI com os dados do item respectivamente com foto, titulo e descricao
        ImageView imvphoto = v.findViewById(R.id.imvPhoto);
        imvphoto.setImageBitmap(myItem.photo);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvdesc = v.findViewById(R.id.tvDesc);
        tvdesc.setText((myItem.description));
    }

    @Override
    public int getItemCount() {
        //retorna tamanho da lista de itens
        return itens.size();
    }

}
