package ulbra.saolucas.appcadastro;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {

    private TextView txtNome, txtEndereco, txtTelefone, txtRegistros;
    private ulbra.saolucas.appcadastro.DBHelper dbHelper;
    private Cursor cursor;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela3);

        txtNome = findViewById(R.id.txtNome);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtRegistros = findViewById(R.id.txtRegistros);
        dbHelper = new ulbra.saolucas.appcadastro.DBHelper(this);

        loadUsers();

        findViewById(R.id.btproximo).setOnClickListener(v -> showNextUser());
        findViewById(R.id.btanterior).setOnClickListener(v -> showPreviousUser());
        findViewById(R.id.btfechar).setOnClickListener(v -> finish());
    }

    private void loadUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("Usuarios", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            displayUser();
        } else {
            txtRegistros.setText("Nenhum usuário cadastrado");
        }
    }

    private void displayUser() {
        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        String endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"));
        String telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));

        txtNome.setText("Nome: " + nome);
        txtEndereco.setText("Endereço: " + endereco);
        txtTelefone.setText("Telefone: " + telefone);
        txtRegistros.setText("Registros: " + (cursor.getPosition() + 1) + "/" + cursor.getCount());
    }

    private void showNextUser() {
        if (cursor.moveToNext()) {
            displayUser();
        }
    }

    private void showPreviousUser() {
        if (cursor.moveToPrevious()) {
            displayUser();
        }
    }
}