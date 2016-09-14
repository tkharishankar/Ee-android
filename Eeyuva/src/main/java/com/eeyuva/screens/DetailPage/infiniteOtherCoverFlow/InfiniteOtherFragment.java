package com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotLinearLayout;
import com.squareup.picasso.Picasso;

public class InfiniteOtherFragment extends Fragment {

    public static Fragment newInstance(DetailActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, InfiniteOtherFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.other_mf, container, false);

        int pos = this.getArguments().getInt("pos");
        ImageView imageView = (ImageView) l.findViewById(R.id.hotimage);
        TextView label = (TextView) l.findViewById(R.id.label);
        TextView labeltitle = (TextView) l.findViewById(R.id.labeltitle);
        Picasso.with(getActivity()).load(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getGalleryimg()).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        label.setText(getSubString(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getTitle()));
        labeltitle.setText(getSubString(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getModulename()));

        InfiniteOtherLinearLayout root = (InfiniteOtherLinearLayout) l.findViewById(R.id.hotroot);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }

    private String getSubString(String summary) {
        if (summary.length() > 100)
            return summary.substring(0, 100) + "...";
        return summary;
    }
}
