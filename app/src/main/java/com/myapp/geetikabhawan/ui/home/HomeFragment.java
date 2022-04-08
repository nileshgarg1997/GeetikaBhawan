package com.myapp.geetikabhawan.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myapp.geetikabhawan.packages.BudgetActivity;
import com.myapp.geetikabhawan.packages.CustomizeActivity;
import com.myapp.geetikabhawan.services.EventActivity;
import com.myapp.geetikabhawan.packages.MaharajaActivity;
import com.myapp.geetikabhawan.services.PartyActivity;
import com.myapp.geetikabhawan.R;
import com.myapp.geetikabhawan.services.ReceptionActivity;
import com.myapp.geetikabhawan.packages.SumptuousActivity;
import com.myapp.geetikabhawan.services.WeddingActivity;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;


public class HomeFragment extends Fragment {

    private LinearLayout maharajaBtn,sumptuousBtn,customizeBtn,budgetBtn;

    private SliderLayout sliderLayout;
    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout=view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2);

        maharajaBtn=view.findViewById(R.id.maharajaBtn);
        sumptuousBtn=view.findViewById(R.id.sumptuousBtn);
        customizeBtn=view.findViewById(R.id.customizeBtn);
        budgetBtn=view.findViewById(R.id.budgetBtn);


        maharajaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), MaharajaActivity.class);
                startActivity(i);
            }
        });

        sumptuousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), SumptuousActivity.class);
                startActivity(i);            }
        });

        customizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), CustomizeActivity.class);
                startActivity(i);
            }
        });

        budgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), BudgetActivity.class);
                startActivity(i);
            }
        });


        setSliderViews();

        map=view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        return view;
    }

    private void openMap() {

        Uri uri=Uri.parse("geo:0, 0?q=Geetika Bhawan");
        Intent intent=new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void setSliderViews() {

        for (int i=0;i<6;i++){

            DefaultSliderView sliderView= new DefaultSliderView(getContext());

            switch (i){

                case 0:
                    sliderView.setDescription("Geetika Bhawan");
                    sliderView.setDescriptionTextSize(25);
                    sliderView.setImageDrawable(R.drawable.homepage_image1);
                    break;

                case 1:
                    sliderView.setDescription("Reception");
                    sliderView.setDescriptionTextSize(25);
                    sliderView.setImageDrawable(R.drawable.homepage_image2);
                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            Intent intent=new Intent(getContext(), ReceptionActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;

                case 2:
                    sliderView.setDescription("Wedding");
                    sliderView.setDescriptionTextSize(25);
                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            Intent intent=new Intent(getContext(), WeddingActivity.class);
                            startActivity(intent);
                        }
                    });
                    sliderView.setImageDrawable(R.drawable.homepage_image3);
                    break;

                case 3:
                    sliderView.setDescription("Party");
                    sliderView.setDescriptionTextSize(25);

                    sliderView.setImageDrawable(R.drawable.homepage_image4);
                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            Intent intent=new Intent(getContext(), PartyActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;

                case 4:
                    sliderView.setDescription("Event");
                    sliderView.setDescriptionTextSize(25);

                    sliderView.setImageDrawable(R.drawable.homepage_image5);
                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            Intent intent=new Intent(getContext(), EventActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;

                case 5:
                    sliderView.setDescription("And Much More...");
                    sliderView.setDescriptionTextSize(25);

                    sliderView.setImageDrawable(R.drawable.homepage_image6);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            sliderLayout.addSliderView(sliderView);
        }
    }


}