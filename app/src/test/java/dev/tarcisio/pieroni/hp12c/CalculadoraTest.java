package dev.tarcisio.pieroni.hp12c;


import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import java.security.PublicKey;

public class CalculadoraTest {

    @Test
    public void testSetNumero() {
        Calculadora calc = new Calculadora();
        calc.setNumero(4);
        calc.enter();
        assertEquals(4.0, calc.getNumero());
    }

    @Test
    public void testSomarAB() {
        Calculadora calc = new Calculadora();
        calc.setNumero(2);
        calc.enter();
        calc.setNumero(2);
        calc.enter();
        calc.soma();
        assertEquals(4.0, calc.getNumero());
    }

    @Test
    public void testSubtracaoAB() {
        Calculadora calc = new Calculadora();
        calc.setNumero(2);
        calc.enter();
        calc.setNumero(4);
        calc.enter();
        calc.subtracao();
        assertEquals(2.0, calc.getNumero());
    }

    @Test
    public void testDivisaoAB() {
        Calculadora calc = new Calculadora();
        calc.setNumero(2);
        calc.enter();
        calc.setNumero(8);
        calc.enter();
        calc.divisao();
        assertEquals(4.0, calc.getNumero());
    }

    @Test
    public void  testMultiplicacaoAB() {
        Calculadora calc = new Calculadora();
        calc.setNumero(2);
        calc.enter();
        calc.setNumero(8);
        calc.enter();
        calc.multiplicacao();
        assertEquals(16.0, calc.getNumero());
    }

}
