package com.apps.szpansky.ajwon_app.Tools;


import com.apps.szpansky.ajwon_app.R;

public abstract class Data implements DataInterface{

    protected String filter = "";

    @Override
    public int getLayoutResourceId(){
        return R.layout.activity_simple_view;
    }

}
