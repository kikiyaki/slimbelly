package com.hfad.slimbelly.main.spravka;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;

import com.hfad.slimbelly.PojoRegim;
import com.hfad.slimbelly.R;
import com.hfad.slimbelly.RegimAdapter;
import com.hfad.slimbelly.main.RegimeActivity;

import java.util.Arrays;
import java.util.Collection;

public class TrainActivity extends Activity {

    private RecyclerView regimRecyclerView;
    private RegimAdapter regimAdapter;
    Collection<PojoRegim> pojoRegim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        regimRecyclerView = findViewById(R.id.regim_recycler_view);
        regimRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        regimAdapter = new RegimAdapter(TrainActivity.this);
        regimRecyclerView.setAdapter(regimAdapter);

        pojoRegim = Arrays.asList(
                new PojoRegim("Бег с подъемом колен", "", R.drawable.pustota),
                new PojoRegim("Берпи", "", R.drawable.pustota),
                new PojoRegim("Скручивания", "", R.drawable.pustota),
                new PojoRegim("Бег с захлестом", "", R.drawable.pustota),
                new PojoRegim("Скалолаз", "", R.drawable.pustota),
                new PojoRegim("Складка", "", R.drawable.pustota),
                new PojoRegim("Прыжки с выпадами", "", R.drawable.pustota),
                new PojoRegim("Поднятие ног в планке", "", R.drawable.pustota),
                new PojoRegim("Велосипед", "", R.drawable.pustota)
        );

        regimAdapter.setItems(pojoRegim);
    }
}
