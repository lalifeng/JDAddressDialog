package com.jli.addressdialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jli.addressdialog.activity.ProvinceActivity;
import com.jli.addressdialog.databinding.ActivityMainBinding;
import com.jli.addressdialog.dialog.AddressPickerDialog;
import com.jli.addressdialog.entity.AreaEntity;
import com.jli.addressdialog.entity.CityEntity;
import com.jli.addressdialog.entity.ProvinceEntity;
import com.jli.addressdialog.entity.TownEntity;
import com.jli.addressdialog.event.AddressEvent;
import com.jli.addressdialog.utils.AddressUtil;
import com.jli.addressdialog.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int[] titles;
    private AddressPickerDialog addressPickerDialog;
    private List<ProvinceEntity> provinces;
    private List<CityEntity> cities;
    private List<AreaEntity> areas;
    private List<TownEntity> towns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EventBus.getDefault().register(this);
        binding.btnSelect.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ProvinceActivity.class)));
        new Thread(() -> {
            provinces = GsonUtil.parseFromStringTolist(AddressUtil.getAddressJson(MainActivity.this, "provinces.json"), ProvinceEntity.class);
            cities = GsonUtil.parseFromStringTolist(AddressUtil.getAddressJson(MainActivity.this, "cities.json"), CityEntity.class);
            areas = GsonUtil.parseFromStringTolist(AddressUtil.getAddressJson(MainActivity.this, "areas.json"), AreaEntity.class);
            towns = GsonUtil.parseFromStringTolist(AddressUtil.getAddressJson(MainActivity.this, "streets.json"), TownEntity.class);
        }).start();

        addressPickerDialog = new AddressPickerDialog(MainActivity.this, R.style.Dialog);
        addressPickerDialog.setAreaPickerViewCallback(new AddressPickerDialog.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                titles = value;
            }

            @Override
            public void addressCallBack(String... value) {
                if (value != null && value.length == 4) {
                    binding.btnJd.setText(value[0] + value[1] + value[2] + value[3]);
                }
            }
        });
        binding.btnJd.setOnClickListener(view -> {
            if (provinces == null || cities == null || areas == null || towns == null) {
                return;
            }
            addressPickerDialog.initData(provinces, cities, areas, towns);
            addressPickerDialog.show();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressEvent(AddressEvent messageEvent) {
        binding.btnSelect.setText(messageEvent.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
