package br.com.leuras.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public final class RelatorioUtils {

    private RelatorioUtils() {

    }

    /**
     * Processa e exporta um documento jasper em formato <i>PDF</i>.
     * 
     * @param jasper
     *            Arquivo Jasper
     * @param parametros
     *            Parâmetros a serem passados para o documento
     * @param dados
     *            Coleção de dados utilizada como fonte de dados pelo documento
     * @return Conteúdo do documento <i>PDF</i> em forma de um array de bytes.
     * @throws JRException
     *             Caso algum erro de processamento do jasper ocorra durante a exportação para <i>PDF</i>.
     */
    public static byte[] emPdf(final String jasper, final Map<String, Object> parametros, List<?> dados)
            throws JRException {

        final JRDataSource datasource = RelatorioUtils.getDataSource(dados);
        final byte[] buffer = JasperRunManager.runReportToPdf(jasper, parametros, datasource);

        return buffer;
    }

    /**
     * Processa e exporta um documento jasper em formato <i>HTML</i>.
     * 
     * @param jasper
     *            Arquivo Jasper <i>(*.jasper)</i>
     * @param parametros
     *            Parâmetros a serem passados para o documento
     * @param dados
     *            Coleção de dados utilizada como fonte de dados pelo documento
     * @return Conteúdo do documento <i>HTML</i> em forma de um array de bytes.
     * @throws JRException
     *             Caso algum erro de processamento do jasper ocorra durante a exportação para <i>HTML</i>.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] emHtml(final String jasper, final Map<String, Object> parametros, List<?> dados)
            throws JRException, IOException {

        final JRDataSource datasource = RelatorioUtils.getDataSource(dados);
        final String temporiario = ArquivoUtils.TEMP + ArquivoUtils.SEPARADOR + System.currentTimeMillis() + Math.random();

        JasperRunManager.runReportToHtmlFile(jasper, temporiario, parametros, datasource);

        final FileInputStream stream = ArquivoUtils.abrirParaLeitura(temporiario);
        final byte[] buffer = new byte[stream.available()];

        stream.read(buffer, 0, stream.available());
        stream.close();

        Files.deleteIfExists(Paths.get(temporiario));

        return buffer;
    }

    /**
     * Processa e exporta um documento jasper em formato <i>XLS</i>.
     * 
     * @param jasper
     *            Arquivo Jasper <i>(*.jasper)</i>
     * @param parametros
     *            Parâmetros a serem passados para o documento
     * @param dados
     *            Coleção de dados utilizada como fonte de dados pelo documento
     * @return Conteúdo do documento <i>XLS</i> em forma de um array de bytes.
     * @throws JRException
     *             Caso algum erro de processamento do jasper ocorra durante a exportação para <i>XLS</i>.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] emExcel(final String jasper, final Map<String, Object> parametros, final List<?> dados)
            throws JRException, IOException {
        return RelatorioUtils.emExcel(jasper, parametros, dados, new SimpleXlsReportConfiguration());
    }

    /**
     * Processa e exporta um documento jasper em formato <i>XLS</i>.
     * 
     * @param jasper
     *            Arquivo Jasper <i>(*.jasper)</i>
     * @param parametros
     *            Parâmetros a serem passados para o documento
     * @param dados
     *            Coleção de dados utilizada como fonte de dados pelo documento
     * @param opcoes
     *            Coleção contendo os parâmetros de exportação <i>XLS</i>
     * @return Conteúdo do documento <i>XLS</i> em forma de um array de bytes.
     * @throws JRException
     *             Caso algum erro de processamento do jasper ocorra durante a exportação para <i>XLS</i>.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] emExcel(final String jasper, final Map<String, Object> parametros, final List<?> dados,
            final SimpleXlsReportConfiguration opcoes) throws JRException, IOException {

        final JRDataSource datasource = RelatorioUtils.getDataSource(dados);

        final JasperReport artefato = (JasperReport) JRLoader.loadObjectFromFile(jasper);
        final JasperPrint documento = JasperFillManager.fillReport(artefato, parametros, datasource);

        final JRXlsExporter xls = new JRXlsExporter();
        final OutputStreamExporterOutput stream = new SimpleOutputStreamExporterOutput(new ByteArrayOutputStream());

        xls.setExporterInput(new SimpleExporterInput(documento));
        xls.setExporterOutput(stream);
        xls.setConfiguration(opcoes);

        xls.exportReport();

        final byte[] buffer = ((ByteArrayOutputStream) stream.getOutputStream()).toByteArray();

        stream.close();

        return buffer;
    }

    /**
     * Processa e exporta um documento jasper em formato <i>DOCX</i>.
     * 
     * @param jasper
     *            Arquivo Jasper <i>(*.jasper)</i>
     * @param parametros
     *            Parâmetros a serem passados para o documento
     * @param dados
     *            Coleção de dados utilizada como fonte de dados pelo documento
     * @return Conteúdo do documento <i>DOCX</i> em forma de um array de bytes.
     * @throws JRException
     *             Caso algum erro de processamento do jasper ocorra durante a exportação para <i>DOCX</i>.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] emWord(final String jasper, final Map<String, Object> parametros, final List<?> dados)
            throws JRException, IOException {
        return RelatorioUtils.emWord(jasper, parametros, dados, new SimpleDocxReportConfiguration());
    }

    /**
     * Processa e exporta um documento jasper em formato <i>DOCX</i>.
     * 
     * @param jasper
     *            Arquivo Jasper <i>(*.jasper)</i>
     * @param parametros
     *            Parâmetros a serem passados para o documento
     * @param dados
     *            Coleção de dados utilizada como fonte de dados pelo documento
     * @param opcoes
     *            Coleção contendo os parâmetros de exportação <i>DOCX</i>
     * @return Conteúdo do documento <i>DOCX</i> em forma de um array de bytes.
     * @throws JRException
     *             Caso algum erro de processamento do jasper ocorra durante a exportação para <i>DOCX</i>.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] emWord(final String jasper, final Map<String, Object> parametros, final List<?> dados,
            final SimpleDocxReportConfiguration opcoes) throws JRException, IOException {

        final JRDataSource datasource = RelatorioUtils.getDataSource(dados);

        final JasperReport artefato = (JasperReport) JRLoader.loadObjectFromFile(jasper);
        final JasperPrint documento = JasperFillManager.fillReport(artefato, parametros, datasource);

        final JRDocxExporter docx = new JRDocxExporter();
        final OutputStreamExporterOutput stream = new SimpleOutputStreamExporterOutput(new ByteArrayOutputStream());

        docx.setExporterInput(new SimpleExporterInput(documento));
        docx.setExporterOutput(stream);
        docx.setConfiguration(opcoes);

        docx.exportReport();

        final byte[] buffer = ((ByteArrayOutputStream) stream.getOutputStream()).toByteArray();

        stream.close();

        return buffer;
    }

    private static JRDataSource getDataSource(final List<?> dados) {

        JRDataSource datasource = null;

        if (dados != null && !dados.isEmpty()) {
            datasource = new JRBeanCollectionDataSource(dados);
        } else {
            datasource = new JREmptyDataSource();
        }
        return datasource;
    }
}
