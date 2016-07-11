package com.example.sharingbooks;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<TwoStrings> {

private int layoutResource;

public CustomAdapter(Context context, int layoutResource, List<TwoStrings> twoStringsList) {
    super(context, layoutResource, twoStringsList);
    this.layoutResource = layoutResource;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

    View view = convertView;

    if (view == null) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(layoutResource, null);
    }

    TwoStrings threeStrings = getItem(position);

    if (threeStrings != null) {
        TextView leftTextView = (TextView) view.findViewById(R.id.up);
        TextView rightTextView = (TextView) view.findViewById(R.id.down);

        if (leftTextView != null) {
            leftTextView.setText(threeStrings.getLeft());
        }

        if (rightTextView != null) {
            rightTextView.setText(threeStrings.getRight());
        }
    }

    return view;
}
}