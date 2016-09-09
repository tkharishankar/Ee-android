package com.eeyuva.screens.home.slider;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eeyuva.R;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.ResponseItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuweichen on 16/5/3.
 */
public class DataDemoView extends LinearLayout{
    private ListView listview;

    public DataDemoView(Context context) {
        super(context);
        initView(context);
    }

    public DataDemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DataDemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public DataDemoView(Context context, List<ResponseItem> articles) {
        super(context);
        initView(context,articles);
    }

    private void initView(Context context, List<ResponseItem> articles) {
        View view = inflate(context, R.layout.view_demo_list,this);
        listview = (ListView) view.findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_expandable_list_item_1,
                getArticle(articles));
        listview.setAdapter(adapter);

        ViewCompat.setNestedScrollingEnabled(listview, true);
    }

    private ArrayList<String> getArticle(List<ResponseItem> articles) {
        ArrayList<String> list = new ArrayList<>();

        for (ResponseItem re:articles) {
            list.add(re.getTitle());
        }
        return list;
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.view_demo_list,this);
        listview = (ListView) view.findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_expandable_list_item_1,
                getData());
        listview.setAdapter(adapter);

        ViewCompat.setNestedScrollingEnabled(listview, true);
    }

    private ArrayList<String> getData()
    {

        ArrayList<String> list = new ArrayList<>();
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");
        list.add("hello world");
        list.add("hello android");

        return list;
    }


}
