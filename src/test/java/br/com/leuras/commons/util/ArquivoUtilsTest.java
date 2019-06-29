package br.com.leuras.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class ArquivoUtilsTest {
    
    private static final String PREFIXO_ARQ = "leuras_";
    
	private static final String EXTENSAO = ".txt";

	@Test
    public void construtorTest() throws Exception {
    	
        final Constructor<ArquivoUtils> constructor = ArquivoUtils.class.getDeclaredConstructor();
        
        Assert.assertEquals(constructor.isAccessible(), false);
        
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }
    
    @Test
    public void escreverTest() throws Exception {
    	
        // Cenário
    	final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String conteudo = "Delegadis gente finis, bibendum egestas augue arcu ut est.";
        
        // Ação
        ArquivoUtils.escrever(arquivo, conteudo);
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
    }
    
    @Test
    public void escreverStringNulaTest() throws Exception {
    	
        // Cenário
    	IllegalArgumentException exception = null;
        final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        
        // Ação
        try {
            ArquivoUtils.escrever(arquivo, (String) null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	exception = e;
        }
        
        // Verificação
        Assert.assertThat(exception.getMessage(), CoreMatchers.is(ArquivoUtils.ME001));
    }
    
    @Test
    public void lerTest() throws Exception {
    	
        // Cenário
    	final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String conteudo = "Mauris nec dolor in eros commodo tempor.";
        
        ArquivoUtils.escrever(arquivo, conteudo);
        
        // Ação
        final byte[] resultado = ArquivoUtils.lerTudo(arquivo);
        
        // Verificação
        Assert.assertThat(new String(resultado), CoreMatchers.is(conteudo));
    }
    
    @Test
    public void lerParteArquivoTest() throws Exception {
    	
        // Cenário
        final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String conteudo = "Mussum Ipsum, cacilds vidis litro abertis.";
        
        ArquivoUtils.escrever(arquivo, conteudo);
        
        // Ação
        final byte[] resultado = ArquivoUtils.ler(arquivo, 0, 4);
        
        // Verificação
        final String esperado = "Muss";
        
        Assert.assertThat(new String(resultado), CoreMatchers.is(esperado));
    }
    
    @Test
    public void abrirParaLeituraTest() throws Exception {
    	
        // Cenário
        final String arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO).getAbsolutePath();
        
        // Ação
        final FileInputStream hnd = ArquivoUtils.abrirParaLeitura(arquivo);
        
        // Verificação
        Assert.assertNotNull(hnd);
        Assert.assertTrue(hnd instanceof FileInputStream);
    }
    
    @Test
    public void abrirParaEscritaTest() throws Exception {
    	
        // Cenário
        final String arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO).getAbsolutePath();
        
        // Ação
        final FileOutputStream hnd = ArquivoUtils.abrirParaEscrita(arquivo);
        
        // Verificação
        Assert.assertNotNull(hnd);
        Assert.assertTrue(hnd instanceof FileOutputStream);
    }
    
    @Test
    public void criarEstruturaDiretoriosTest() throws Exception {
       
    	// Cenário
        final String caminho = ArquivoUtils.TEMP + ArquivoUtils.SEPARADOR + PREFIXO_ARQ + System.currentTimeMillis();
        
        // Ação
        final File diretorio = ArquivoUtils.criarEstruturaDiretorios(caminho);
        
        // Verificação
        Assert.assertNotNull(diretorio);
        Assert.assertTrue(diretorio.exists());
    }
    
    @Test
    public void getTipoMimeTest() throws Exception {
    	
        // Cenário
        final String conteudo = "<pessoas><pessoa>Fernando</pessoa></pessoas>";
        final String caminho  = ArquivoUtils.TEMP + ArquivoUtils.SEPARADOR + PREFIXO_ARQ + System.currentTimeMillis() + ".xml";
        final File arquivo = new File(caminho);
        
        // Ação
        ArquivoUtils.escrever(arquivo, conteudo);
        final String resultado = ArquivoUtils.getTipoMime(arquivo);
        
        // Verificação
        final String esperado = "application/xml";
        Assert.assertThat(resultado, CoreMatchers.is(esperado));
    }
}
