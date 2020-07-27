package Project.covid.hack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {

    private ImageView BtnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BtnMapa = findViewById(R.id.btnMap);

        BtnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,Mapa.class));
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
        builder.setTitle("Alerta!");
        builder.setCancelable(false);
        builder.setMessage("Deseas Salir? ");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

}