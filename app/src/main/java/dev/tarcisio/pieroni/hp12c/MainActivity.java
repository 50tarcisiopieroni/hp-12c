package dev.tarcisio.pieroni.hp12c;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private EditText visor;
    private Calculadora calculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        calculadora = new ViewModelProvider(this).get(Calculadora.class);

        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        Button btn3 = findViewById(R.id.btn_3);
        Button btn4 = findViewById(R.id.btn_4);
        Button btn5 = findViewById(R.id.btn_5);
        Button btn6 = findViewById(R.id.btn_6);
        Button btn7 = findViewById(R.id.btn_7);
        Button btn8 = findViewById(R.id.btn_8);
        Button btn9 = findViewById(R.id.btn_9);
        Button btn0 = findViewById(R.id.btn_0);
        Button btnEnter = findViewById(R.id.btn_enter);
        Button btnBackSpace = findViewById(R.id.btn_back_space);
        Button btnAdicao = findViewById(R.id.btn_soma);
        Button btnSubtracao = findViewById(R.id.btn_subtracao);
        Button btnMultiplicacao = findViewById(R.id.btn_multiplicacao);
        Button btnDivisao = findViewById(R.id.btn_divisao);
        Button btnClear = findViewById(R.id.btn_clr);
        Button btnPV = findViewById(R.id.btn_PV);
        Button btnFV = findViewById(R.id.btn_FV);
        Button btnPMT = findViewById(R.id.btn_PMT);
        Button btnTaxa = findViewById(R.id.btn_taxa);
        Button btnN = findViewById(R.id.btn_n);
        Button btnVirgula = findViewById(R.id.btn_virgula);

        visor = findViewById(R.id.et_visor);
        visor.setShowSoftInputOnFocus(false);

        btn1.setOnClickListener(botaoCLick("1"));
        btn2.setOnClickListener(botaoCLick("2"));
        btn3.setOnClickListener(botaoCLick("3"));
        btn4.setOnClickListener(botaoCLick("4"));
        btn5.setOnClickListener(botaoCLick("5"));
        btn6.setOnClickListener(botaoCLick("6"));
        btn7.setOnClickListener(botaoCLick("7"));
        btn8.setOnClickListener(botaoCLick("8"));
        btn9.setOnClickListener(botaoCLick("9"));
        btn0.setOnClickListener(botaoCLick("0"));

        btnEnter.setOnClickListener((v) -> executaOperacao( calculadora::enter));
        btnAdicao.setOnClickListener((v) -> executaOperacao(calculadora::soma));
        btnSubtracao.setOnClickListener((v) -> executaOperacao(calculadora::subtracao));
        btnMultiplicacao.setOnClickListener((v) -> executaOperacao(calculadora::multiplicacao));
        btnDivisao.setOnClickListener((v) -> executaOperacao(calculadora::divisao));

        btnClear.setOnClickListener((v) -> executaOperacao(() -> calculadora = new Calculadora()));
        btnBackSpace.setOnClickListener((v) -> executaBackSpace());
        btnVirgula.setOnClickListener((v) -> executaVirgula());
        btnPV.setOnClickListener((V) -> executaPV());
        btnFV.setOnClickListener((V) -> executaFV());
        btnPMT.setOnClickListener((V) -> executaPMT());
        btnTaxa.setOnClickListener((V) -> executaTaxa());
        btnN.setOnClickListener((V) -> exevutaN());
    }

    private void exevutaN() {
        if (isModoExibindo()) {
            String valorN = calculadora.calcularPeriodo().toString();
            visor.setText(valorN);
        }
        else {
            if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                String visorText = visor.getText().toString().replace(",", ".");
                double valor = Double.parseDouble(visorText);
                calculadora.setnPeriodo(valor);
            } else {
                calculadora.setnPeriodo(0.0);
            }
            visor.setText("0,0");
        }
        calculadora.setModo(Calculadora.MODO_EXIBINDO);
    }

    private void executaTaxa() {
        if (isModoExibindo()) {
            String valorTaxa = calculadora.calculaTaxa().toString();
            visor.setText(valorTaxa);
        }
        else {
            if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                String visorText = visor.getText().toString().replace(",", ".");
                double valor = Double.parseDouble(visorText);
                calculadora.setiTaxa(valor/100);
            } else {
                double valor = 0.0;
                calculadora.setiTaxa(valor/100);
            }
            visor.setText("0,0");
        }
        calculadora.setModo(Calculadora.MODO_EXIBINDO);
    }

    private void executaPMT() {
        if (isModoExibindo()) {
            calculadora.calculaPMT();
            String valuePMT = calculadora.getPmt().toString();
            visor.setText(valuePMT);
        }
        else {
            if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                String visorText = visor.getText().toString().replace(",", ".");
                double valor = Double.parseDouble(visorText);
                calculadora.setPmt(valor);
            } else {
                double valor = 0.0;
                calculadora.setPmt(valor);
            }
            visor.setText("0,0");
        }
        calculadora.setModo(Calculadora.MODO_EXIBINDO);
    }

    private void executaPV() {
        if (isModoExibindo()) {
            String valorPV = calculadora.calculaPV().toString();
            visor.setText(valorPV);
        }
        else {
            if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                String visorText = visor.getText().toString().replace(",", ".");
                double valor = Double.parseDouble(visorText);
                calculadora.setPv(valor);
            } else {
                double valor = 0.0;
                calculadora.setPv(valor);
            }
            visor.setText("0,0");
        }
        calculadora.setModo(Calculadora.MODO_EXIBINDO);
    }

    private void executaFV() {
        if (isModoExibindo()) {
            String valorFV = calculadora.calculAFV().toString();
            visor.setText(valorFV);
        }
        else {
            if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                String visorText = visor.getText().toString().replace(",", ".");
                double valor = Double.parseDouble(visorText);
                calculadora.setFv(valor);
            } else {
                double valor = 0.0;
                calculadora.setFv(valor);
            }
            visor.setText("0,0");
        }
        calculadora.setModo(Calculadora.MODO_EXIBINDO);
    }

    private void executaVirgula() {
        if ( isModoExibindo() ){
            visor.setText("0,");
            visor.setSelection(2,2);
            calculadora.setModo(Calculadora.MODO_EDITANDO);
        }

        if (calculadora.getModo() != Calculadora.MODO_ERROR
                && !visor.getText().toString().contains(",")
        )  {
            String valorComVirgula = visor.getText() + ",";
            visor.setText(valorComVirgula);
            visor.setSelection(visor.getText().length());
        }
    }

    public View.OnClickListener botaoCLick(final String s) {
        return (v) -> {
            if(isModoExibindo()) visor.setText("");

            int inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();

            visor.getText().replace(inicioSelecao, finalSelecao, s);
            atualizaNumero();
        };
    }

    public void executaOperacao(Runnable operacao){
        operacao.run();
        atualizaVisor();
    }

    private void executaBackSpace(){
        int inicioSelecao = visor.getSelectionStart() - 1;
        int finalSelecao = visor.getSelectionEnd();

        inicioSelecao = Math.max(inicioSelecao, 0);

        visor.getText().delete(inicioSelecao, finalSelecao);
    }
    public void atualizaNumero(){
        String s = visor.getText().toString();
        s = s.isEmpty() ? "0" : s;
        s = s.replace(',','.');
        calculadora.setNumero(Double.parseDouble(s));
    }

    @SuppressLint("DefaultLocale")
    public void atualizaVisor(){
        double numero = calculadora.getNumero();
        visor.setText(String.format("%.2f", numero));
    }

    public boolean isModoExibindo() {
        return  calculadora.getModo() == Calculadora.MODO_EXIBINDO;
    }
}