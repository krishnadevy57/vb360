package com.mind2web.vb360.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mind2web.vb360.modeles.EmailMessageTemplateData;

import java.util.List;

public class EmailTemplateAdapter extends ArrayAdapter<EmailMessageTemplateData> {

    public EmailTemplateAdapter(Context context, List<EmailMessageTemplateData> templates) {
        super(context, android.R.layout.simple_spinner_item, templates);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(getItem(position).getSubject());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(getItem(position).getSubject());
        return label;
    }
}
