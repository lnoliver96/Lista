package otoni.luan.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {
    Uri selectedPhotoLocation = null;

    //Obtem a lista de itens
    public Uri getSelectedPhotoLocation(){
        return selectedPhotoLocation;
    }

    //seta o endereço uri dentro do viewModel
    public void setSelectedPhotoLocation(Uri selectedPhotoLocation){
        this.selectedPhotoLocation = selectedPhotoLocation;
    }
}
