package ulbra.saolucas.appcadastro;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText edNome, edEndereco, edTelefone;
    private ulbra.saolucas.appcadastro.DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela2);

        edNome = findViewById(R.id.edtnome);
        edEndereco = findViewById(R.id.edtendereco);
        edTelefone = findViewById(R.id.edttelefone);
        dbHelper = new ulbra.saolucas.appcadastro.DBHelper(this);

        findViewById(R.id.btcadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save user data to the database
                saveUserData();
            }
        });

        findViewById(R.id.btcancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the fields and go back to the main screen
                finish();
            }
        });
    }

    private void saveUserData() {
        String nome = edNome.getText().toString();
        String endereco = edEndereco.getText().toString();
        String telefone = edTelefone.getText().toString();

        if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Insert data into the database
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nome", nome);
            values.put("endereco", endereco);
            values.put("telefone", telefone);

            long newRowId = db.insert("Usuarios", null, values);
            if (newRowId != -1) {
                // Success message
                Toast.makeText(this, "Cadastro concluído com sucesso!", Toast.LENGTH_LONG).show();

                // Clear the input fields after registration
                edNome.setText("");
                edEndereco.setText("");
                edTelefone.setText("");

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Close the registration activity
                    }
                }, 2000); // 2 seconds delay
            } else {
                Toast.makeText(this, "Erro ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
