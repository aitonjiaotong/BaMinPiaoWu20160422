package bamin.com.kepiao.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import bamin.com.kepiao.R;
import bamin.com.kepiao.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment
{

    private int guideImg;
    private int guideImg_length;
    private int position;
    private Button mEnter;
    public GuideFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View inflate = inflater.inflate(R.layout.fragment_guide, container, false);
        ImageView guide_img = (ImageView) inflate.findViewById(R.id.guide_img);
        if (position==guideImg_length){
            mEnter = (Button) inflate.findViewById(R.id.button_guide);
            mEnter.setVisibility(View.VISIBLE);

            mEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getActivity().getSharedPreferences("isfrist", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isfrist", false);
                    edit.commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }
        guide_img.setImageResource(guideImg);
        return inflate;
    }

    public void setImg(int guideImg,int guideImg_length,int position){
        this.guideImg=guideImg;
        this.guideImg_length=guideImg_length;
        this.position=position;

    }

}
