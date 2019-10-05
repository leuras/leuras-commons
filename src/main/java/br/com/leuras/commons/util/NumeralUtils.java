package br.com.leuras.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public final class NumeralUtils {

    public static final Integer ZERO = 0;

    public static final Integer UM = 1;

    public static final Integer DOIS = 2;

    public static final Integer CINCO = 5;

    public static final Integer DEZ = 10;

    public static final Integer VINTE = 20;

    public static final Integer TRINTA = 30;

    public static final Integer QUARENTA = 40;

    public static final Integer CINQUENTA = 50;

    public static final Integer SESSENTA = 60;

    public static final Integer SETENTA = 70;

    public static final Integer OITENTA = 80;

    public static final Integer NOVENTA = 90;

    public static final Integer CEM = 100;

    protected static final String ME001 = "Tipo de dado inválido. Use apenas: BigDecimal, Double, Integer e Long.";
    
    private NumeralUtils() {

    }

    /**
     * Converte um valor numérico representado por meio de uma string em um número.
     * 
     * @param numero
     *            Valor numérico a ser convertido
     * @param formatador
     *            Formatador que irá realizar a conversão
     * @param clazz
     *            Classe resultante da conversão. Tipos suportados: BigDecimal, Double, Integer e Long.
     * @return O número convertido.
     * @throws IllegalArgumentException
     *             Caso haja algum erro de conversão ou o parâmetro <b>clazz</b> não seja suportado.
     */
    public static Number paraNumero(final String numero, final NumberFormat formatador,
            final Class<? extends Number> clazz) throws IllegalArgumentException {

        try {

            final Number resultado = formatador.parse(numero);

            if (BigDecimal.class == clazz) {
                return BigDecimal.valueOf(resultado.doubleValue());
            } else if (Double.class == clazz) {
                return Double.valueOf(resultado.doubleValue());
            } else if (Integer.class == clazz) {
                return Integer.valueOf(resultado.intValue());
            } else if (Long.class == clazz) {
                return Long.valueOf(resultado.longValue());
            }

            throw new IllegalArgumentException(NumeralUtils.ME001);

        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * Converte um valor de formato moeda (texto) para um número.
     * 
     * @param valor
     *            Moeda a ser convertida
     * @param clazz
     *            Classe resultante da conversão
     * @return O número convertido.
     * @throws IllegalArgumentException
     *             Caso haja algum erro de conversão ou o parâmetro <b>clazz</b> não seja suportado.
     */
    public static Number deMoeda(final String valor, final Class<? extends Number> clazz)
            throws IllegalArgumentException {
    	
        Number moeda = NumeralUtils.paraNumero(valor, NumberFormat.getCurrencyInstance(Brasil.Local.BR), clazz);
        
        if (moeda instanceof BigDecimal) {
            moeda = ((BigDecimal) moeda).setScale(NumeralUtils.DOIS);
        }
        
        return moeda;
    }

    /**
     * Converte um número para um valor em formato moeda (texto).
     * 
     * @param valor
     *            Número a ser convertido
     * @return O valor em formato moeda.
     */
    public static String paraMoeda(final Number valor) {
        final NumberFormat formatador = NumberFormat.getCurrencyInstance(Brasil.Local.BR);

        return formatador.format(valor);
    }

    /**
     * Converte um valor percentual (texto) para um número.
     * 
     * @param porcentagem
     *            Percentual a ser convertido
     * @param clazz
     *            Classe resultante da conversão
     * @return O número convertido.
     * @throws IllegalArgumentException
     *             Caso haja algum erro de conversão ou o parâmetro <b>clazz</b> não seja suportado.
     */
    public static Number dePorcentagem(final String porcentagem, final Class<? extends Number> clazz)
            throws IllegalArgumentException {
        return NumeralUtils.paraNumero(porcentagem, NumberFormat.getPercentInstance(Brasil.Local.BR), clazz);
    }

    /**
     * Converte um número para um valor percentual (texto) usando duas casas decimais.
     * 
     * @see #paraPorcentagem(Number, Integer) paraPorcentagem
     * 
     * @param valor
     *            Número a ser convertido
     * @return O valor em formato percentual.
     */
    public static String paraPorcentagem(final Number valor) {
        return NumeralUtils.paraPorcentagem(valor, NumeralUtils.DOIS);
    }

    /**
     * Converte um número para um valor percentual (texto). Este método <b>realiza a conversão de um valor numérico em
     * percentual</b> e não apenas a formatação.
     * 
     * <pre>
     * String percentual = NumeralUtils.paraPorcentagem(0.55, 2);
     * System.out.println(percentual); // 55,00%
     * </pre>
     * 
     * @param valor
     *            Número a ser convertido
     * @param precisao
     *            Número de casas decimais
     * @return O valor em formato percentual.
     */
    public static String paraPorcentagem(final Number valor, final Integer precisao) {
    	
        final NumberFormat formatador = NumberFormat.getPercentInstance(Brasil.Local.BR);

        formatador.setMaximumFractionDigits(precisao);
        formatador.setMinimumFractionDigits(precisao);

        return formatador.format(valor);
    }

    /**
     * Formata um número como um valor percentual (texto) usando duas casas decimais.
     * 
     * @see #comoPorcentagem(Number, Integer) comoPorcentagem
     * 
     * @param valor
     *            Número a ser formatado
     * @return O valor em formato percentual.
     */
    public static String comoPorcentagem(final Number valor) {
        return NumeralUtils.comoPorcentagem(valor, NumeralUtils.DOIS);
    }

    /**
     * Formata um número como um valor percentual (texto).
     * 
     * <pre>
     * String percentual = NumeralUtils.comoPorcentagem(33.3d, 2);
     * System.out.println(percentual); // 33,30%
     * </pre>
     * 
     * @param valor
     *            Número a ser formatado
     * @param precisao
     *            Número de casas decimais
     * @return O valor em formato percentual.
     */
    public static String comoPorcentagem(final Number valor, final Integer precisao) {
        final String formato = String.format(Brasil.Local.BR, "%%.%df%%%%", precisao);

        return String.format(formato, valor);
    }

    /**
     * Calcula o percentual de <b>x</b> sobre o <b>total</b>.
     * 
     * @param x
     *            Parte do montante a ser calculada
     * @param total
     *            Montante
     * @param precisao
     *            Número de casas decimais
     * @return A porcentagem calculada sobre o montante
     */
    public static BigDecimal percentualDe(final Integer x, final Integer total, final int precisao) {
        return NumeralUtils.percentualDe(new BigDecimal(x), new BigDecimal(total), precisao);
    }

    /**
     * Calcula o percentual de <b>x</b> sobre o <b>total</b>.
     * 
     * @param x
     *            Parte do montante a ser calculada
     * @param total
     *            Montante
     * @param precisao
     *            Número de casas decimais
     * @return A porcentagem calculada sobre o montante
     */
    public static BigDecimal percentualDe(final Long x, final Long total, final int precisao) {
        return NumeralUtils.percentualDe(new BigDecimal(x), new BigDecimal(total), precisao);
    }

    /**
     * Calcula o percentual de <b>x</b> sobre o <b>total</b>.
     * 
     * @param x
     *            Parte do montante a ser calculada
     * @param total
     *            Montante
     * @param precisao
     *            Número de casas decimais
     * @return A porcentagem calculada sobre o montante
     */
    public static BigDecimal percentualDe(final Double x, final Double total, final int precisao) {
        return NumeralUtils.percentualDe(new BigDecimal(x), new BigDecimal(total), precisao);
    }

    /**
     * Calcula o percentual de <b>x</b> sobre o <b>total</b>.
     * 
     * @param x
     *            Parte do montante a ser calculada
     * @param total
     *            Montante
     * @param precisao
     *            Número de casas decimais
     * @return A porcentagem calculada sobre o montante
     */
    public static BigDecimal percentualDe(final BigDecimal x, final BigDecimal total, final int precisao) {
        return x.multiply(new BigDecimal(NumeralUtils.CEM)).divide(total, precisao, RoundingMode.HALF_EVEN);
    }

    /**
     * Remove todos os caracteres não númericos do valor dado.
     * 
     * @param valor
     *            String contendo caracteres especiais e letras
     * @return Uma string contendo apenas números.
     */
    public static String somenteNumeros(final String valor) {
        return valor.replaceAll("[^0-9]", StringUtils.EMPTY);
    }
}
