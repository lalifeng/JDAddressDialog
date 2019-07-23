package com.jli.addressdialog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jli.addressdialog.R;
import com.jli.addressdialog.adapter.ProvinceAdapter;
import com.jli.addressdialog.databinding.ActivityProvinceBinding;
import com.jli.addressdialog.entity.ProvinceEntity;
import com.jli.addressdialog.event.AddressEvent;
import com.jli.addressdialog.widget.RecycleViewDividerForList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ProvinceActivity extends AppCompatActivity {

    private ActivityProvinceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_province);
        EventBus.getDefault().register(this);
        binding.titleLayout.txtTitle.setText("请选择");
        final String provinceJson = getProvinceJson();
        Gson gson = new Gson();
        final List<ProvinceEntity> provinceList = gson.fromJson(provinceJson, new TypeToken<List<ProvinceEntity>>() {
        }.getType());
        binding.prv.setLayoutManager(new LinearLayoutManager(this));
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(provinceList);
        binding.prv.setAdapter(provinceAdapter);
        binding.prv.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));
        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                intent.putExtra("name", provinceList.get(position).getName());
                intent.putExtra("code", provinceList.get(position).getCode());
                startActivity(intent);
            }
        });
        binding.titleLayout.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getProvinceJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("provinces.json")));
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
