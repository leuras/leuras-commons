package br.com.leuras.commons.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RelatorioUtilsTest {
    
    private static final String RELATORIO = "src/test/resources/exemplo.jasper";
    
    private List<Pessoa> datasource = null;
    
    private Map<String, Object> parametros = new HashMap<>();
    
    @Before
    public void setup() {
        this.datasource = new ArrayList<>();
        
        this.datasource.add(new Pessoa("Manuel Levi da Mata"));
        this.datasource.add(new Pessoa("Vitória Daiane Vieira"));
        this.datasource.add(new Pessoa("Sérgio Diego Kaique Lopes"));
        this.datasource.add(new Pessoa("Bruno Noah Elias Oliveira"));
        this.datasource.add(new Pessoa("Henry Pietro da Silva"));
        this.datasource.add(new Pessoa("Joana Malu dos Santos"));
        this.datasource.add(new Pessoa("Luiz João da Costa"));
        this.datasource.add(new Pessoa("Vitória Antonella Melissa Ribeiro"));
        
        this.parametros.put("ORGANIZACAO", "CORPORAÇÃO CAPSULA");
    }

    @Test
    public void construtorTest() throws Exception {
        final Constructor<RelatorioUtils> constructor = RelatorioUtils.class.getDeclaredConstructor();
        
        Assert.assertEquals(constructor.isAccessible(), false);
        
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }
    
    @Test
    public void emPdfTest() throws Exception {
    	
        // Cenário
        final String jasper = Paths.get(RELATORIO).toRealPath().toString();
        
        // Ação
        final byte[] buffer = RelatorioUtils.emPdf(jasper, this.parametros, this.datasource);
        
        // Verificação
        Assert.assertNotNull(buffer);
        Assert.assertTrue(buffer.length > 0);
    }
    
    @Test
    public void emHtmlTest() throws Exception {
    	
        // Cenário
        final String jasper = Paths.get(RELATORIO).toRealPath().toString();
        
        // Ação
        final byte[] buffer = RelatorioUtils.emHtml(jasper, this.parametros, this.datasource);
        
        // Verificação
        Assert.assertNotNull(buffer);
        Assert.assertTrue(buffer.length > 0);
    }
    
    @Test
    public void emExcelTest() throws Exception {
    	
        // Cenário
        final String jasper = Paths.get(RELATORIO).toRealPath().toString();
        
        // Ação
        final byte[] buffer = RelatorioUtils.emExcel(jasper, this.parametros, this.datasource);
        
        // Verificação
        Assert.assertNotNull(buffer);
        Assert.assertTrue(buffer.length > 0);
    }
    
    @Test
    public void emWordTest() throws Exception {
    	
        // Cenário
        final String jasper = Paths.get(RELATORIO).toRealPath().toString();
        final String caminho = ArquivoUtils.TEMP + ArquivoUtils.SEPARADOR + "exemplo.docx";
        
        // Ação
        final byte[] buffer = RelatorioUtils.emWord(jasper, this.parametros, this.datasource);
        
        ArquivoUtils.escrever(new File(caminho), buffer);
        
        // Verificação
        Assert.assertNotNull(buffer);
        Assert.assertTrue(buffer.length > 0);
    }
    
    @Test
    public void relatorioSemDatasourceTest() throws Exception {
    	
        // Cenário
        final String jasper = Paths.get(RELATORIO).toRealPath().toString();
        
        // Ação
        final byte[] buffer = RelatorioUtils.emWord(jasper, this.parametros, null);
        
        // Verificação
        Assert.assertNotNull(buffer);
    }
    
    public class Pessoa {
        
        private String nome;
        
        public Pessoa(final String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return this.nome;
        }
        
        public void setNome(final String nome) {
            this.nome = nome;
        }
    }

}
