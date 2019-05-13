package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.custom.adapter.PlanetListAdapter;
import com.example.custom.bean.Planet;
import com.example.custom.widget.NoScrollListView;

/**
 * OnMeasureActivity
 *
 * @author lao
 * @date 2019/4/19
 */

public class OnMeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_measure);
        PlanetListAdapter adapter1 = new PlanetListAdapter(this, Planet.getDefaultList());
        ListView lv_planet = findViewById(R.id.lv_planet);
        lv_planet.setAdapter(adapter1);
        lv_planet.setOnItemClickListener(adapter1);
        lv_planet.setOnItemLongClickListener(adapter1);
        PlanetListAdapter adapter2 = new PlanetListAdapter(this, Planet.getDefaultList());
        NoScrollListView nslv_planet = findViewById(R.id.nslv_planet);
        nslv_planet.setAdapter(adapter2);
        nslv_planet.setOnItemClickListener(adapter2);
        nslv_planet.setOnItemLongClickListener(adapter2);
    }
}
