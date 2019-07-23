package com.jli.addressdialog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jli.addressdialog.R;
import com.jli.addressdialog.adapter.CityAdapter;
import com.jli.addressdialog.adapter.ProvinceAdapter;
import com.jli.addressdialog.databinding.ActivityCityBinding;
import com.jli.addressdialog.entity.CityEntity;
import com.jli.addressdialog.entity.ProvinceEntity;
import com.jli.addressdialog.event.AddressEvent;
import com.jli.addressdialog.widget.RecycleViewDividerForList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {

    private ActivityCityBinding binding;
    private String mProvinceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city);
        EventBus.getDefault().register(this);
        mProvinceName = getIntent().getStringExtra("name");
        String code = getIntent().getStringExtra("code");
        binding.titleLayout.txtTitle.setText(mProvinceName);
        binding.titleLayout.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String cityJson = getCityJson();
        Gson gson = new Gson();
        final List<CityEntity> cityList = gson.fromJson(cityJson, new TypeToken<List<CityEntity>>() {
        }.getType());
        final List<CityEntity> selectCityList = new ArrayList<>();
        for (CityEntity entity : cityList) {
            if (entity.getProvinceCode().equals(code)) {
                selectCityList.add(entity);
            }
        }
        binding.crv.setLayoutManager(new LinearLayoutManager(this));
        CityAdapter cityAdapter = new CityAdapter(selectCityList);
        binding.crv.setAdapter(cityAdapter);
        binding.crv.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));
        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CityActivity.this, AreaActivity.class);
                intent.putExtra("name", selectCityList.get(position).getName());
                intent.putExtra("provinceName", mProvinceName);
                intent.putExtra("code", selectCityList.get(position).getCode());
                startActivity(intent);
            }
        });
    }

    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("cities.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressEvent(AddressEvent messageEvent) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
