package br.com.leuras.commons.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import br.com.leuras.commons.enumerator.SimNaoEnum;
import br.com.leuras.commons.exception.ExportacaoExcelException;
import br.com.leuras.commons.util.DataUtils;
import br.com.leuras.commons.util.NumeralUtils;
import br.com.leuras.commons.util.ObjetoUtils;

public class FormatoExportavelExcel<T> implements FormatoExportavel<T> {
    
    private Class<T> type;
    
    private HSSFSheet planilha;
    
    private HSSFWorkbook documento;
    
    private List<String> colunas = new ArrayList<>();
    
    /**
     * Constrói uma nova instância de FormatoExportavelExcel.
     * 
     * @param type
     *            Classe
     */
    public FormatoExportavelExcel(final Class<T> type) {
        this.type = type;
        
        final Field[] atributos = this.type.getDeclaredFields();
        
        for (final Field atributo : atributos) {
            if (atributo.isAnnotationPresent(AtributoExportavel.class)) {
                final AtributoExportavel anotacao = atributo.getAnnotation(AtributoExportavel.class);
                
                if (! anotacao.ignorar() && anotacao.coluna() != null) {
                    this.colunas.add(anotacao.coluna());
                }
            }
        }
        
        this.documento = new HSSFWorkbook();
        this.planilha = this.documento.createSheet();
    }

    @Override
    public void cabecalho(final Map<String, Object> parametros) throws ExportacaoExcelException {
        
        final Exportavel anotacao = this.type.getAnnotation(Exportavel.class);
        
        if (! anotacao.titulo().isEmpty()) {
            
            final HSSFRow linhaCabecalho = this.planilha.createRow(NumeralUtils.ZERO);
            final CellRangeAddress regiao = new CellRangeAddress(0, 0, 0, this.colunas.size() - 1);
            
            this.planilha.addMergedRegion(regiao);
            
            final HSSFCell celula = linhaCabecalho.createCell(NumeralUtils.ZERO);
            
            celula.setCellValue(new HSSFRichTextString(anotacao.titulo()));
            celula.setCellStyle(this.getEstiloCabecalho());
        }
    }

    @Override
    public void detalhes(List<T> registros) throws ExportacaoExcelException {
        
        this.cabecalhoDetalhe();        
                
        int linha = NumeralUtils.DOIS;
                
        for (T registro : registros) {
            
            final Field[] atributos = this.type.getDeclaredFields();
            final HSSFRow linhaDetalhe = this.planilha.createRow(linha);
            
            int coluna = NumeralUtils.ZERO;

            for (final Field atributo : atributos) {
                if (atributo.isAnnotationPresent(AtributoExportavel.class)) {
                    final AtributoExportavel anotacao = atributo.getAnnotation(AtributoExportavel.class);

                    if (!anotacao.ignorar()) {
                        try {
                            
                            final Object valor = ObjetoUtils.invokeGet(registro, atributo);
                            final HSSFCell celula = linhaDetalhe.createCell(coluna);
                            
                            if (valor instanceof Boolean) {
                                celula.setCellValue(((Boolean) valor) ? SimNaoEnum.SIM.getLabel() : SimNaoEnum.NAO.getLabel());
                            } else if (valor instanceof Calendar) {
                                celula.setCellValue(this.comoData(anotacao.formato(), ((Calendar) valor).getTime()));
                            } else if (valor instanceof Date) {
                                celula.setCellValue(this.comoData(anotacao.formato(), (Date) valor));
                            } else if (valor instanceof Double) {
                                celula.setCellValue((Double) valor);
                            } else if (valor instanceof Number) {
                                if (! FormatoAtributoEnum.NENHUM.equals(anotacao.formato())) {
                                    celula.setCellValue(this.comoNumero(anotacao.formato(), (Number) valor));
                                } else {
                                    celula.setCellValue(((Number) valor).doubleValue());
                                }
                            } else if (valor instanceof String) {
                                celula.setCellValue((String) valor);
                            }
                        } catch (Exception e) {
                            throw new ExportacaoExcelException(e.getMessage(), e);
                        }
                    }
                }
                
                coluna++;
            }
            
            linha++;
        }
    }

    @Override
    public void rodape(final Map<String, Object> parametros) throws ExportacaoExcelException {
        
    }

    @Override
    public byte[] getBytes() throws ExportacaoExcelException {
        
        for (int celula = 0; celula < this.colunas.size(); celula++) {
            this.planilha.autoSizeColumn(celula);
        }
        
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            
            this.documento.write(out);
            return out.toByteArray();
            
        } catch (IOException e) {
            throw new ExportacaoExcelException(e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new ExportacaoExcelException(e.getMessage(), e);
            }
        }
    }
    
    @Override
    public Class<T> getType() {
        return this.type;
    }
    
    /**
     * Responsável por escrever o cabeçalho do detalhe do documento exportado.
     */
    protected void cabecalhoDetalhe() {
        
        final HSSFRow linhaCabecalhoDetalhe = this.planilha.createRow(NumeralUtils.UM);

        for (int posicao = 0; posicao < this.colunas.size(); posicao++) {
            final HSSFCell celula = linhaCabecalhoDetalhe.createCell(posicao);
            
            celula.setCellValue(this.colunas.get(posicao));
            celula.setCellStyle(this.getEstiloCabecalhoDetalhe());
        }
    }
    
    /**
     * Obtém o estilo de formatação utilizado para decorar as células do <b>cabeçalho</b>.
     * 
     * @return Estilo que é aplicado ao cabeçalho.
     */
    protected HSSFCellStyle getEstiloCabecalho() {
        
        final HSSFCellStyle estilo = this.documento.createCellStyle();
        final HSSFFont fonte = this.documento.createFont();
        
        fonte.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fonte.setFontHeightInPoints(NumeralUtils.VINTE.shortValue());
        fonte.setUnderline(NumeralUtils.CINCO.byteValue());
        
        estilo.setFont(fonte);
        
        return estilo;
    }
    
    /**
     * Obtém o estilo de formatação utilizado para decorar as células do <b>cabeçalho do detalhe</b> (nome das colunas).
     * 
     * @return Estilo que é aplicado ao cabeçalho do detalhe.
     */
    protected HSSFCellStyle getEstiloCabecalhoDetalhe() {
        
        final HSSFCellStyle estilo = this.documento.createCellStyle();
        final HSSFFont fonte = this.documento.createFont();
        
        fonte.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fonte.setFontHeightInPoints(NumeralUtils.DEZ.shortValue());
        
        estilo.setFont(fonte);
        
        return estilo;
    }
    
    /**
     * Formata uma data utilizando o formato espeficicado.
     * 
     * @param formato
     *            Formatação desejada
     * @param data
     *            Data
     * @return A data formatada.
     */
    protected String comoData(final FormatoAtributoEnum formato, final Date data) {
        
        if (FormatoAtributoEnum.DATA.equals(formato)) {
            return DataUtils.formatar(data, DataUtils.Formato.PADRAO);
        }
        
        if (FormatoAtributoEnum.HORA.equals(formato)) {
            return DataUtils.formatar(data, DataUtils.Formato.PADRAO_COMPLETO);
        }
        
        if (FormatoAtributoEnum.TIMESTAMP.equals(formato)) {
            return DataUtils.formatar(data, DataUtils.Formato.HORARIO);
        }
        
        return data.toString();
    }
    
    /**
     * Formata um valor numérico utilizando o formato espeficicado.
     * 
     * @param formato
     *            Formatação desejada
     * @param valor
     *            valor númerico
     * @return O número formatado.
     */
    protected String comoNumero(final FormatoAtributoEnum formato, final Number valor) {
        
        final Double numero = new Double(((Number) valor).doubleValue());
        
        if (FormatoAtributoEnum.MOEDA.equals(formato)) {
            return NumeralUtils.paraMoeda(numero);
        }
        
        if (FormatoAtributoEnum.PERCENTUAL.equals(formato)) {
            return NumeralUtils.paraPorcentagem(numero);
        }
        
        throw new IllegalArgumentException();
    }
}
