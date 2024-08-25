package dev.tarcisio.pieroni.hp12c;

import androidx.lifecycle.ViewModel;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

public class Calculadora extends ViewModel {

    public static final int MODO_EDITANDO = 0;
    public static final int MODO_EXIBINDO = 1;
    public static final int MODO_ERROR = 2;
    private double numero;
    private final Deque<Double> operandos;
    private int modo = MODO_EXIBINDO;

    private double pv;
    private double fv;
    private double pmt;
    private double iTaxa;
    private double nPeriodo;

    public Calculadora() {
        numero = 0;
        operandos = new LinkedList<>();
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
        modo = MODO_EDITANDO;
    }

    public int getModo(){
        return modo;
    }

    public void setModo(int n){
        this.modo = n;
    }

    public void enter(){
        if(modo == MODO_ERROR){
            modo = MODO_EXIBINDO;
        }
        if (modo == MODO_EDITANDO){
            operandos.push(numero);
            modo = MODO_EXIBINDO;
        }
    }

    public void setPv(double pv) {
        this.pv = pv;
    }

    public void setFv(double fv) {
        this.fv = fv;
    }

    public Double getPmt(){
        return this.pmt;
    }
    public void setPmt(double pmt) {
        this.pmt = pmt;
    }

    public void setiTaxa(double iTaxa) {
        this.iTaxa = iTaxa;
    }

    public void setnPeriodo(double nPeriodo) {
        this.nPeriodo = nPeriodo;
    }

    public void executarOperacao(BiFunction<Double, Double, Double> operacao){
        if(modo == MODO_EDITANDO || modo == MODO_ERROR){
            enter();
        }
        double op1 = Optional.ofNullable(operandos.pollFirst()).orElse(0.0);
        double op2 = Optional.ofNullable(operandos.pollFirst()).orElse(0.0);
        numero = operacao.apply(op1, op2);
        operandos.push(numero);
    }

    public void soma(){
        executarOperacao(Double::sum);
    }

    public void subtracao(){
        executarOperacao((op1, op2) -> op2 - op1);
    }

    public void multiplicacao(){
        executarOperacao((op1, op2) -> op1 * op2);
    }

    public void divisao(){
        double denominador = Optional.ofNullable(operandos.peek()).orElse(0.0);

        if(denominador == 0){
            modo = MODO_ERROR;
            return;
        }
        executarOperacao((op1, op2) -> op2 / op1);
    }

    public Double calculaPV() {
        if (nPeriodo == 0) {
            return fv;
        }
        return fv / Math.pow(1+iTaxa, nPeriodo);
    }

    public Double calculAFV() {
        if (nPeriodo == 0) {
            return pv;
        }
        return pv * Math.pow(1+iTaxa, nPeriodo);
    }

    public void calculaPMT() {
        if (nPeriodo == 0) {
            setPmt(pv / nPeriodo);
        }
        setPmt( pv * iTaxa / (1 - Math.pow(1+iTaxa, -nPeriodo)));
    }

    public Double calculaTaxa() {
        if (nPeriodo == 0) { //error
            setModo(MODO_ERROR);
            return 0.0;
        }
        return Math.pow(fv / pv, 1.0 / nPeriodo) - 1;
    }

    public Double calcularPeriodo() {
        if (iTaxa == 0) { //error
            setModo(MODO_ERROR);
            return 0.0;
        }
        return Math.log(fv / pv) / Math.log(1 + iTaxa);
    }
}