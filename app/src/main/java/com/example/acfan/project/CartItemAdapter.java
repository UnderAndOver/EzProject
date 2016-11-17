package com.example.acfan.project;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by acfan on 11/17/2016.
 */

public class CartItemAdapter extends ArrayAdapter<> {
    public CartItemAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }
}
