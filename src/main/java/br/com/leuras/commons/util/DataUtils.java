package br.com.leuras.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public final class DataUtils {

    public static final class Formato {

        public static final String PADRAO = "dd/MM/yyyy";
        
        public static final String PADRAO_COMPLETO = "dd/MM/yyyy HH:mm";
        
        public static final String UNIVERSAL = "yyyy-MM-dd";
        
        public static final String UNIVERSAL_COMPLETO = "yyyy-MM-dd HH:mm:ss";
        
        public static final String DIA_E_MES = "dd/MM";
        
        public static final String MES_E_ANO = "MM/yyyy";
        
        public static final String ANO_CURTO = "yy";

        public static final String ANO_LONGO = "yyyy";

        public static final String HORARIO = "HH:mm";
        
        public static final String HORARIO_COMPLETO = "HH:mm:ss";
        
        private Formato() {
            
        }
    }

    private static final int MILISEGUNDOS_DIA = 86400000;

    private DataUtils() {

    }

    /**
     * Formata uma data utilizando o padrão espeficicado.
     * 
     * @param data
     *            Data a ser formatada
     * @param formato
     *            Formatação desejada
     * @return A data formatada.
     */
    public static String formatar(final Date data, final String formato) {

        if (data != null) {
            final SimpleDateFormat formatador = new SimpleDateFormat(formato);

            return formatador.format(data);
        }

        return StringUtils.EMPTY;
    }

    /**
     * Retorna a diferença em dias entre duas datas.
     * 
     * @param d1
     *            Data início
     * @param d2
     *            Data fim
     * @return A diferença em dias.
     */
    public static int diff(final Date d1, final Date d2) {

        final Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        c1.set(Calendar.MILLISECOND, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.HOUR_OF_DAY, 0);

        final Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        c2.set(Calendar.MILLISECOND, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.HOUR_OF_DAY, 0);

        return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / MILISEGUNDOS_DIA);
    }

    /**
     * Tenta converter uma string no formato <i>dd/MM/yyyy</i> para um objeto do
     * tipo <b>Date</b>.
     * 
     * @param data
     *            String contendo a data formatada
     * @param valorPadrao
     *            Instância do objeto <b>Date</b> padrão que será retornada caso
     *            haja algum erro de conversão
     * @return A data obtida a partir da string.
     */
    public static Date paraData(final String data, final Date valorPadrao) {
        return DataUtils.paraData(data, valorPadrao, Formato.UNIVERSAL_COMPLETO);
    }

    /**
     * Tenta converter uma string no formato especificado para um objeto do tipo
     * <b>Date</b>.
     * 
     * @param data
     *            String contendo a data formatada.
     * @param valorPadrao
     *            Instância do objeto <b>Date</b> padrão que será retornada caso
     *            haja algum erro de conversão
     * @param formato
     *            Formatação desejada
     * @return A data obtida a partir da string.
     */
    public static Date paraData(final String data, final Date valorPadrao, final String formato) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(formato);

        dateFormat.setLenient(false);

        try {
            return dateFormat.parse(data);
        } catch (ParseException e) {
            return valorPadrao;
        }
    }

    /**
     * Retorna os últimos dois dígitos do ano da data informada.
     * 
     * @param data
     *            Data que se deseja extrair o ano
     * @return Ano com dois dígitos.
     * @throws NumberFormatException
     *             Caso haja algum erro de conversão do ano obtido para inteiro.
     */
    public static Integer getAnoDoisDigitos(final Calendar data) throws NumberFormatException {
        final SimpleDateFormat formatador = new SimpleDateFormat(Formato.ANO_CURTO);
        
        return Integer.valueOf(formatador.format(data.getTime()));
    }

    /**
     * Retorna a data informada por parâmetro com o horário definido para 23:59:59.
     * 
     * @param data
     *            Data original
     * @return Data com o horário reajustado.
     */
    public static Date setUltimaHoraDia(final Date data) {

        final Calendar c = Calendar.getInstance();

        c.setLenient(false);
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTime();
    }

    /**
     * Retorna a data informada por parâmetro com o horário definido para 00:00:00.
     * 
     * @param data
     *            Data original
     * @return Data com o horário reajustado.
     */
    public static Date setPrimeiraHoraDia(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setLenient(false);
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }
    
    /**
     * Retorna a data por extenso, exemplo: <i>18 de Janeiro de 1981</i>.
     * 
     * @param data
     *            Data
     * @return Data por extenso.
     */
    public static String porExtenso(final Date data) {
        
        final Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        
        return DataUtils.porExtenso(calendario);
    }
    
    /**
     * Retorna a data por extenso, exemplo: <i>18 de Janeiro de 1981</i>.
     * 
     * @param data
     *            Data
     * @return Data por extenso.
     */
    public static String porExtenso(final Calendar data) {
        
        final String mes = data.getDisplayName(Calendar.MONTH, Calendar.LONG, Brasil.Local.BR);
        
        return String.format("%02d de %s de %d", data.get(Calendar.DAY_OF_MONTH), mes, data.get(Calendar.YEAR));
    }
}
