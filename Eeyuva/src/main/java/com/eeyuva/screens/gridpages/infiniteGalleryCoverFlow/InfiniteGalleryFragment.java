package com.eeyuva.screens.gridpages.infiniteGalleryCoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.gridpages.PhotoGalleryActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.squareup.picasso.Picasso;

public class InfiniteGalleryFragment extends Fragment {

    public static Fragment newInstance(PhotoGalleryActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, InfiniteGalleryFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.gallery_mf, container, false);

        int pos = this.getArguments().getInt("pos");
        ImageView imageView = (ImageView) l.findViewById(R.id.hotimage);
        TextView label = (TextView) l.findViewById(R.id.label);
        TextView labeltitle = (TextView) l.findViewById(R.id.labeltitle);
        Picasso.with(getActivity()).load(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getPicpath()).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        label.setText(getSubString(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getTitle()));

        InfiniteGalleryLinearLayout root = (InfiniteGalleryLinearLayout) l.findViewById(R.id.hotroot);
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
