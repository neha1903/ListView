package com.custom.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomListViewAdapter extends ArrayAdapter<Container> {


    public CustomListViewAdapter(Context context, ArrayList<Container> items) {
        super(context, 0, items);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View listItemView = convertView;
        final Container container = getItem(position);

        if(listItemView == null){
            listItemView = LayoutInflater.from(this.getContext()).inflate(R.layout.row, parent, false);
        }


        final ImageView cameraBtn = listItemView.findViewById(R.id.btnCamera);
        final EditText editText = (EditText) listItemView.findViewById(R.id.editView);
        final TextView textView = (TextView) listItemView.findViewById(R.id.textView);
        editText.setTag(position);

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = (Integer) editText.getTag();
                MainActivity.containers.get(pos).setRemark(s.toString());
            }
        };

        if(container != null){
            container.setListItemPosition(position);

            if(container.getRemarkCheck()){
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);

            }else{
                editText.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
            editText.setText(container.getRemark());
            textView.setText(container.getRemark());


            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    for(int i=0; i<MainActivity.containers.size(); i++){
                        MainActivity.containers.get(i).setRemarkCheck(false);
                    }
                    container.setRemarkCheck(true);
                    textView.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                    editText.requestFocus();

                    if (editText.requestFocus()) {
                        InputMethodManager imm = (InputMethodManager)
                                getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }

                    editText.addTextChangedListener(textWatcher);
                    return true;
                }
            });

            cameraBtn.setImageBitmap(container.getPhoto());

            cameraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.position1 = container.getListItemPosition();
                    ((MainActivity) getContext()).startCameraActivity();
//                    Toast.makeText(getContext(), "Camera Button",  Toast.LENGTH_LONG).show();
                }
            });

        }


        return listItemView;

    }
}
