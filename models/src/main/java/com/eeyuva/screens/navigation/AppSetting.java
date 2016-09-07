package com.eeyuva.screens.navigation;

/**
 * Created by hari on 07/09/16.
 */
public class AppSetting {
    public int icon;
    public String title;

    public AppSetting(String title) {
        this.title = title;
    }

    public AppSetting(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }
}
