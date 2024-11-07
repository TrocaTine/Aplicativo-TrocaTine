package com.example.trocatine.error;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;

public class ErrorDialog {

    private Dialog dialog;

    public ErrorDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.card_error); // Certifique-se de que o layout XML está nomeado corretamente
        dialog.setCancelable(true);
    }

    public void show(String title, String description) {
        TextView errorTitle = dialog.findViewById(R.id.errorTitle);
        TextView errorDescription = dialog.findViewById(R.id.errorDescription);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        errorTitle.setText(title);
        errorDescription.setText(description);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}