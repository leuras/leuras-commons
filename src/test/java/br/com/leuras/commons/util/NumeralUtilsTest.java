package br.com.leuras.commons.util;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class NumeralUtilsTest {
    
    @Test
    public void construtorTest() throws Exception {
        final Constructor<NumeralUtils> constructor = NumeralUtils.class.getDeclaredConstructor();
        
        Assert.assertEquals(constructor.isAccessible(), false);
        
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }

    @Test
    public void somenteNumerosTest() {
    	
        // Cenário
        final String numero = "ASD123QWE789zxc456.,_&*%$#@!";

        // Ação
        final String resultado = NumeralUtils.somenteNumeros(numero);

        // Verificação
        Assert.assertThat(resultado, CoreMatchers.is("123789456"));
    }

    @Test
    public void percentualDeBigDecimalTest() {
    	
        // Cenário
        final BigDecimal x = new BigDecimal(15);
        final BigDecimal t = new BigDecimal(30);

        // Ação
        final BigDecimal resultado = NumeralUtils.percentualDe(x, t, 2);

        // Verificação
        final BigDecimal esperado = new BigDecimal(50).setScale(2);

        Assert.assertThat(resultado, CoreMatchers.is(esperado));
    }

    @Test
    public void percentualDeDoubleTest() {

        // Cenário
        final Double x = 33d;
        final Double t = 45d;

        // Ação
        final BigDecimal resultado = NumeralUtils.percentualDe(x, t, 2);

        // Verificação
        BigDecimal esperado = new BigDecimal(73.33).setScale(2, RoundingMode.HALF_EVEN);

        Assert.assertThat(resultado, CoreMatchers.is(esperado));
    }

    @Test
    public void percentualDeLongTest() {

        // Cenário
        final Long x = 25L;
        final Long t = 100L;

        // Ação
        final BigDecimal resultado = NumeralUtils.percentualDe(x, t, 2);

        // Verificação
        BigDecimal esperado = new BigDecimal(25.00).setScale(2);

        Assert.assertThat(resultado, CoreMatchers.is(esperado));
    }

    @Test
    public void percentualDeIntegerTest() {
    	
        // Cenário
        final Integer x = 15;
        final Integer t = 50;

        // Ação
        final BigDecimal resultado = NumeralUtils.percentualDe(x, t, 2);

        // Verificação
        final BigDecimal esperado = new BigDecimal(30).setScale(2);

        Assert.assertThat(resultado, CoreMatchers.is(esperado));
    }

    @Test
    public void paraPorcentagemTest() {
    	
        // Cenário
        final Double valor = 0.557d;
        
        // Ação
        final String resultado = NumeralUtils.paraPorcentagem(valor);
        
        // Verificação
        Assert.assertEquals("55,70%", resultado);
    }

    @Test
    public void comoPorcentagemTest() {
    	
        // Cenário
        final Double valor = 55.7d;
        
        // Ação
        final String resultado = NumeralUtils.comoPorcentagem(valor);
        
        // Verificação
        Assert.assertEquals("55,70%", resultado);
    }

    @Test
    public void dePorcentagemTest() {
    	
        // Cenário
        final String valor = "55,70%";
        
        // Ação
        final Double resultado = (Double) NumeralUtils.dePorcentagem(valor, Double.class);
        final Double esperado = 0.557d;
        
        // Verificação
        Assert.assertEquals(esperado, resultado);
    }

    @Test
    public void paraMoedaTest() {
    	
        // Cenário
        final BigDecimal valor = new BigDecimal(37.50).setScale(2);
        
        // Ação
        final String resultado = NumeralUtils.paraMoeda(valor);
        
        // Verificação
        Assert.assertEquals("R$ 37,50", resultado);
    }

    @Test
    public void deMoedaTest() {
    	
        // Cenário
        final String valor = "R$ 37,50";
        
        // Ação
        final BigDecimal resultado = (BigDecimal) NumeralUtils.deMoeda(valor, BigDecimal.class);
        final BigDecimal esperado = new BigDecimal(37.50).setScale(2);
        
        // Verificação
        Assert.assertEquals(esperado, resultado);
    }

    @Test
    public void deMoedaDoubleTest() {
    	
        // Cenário
        final String valor = "R$ 9,99";
        
        // Ação
        final Double resultado = (Double) NumeralUtils.deMoeda(valor, Double.class);
        final Double esperado = new Double(9.99);
        
        // Verificação
        Assert.assertEquals(esperado, resultado);
    }

    @Test
    public void paraNumeroInteiroTest() {
    	
        // Cenário
        final String valor = "-1";
        final NumberFormat formatador = NumberFormat.getIntegerInstance(Brasil.Local.BR);
        
        // Ação
        final Number resultado = NumeralUtils.paraNumero(valor, formatador, Integer.class);
        
        // Verificação
        Assert.assertEquals(Integer.class, resultado.getClass());
    }

    @Test
    public void paraNumeroLongTest() {
    	
        // Cenário
        final String valor = "9999999999";
        final NumberFormat formatador = NumberFormat.getIntegerInstance(Brasil.Local.BR);
        
		// Ação
        final Number resultado = NumeralUtils.paraNumero(valor, formatador, Long.class);
        
        // Verificação
        Assert.assertEquals(Long.class, resultado.getClass());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void paraNumeroTipoInvalidoTest() {
    	
        // Cenário
        final String valor = "abc";
        
        // Ação
        NumeralUtils.paraNumero(valor, NumberFormat.getIntegerInstance(Brasil.Local.BR), Long.class);
    }

    @Test
    public void paraNumeroDadoInvalidoTest() {
    	
        // Cenário
        final String valor = "99.9";
        
        try {
            // Ação
            NumeralUtils.paraNumero(valor, NumberFormat.getIntegerInstance(Brasil.Local.BR), Float.class);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is(NumeralUtils.ME001));
        }
    }
}
