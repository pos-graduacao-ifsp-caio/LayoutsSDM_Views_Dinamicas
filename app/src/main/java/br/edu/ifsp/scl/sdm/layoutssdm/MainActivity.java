package br.edu.ifsp.scl.sdm.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private static final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";
    private static final String EMAILS_ARMAZENADOS = "EMAILS_ARMAZENADOS";
    private static final String TELEFONES_ARMAZENADOS = "TEEFONES_ARMAZENADOS";
    private static final String TIPOS_TEEFONES_ARMAZENADOS = "TIPOS_TEEFONES_ARMAZENADOS";

    private ArrayList<String> emailsArmazenados = new ArrayList<>();
    private ArrayList<String> telefonesArmazenados = new ArrayList<>();
    private ArrayList<Integer> tiposTelefonesArmazenados = new ArrayList<>();

    private CheckBox notificaceosCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;

    private LinearLayout linearLayoutTelefone;
    private LinearLayout linearLayoutEmail;
    private ArrayList<View> telefoneArrayList = new ArrayList<>();
    private ArrayList<View> emailArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        // Referências para as views
        notificaceosCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditText = findViewById(R.id.nomeEditText);

        linearLayoutTelefone = this.findViewById(R.id.linearLayout_telefone);
        linearLayoutEmail = this.findViewById(R.id.linearLayout_email);

        // Tratando evento de check no checkbox
        notificaceosCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                notificacoesRadioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE));
    }

    public void adicionarEmail(View view){
        if(view.getId() == R.id.btn_adicionar_email){
            LayoutInflater layoutInflater = getLayoutInflater();

            View layoutNovoEmail = layoutInflater.inflate(R.layout.novo_email_layout, null);
            emailArrayList .add(layoutNovoEmail);
            linearLayoutEmail.addView(layoutNovoEmail);
        }
    }

    public void adicionarTelefone(View view){
        if(view.getId() == R.id.btn_adicionar_telefone){
            LayoutInflater layoutInflater = getLayoutInflater();

            View layoutNovoTelefone = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
            telefoneArrayList.add(layoutNovoTelefone);
            linearLayoutTelefone.addView(layoutNovoTelefone);
        }
    }

    public void restaurarEmails(ArrayList<String> emailsArmazenados){
        for (String email : emailsArmazenados) {
            LayoutInflater layoutInflater = getLayoutInflater();

            // Infla um novo objeto View e o retorna.
            View layoutNovoTelefone = layoutInflater.inflate(R.layout.novo_email_layout, null);

            // A partir desse view inflada é possível recuperar os componentes que compoem essa view.
            EditText editTxt = layoutNovoTelefone.findViewById(R.id.editTxt_email);  // recupera o editTxt do email
            editTxt.setText(email);  // seta o email que foi guardado.
            linearLayoutTelefone.addView(layoutNovoTelefone);   // insere a view novamente no linearLayout
        }
    }

    public void restaurarTelefones(ArrayList<String> telefonesArmazenados, ArrayList<Integer> tiposTelefonesArmazenados){
        int i = 0; // variável de controle p/ recuperar o valor dos objetos da lista de tiposTelefone.
        for (String numeroTelefone : telefonesArmazenados) {
            LayoutInflater layoutInflater = getLayoutInflater();

            // Infla um novo objeto View e o retorna.
            View layoutNovoTelefone = layoutInflater.inflate(R.layout.novo_telefone_layout, null);

            // A partir desse view inflada é possível recuperar os componentes que compoem essa view.
            EditText editText = layoutNovoTelefone.findViewById(R.id.editTxt_telefone);  // recupera o editTxt do telefone
            editText.setText(numeroTelefone);  // seta o número de telefone que foi guardado na lista.

            Spinner spinnerTipoTelefone = layoutNovoTelefone.findViewById(R.id.spinner_tipoTelefone);  // recupera o spinner
            spinnerTipoTelefone.setSelection(tiposTelefonesArmazenados.get(i)); // seta o tipo do spinner.

            linearLayoutTelefone.addView(layoutNovoTelefone); // insere a view novamente no linearLayout
            i++;  // variável de controle
        }
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.notificacoesCheckBox){
            if(((CheckBox) view).isChecked()){
                notificacoesRadioGroup.setVisibility(View.VISIBLE);
            }   else{
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificaceosCheckBox.isChecked());
        outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        for(View viewEmail : emailArrayList){ //  recupera as views adicionadas para guardar os dados dos componentes internos
            EditText editTxtEmail = viewEmail.findViewById(R.id.editTxt_email);  // recupera o editTxt
            emailsArmazenados.add(String.valueOf(editTxtEmail.getText()));      // guarda na lista.
        }

        for(View viewTelefone : telefoneArrayList){    //  recupera as view adicionadas para guardar seus componentes internos
            EditText editTxtTelefone = viewTelefone.findViewById(R.id.editTxt_telefone);  // recupera o editTxt
            Spinner spinnerTipoTelefone = viewTelefone.findViewById(R.id.spinner_tipoTelefone);  // recupera o spinner

            telefonesArmazenados.add(String.valueOf(editTxtTelefone.getText()));  // guarda os números de telefone na lista

            if(spinnerTipoTelefone.getSelectedItem().equals("Fixo")){ // verifica o tipo do fone e guarda com seu valor na lista
                this.tiposTelefonesArmazenados.add(0);   // lista de inteiros   0 para fixo  1 para celular.
            }   else{
                this.tiposTelefonesArmazenados.add(1);
            }
        }
                    //  guarda os dados armazenados no bundle.
        outState.putStringArrayList(TELEFONES_ARMAZENADOS, telefonesArmazenados);
        outState.putIntegerArrayList(TIPOS_TEEFONES_ARMAZENADOS, tiposTelefonesArmazenados);
        outState.putStringArrayList(EMAILS_ARMAZENADOS, emailsArmazenados);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
                // recupera os dados armazenados no bundle.
        notificaceosCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX, false));
        notificacoesRadioGroup.check(savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, View.NO_ID));
        this.emailsArmazenados = savedInstanceState.getStringArrayList(EMAILS_ARMAZENADOS);
        this.telefonesArmazenados = savedInstanceState.getStringArrayList(TELEFONES_ARMAZENADOS);
        this.tiposTelefonesArmazenados = savedInstanceState.getIntegerArrayList(TIPOS_TEEFONES_ARMAZENADOS);

        if(this.emailsArmazenados.size() > 0) {   // verifica se existem dados armazenados p/ inflar novamente as views
            this.restaurarEmails(this.emailsArmazenados);
        }
        if(this.telefonesArmazenados.size() > 0) {  // verifica se existem dados armazenados p/ inflar novamente as views
            this.restaurarTelefones(this.telefonesArmazenados, this.tiposTelefonesArmazenados);
        }
    }

    public void limparFormulario(View botao) {
        nomeEditText.setText(null);
        notificacoesRadioGroup.clearCheck();
        notificaceosCheckBox.setChecked(false);
        nomeEditText.requestFocus();

        if(!emailsArmazenados.isEmpty()){   // se a lista ñ estiver vazia  remove as views do linearLayout
            linearLayoutEmail.removeAllViews();
            emailsArmazenados.clear();
        }
        if(!telefonesArmazenados.isEmpty()){  // se a lista ñ estiver vazia  remove as views do linearLayout
            linearLayoutTelefone.removeAllViews();
            telefonesArmazenados.clear();
            tiposTelefonesArmazenados.clear();
        }

    }
}