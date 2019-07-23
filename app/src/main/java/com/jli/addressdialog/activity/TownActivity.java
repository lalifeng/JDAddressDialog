package com.jli.addressdialog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jli.addressdialog.R;
import com.jli.addressdialog.adapter.TownAdapter;
import com.jli.addressdialog.databinding.ActivityTownBinding;
import com.jli.addressdialog.entity.TownEntity;
import com.jli.addressdialog.event.AddressEvent;
import com.jli.addressdialog.widget.RecycleViewDividerForList;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TownActivity extends AppCompatActivity {

    private ActivityTownBinding binding;
    private String mAreaName;
    private String mCityName;
    private String mProvinceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_town);
        mAreaName = getIntent().getStringExtra("name");
        mCityName = getIntent().getStringExtra("cityName");
        mProvinceName = getIntent().getStringExtra("provinceName");
        String code = getIntent().getStringExtra("code");
        binding.titleLayout.txtTitle.setText(mAreaName);
        binding.titleLayout.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String areaJson = getTownJson();
        Gson gson = new Gson();
        final List<TownEntity> townList = gson.fromJson(areaJson, new TypeToken<List<TownEntity>>() {
        }.getType());
        final List<TownEntity> selectTownList = new ArrayList<>();
        for (TownEntity entity : townList) {
            if (entity.getAreaCode().equals(code)) {
                selectTownList.add(entity);
            }
        }
        binding.trv.setLayoutManager(new LinearLayoutManager(this));
        TownAdapter townAdapter = new TownAdapter(selectTownList);
        binding.trv.setAdapter(townAdapter);
        binding.trv.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));
        townAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new AddressEvent(mProvinceName + mCityName + mAreaName + selectTownList.get(position).getName(), selectTownList.get(position).getCode()));
                finish();
            }
        });
    }

    private String getTownJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("streets.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
