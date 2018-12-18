package com.hfad.slimbelly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.slimbelly.main.spravka.MealActivity;
import com.hfad.slimbelly.main.spravka.TrainTrainActivity;

public class RegimAdapter extends RecyclerView.Adapter<RegimAdapter.RegimViewHolder> {

    private List<PojoRegim> regimList = new ArrayList<>();
    private Context context;

    public RegimAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RegimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.regim_view, parent, false);
        return new RegimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegimViewHolder holder, int position) {
        holder.bind(regimList.get(position));
    }

    @Override
    public int getItemCount() {
        return regimList.size();
    }

    public void setItems(Collection<PojoRegim> pojoRegimCollection) {
        regimList.addAll(pojoRegimCollection);
        notifyDataSetChanged();
    }

    class RegimViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView regimImageView;
        private TextView regimNameView;
        private TextView regimTimeView;


        public RegimViewHolder(View itemView) {
            super(itemView);
            regimImageView = itemView.findViewById(R.id.imageRegim);
            regimNameView = itemView.findViewById(R.id.textRegim);
            regimTimeView = itemView.findViewById(R.id.timeRegim);

            regimImageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String tag = (String) ((ImageView) view).getTag();
            switch (tag) {
                case "Зарядка":
                    Intent intent = new Intent(context, TrainingActivity.class);
                    context.startActivity(intent);
                    break;

                case "Завтрак":
                    Intent intent1 = new Intent(context, MealActivity.class);
                    context.startActivity(intent1);
                    break;
                case "Обед":
                    Intent intent2 = new Intent(context, MealActivity.class);
                    context.startActivity(intent2);
                    break;
                case "Ужин":
                    Intent intent3 = new Intent(context, MealActivity.class);
                    context.startActivity(intent3);
                    break;
                case "Вакуум":
                    Intent intent4 = new Intent(context, VacuumActivity.class);
                    context.startActivity(intent4);
                    break;

                    //Варианты для использования в справке
                case "Бег с подъемом колен":
                    Intent intent11 = new Intent(context, TrainTrainActivity.class);
                    intent11.putExtra("EX", 1);
                    context.startActivity(intent11);
                    break;
                case "Берпи":
                    Intent intent12 = new Intent(context, TrainTrainActivity.class);
                    intent12.putExtra("EX", 2);
                    context.startActivity(intent12);
                    break;
                case "Скручивания":
                    Intent intent13 = new Intent(context, TrainTrainActivity.class);
                    intent13.putExtra("EX", 3);
                    context.startActivity(intent13);
                    break;
                case "Бег с захлестом":
                    Intent intent14 = new Intent(context, TrainTrainActivity.class);
                    intent14.putExtra("EX", 4);
                    context.startActivity(intent14);
                    break;
                case "Скалолаз":
                    Intent intent15 = new Intent(context, TrainTrainActivity.class);
                    intent15.putExtra("EX", 5);
                    context.startActivity(intent15);
                    break;
                case "Складка":
                    Intent intent16 = new Intent(context, TrainTrainActivity.class);
                    intent16.putExtra("EX", 6);
                    context.startActivity(intent16);
                    break;
                case "Прыжки с выпадами":
                    Intent intent17 = new Intent(context, TrainTrainActivity.class);
                    intent17.putExtra("EX", 7);
                    context.startActivity(intent17);
                    break;
                case "Поднятие ног в планке":
                    Intent intent18 = new Intent(context, TrainTrainActivity.class);
                    intent18.putExtra("EX", 8);
                    context.startActivity(intent18);
                    break;
                case "Велосипед":
                    Intent intent19 = new Intent(context, TrainTrainActivity.class);
                    intent19.putExtra("EX", 9);
                    context.startActivity(intent19);
                    break;
            }
        }

        public void bind(PojoRegim pojoRegim) {
            regimImageView.setImageResource(pojoRegim.getImageId());
            regimImageView.setTag(pojoRegim.getName());
            regimNameView.setText(pojoRegim.getName());
            regimTimeView.setText(pojoRegim.getTime());
        }


    }


}
