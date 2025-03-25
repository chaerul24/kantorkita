package com.kantorkita;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.kantorkita.adapter.MenuAdapter;
import com.kantorkita.adapter.model.ModelMenu;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<SlideModel> array;
    private RecyclerView recyclerMenu;
    private static final int CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        array = new ArrayList<SlideModel>();
        array.add(new SlideModel("https://pemerintahandesa.wordpress.com/wp-content/uploads/2019/06/fb_img_15608787597448018-4.jpg", ScaleTypes.FIT));
        array.add(new SlideModel("https://scontent-cgk1-1.xx.fbcdn.net/v/t39.30808-6/318795437_1348993792509054_4975785352263816543_n.jpg?stp=dst-jpg_s960x960_tt6&_nc_cat=109&ccb=1-7&_nc_sid=2285d6&_nc_eui2=AeF2vqwArJQk6SaEWsS36u6Q8R7Y9aroaarxHtj1quhpquzx1Xz-vzzs8NOfrNdlTkoLe8KBrtOKUuid3Sez-278&_nc_ohc=oNMkTE6HCygQ7kNvgGYud5k&_nc_oc=Adlgs-X0auNbEbT-AVlvIj74M0QoQUFC5FhY_PrLi5Dh69nA0DT4Q3WFn_r92GU_IWM&_nc_zt=23&_nc_ht=scontent-cgk1-1.xx&_nc_gid=XCf1zdrZYAzBpxFemgq4fQ&oh=00_AYGiYdY7zxWPueWbZIVmqWyCwwak7aIiravrDoMFEQHzfg&oe=67E4F64E", ScaleTypes.FIT));
        com.denzcoskun.imageslider.ImageSlider imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(array);

        ArrayList<ModelMenu> menu = new ArrayList<>();
        menu.add(new ModelMenu("Absensi Masuk", R.drawable.timetable));
        menu.add(new ModelMenu("Absensi Keluar", R.drawable.appointment));
//        menu.add(new ModelMenu("Karyawan", R.drawable.ic_karyawan));
        menu.add(new ModelMenu("Jadwal Shift", R.drawable.ic_shift));
        menu.add(new ModelMenu("Pengajuan Cuti", R.drawable.ic_form));
        menu.add(new ModelMenu("Barang Masuk", R.drawable.ic_box));
        menu.add(new ModelMenu("Barang Keluar", R.drawable.ic_box_out));
        recyclerMenu = findViewById(R.id.recycler_menu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerMenu.setLayoutManager(gridLayoutManager);

        MenuAdapter adapter = new MenuAdapter(this, menu, new MenuAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                if(position == 0){
                    startActivity(new Intent(HomeActivity.this, AbsenMasukActivity.class));
                }
            }
        });
        recyclerMenu.setAdapter(adapter);


        if (checkCameraPermission()) {

        } else {
            requestCameraPermission();
        }
    }
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
