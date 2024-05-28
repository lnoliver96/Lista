package otoni.luan.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import otoni.luan.lista.R;
import otoni.luan.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Obtem ViewModel referente a NewItemActivity
        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
        //Obtem endereco uri de dentro do viewmodel
        Uri selectPhotoLocation = vm.getSelectedPhotoLocation();
        if(selectPhotoLocation != null){
            ImageView imvphotoPreview = findViewById(R.id.imvPhotoPreview);
            imvphotoPreview.setImageURI(selectPhotoLocation);
        }


        //Obtem o image button
        ImageButton imgCI = findViewById(R.id.imbCI);



        //define o ouvidor de cliques
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cria intent implicito
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //define tipo de documentos de interesse
                photoPickerIntent.setType("image/*");
                //executa intent
                startActivityForResult(photoPickerIntent,PHOTO_PICKER_REQUEST);


            }
        });
        //Obtem botao
        Button btnAddItem = findViewById(R.id.btnAddItem);
        //Cria ouvidor de cliques
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri photoSelected = vm.getSelectedPhotoLocation();
                //Verifica se o campo da foto foi preenchido pelo usuario
                if (photoSelected == null){
                    Toast.makeText(NewItemActivity.this,"É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;

                }
                //obtem titulo
                EditText etTitle = findViewById(R.id.etTitle);
                //converte para string
                String title = etTitle.getText().toString();
                //Verifica se o campo de titulo foi preenchido pelo usuario
                if(title.isEmpty()){
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                //obtem descricao
                EditText etDesc = findViewById(R.id.etDesc);
                //converte para string
                String description = etDesc.getText().toString();
                //Verifica se o campo de descricao foi preenchido pelo usuario
                if(description.isEmpty()){
                    Toast.makeText(NewItemActivity.this, "è necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                //cria intent para guardar dados que serao retornados para MainActivity
                Intent i = new Intent();
                //seta uri da imagem na intent
                i.setData(photoSelected);
                //seta titulo na intent
                i.putExtra("title",title);
                //seta dexcricao na intent
                i.putExtra("description",description);
                //indica resultado da  Activity
                setResult(Activity.RESULT_OK,i);
                //Activity é finalizada
                finish();
            }
        });
    }
    @Override
    //Parametros de onActivityResult:
    //requestcode -> inteiro que indica a qual chamada de startActivityForResult essa resposta se refere
    //resultCode -> código que indica se a Activity de destino retornou com sucesso ou não
    //data -> Intent que contém os dados retornados pela Activity de destino
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //verifica se requestcode é o que foi dado na chamada de StartActivity
        if(requestCode == PHOTO_PICKER_REQUEST){
            //verifica se codigo resultou em sucesso
            if(resultCode == Activity.RESULT_OK){
                //obtem uri da imagem
                Uri photoSelected = data.getData();
                //obtem o imageView
                ImageView imvphotoPreview = findViewById(R.id.imvPhotoPreview);
                //seta o uri da foto no imageView
                imvphotoPreview.setImageURI(photoSelected);


                //obtem viewmodel
                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                //guarda endereco uri da imagem escolhida
                vm.setSelectedPhotoLocation(photoSelected);

            }
        }
    }
}