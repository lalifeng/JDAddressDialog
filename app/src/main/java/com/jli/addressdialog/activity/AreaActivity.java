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
import com.jli.addressdialog.adapter.AreaAdapter;
import com.jli.addressdialog.adapter.CityAdapter;
import com.jli.addressdialog.databinding.ActivityAreaBinding;
import com.jli.addressdialog.entity.AreaEntity;
import com.jli.addressdialog.entity.CityEntity;
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

public class AreaActivity extends AppCompatActivity {

    private ActivityAreaBinding binding;
    private String mCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_area);
        EventBus.getDefault().register(this);
        mCityName = getIntent().getStringExtra("name");
        String code = getIntent().getStringExtra("code");
        binding.titleLayout.txtTitle.setText(mCityName);
        binding.titleLayout.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String areaJson = getAreaJson();
        Gson gson = new Gson();
        final List<AreaEntity> areaList = gson.fromJson(areaJson, new TypeToken<List<AreaEntity>>() {
        }.getType());
        final List<AreaEntity> selectAreaList = new ArrayList<>();
        for (AreaEntity entity : areaList) {
            if (entity.getCityCode().equals(code)) {
                selectAreaList.add(entity);
            }
        }
        binding.arv.setLayoutManager(new LinearLayoutManager(this));
        AreaAdapter areaAdapter = new AreaAdapter(selectAreaList);
        binding.arv.setAdapter(areaAdapter);
        binding.arv.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));
        areaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(AreaActivity.this, TownActivity.class);
                intent.putExtra("name", selectAreaList.get(position).getName());
                intent.putExtra("cityName", mCityName);
                intent.putExtra("provinceName", getIntent().getStringExtra("provinceName"));
                intent.putExtra("code", selectAreaList.get(position).getCode());
                startActivity(intent);
            }
        });
    }

    private String getAreaJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("areas.json")));
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
