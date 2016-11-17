package com.eeyuva.screens.gridpages.infiniteGalleryCoverFlow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.eeyuva.screens.gridpages.ScaleImageView;
import com.eeyuva.screens.home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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


        ScaleImageView imageView = (ScaleImageView) l.findViewById(R.id.hotimage);
        TextView label = (TextView) l.findViewById(R.id.label);
        TextView labeltitle = (TextView) l.findViewById(R.id.labeltitle);
//        imageView.setImageBitmap(getBitmapFromURL(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getPicpath()));
        Picasso.with(getActivity()).load(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getPicpath()).placeholder(getActivity().getResources().getDrawable(R.drawable.y_logo)).into(imageView);
        imageView.setScaleType(ScaleImageView.ScaleType.FIT_XY);
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
