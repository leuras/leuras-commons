package br.com.leuras.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;

import org.apache.commons.lang3.StringUtils;
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
	public void criarUsandoCaminhoEConteudoStringTest() throws Exception {
		
		// Cenário
		final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String caminho = arquivo.getAbsolutePath();
        final String conteudo = "Em pé sem cair, deitado sem dormir, sentado sem cochilar e fazendo pose.";
        
        // Ação
        ArquivoUtils.criar(caminho, conteudo);
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
	}
	
	@Test
	public void criarUsandoArquivoEConteudoStringTest() throws Exception {
		
		// Cenário
		final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String conteudo = "Mussum Ipsum, cacilds vidis litro abertis.";
        
        // Ação
        ArquivoUtils.criar(arquivo, conteudo);
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
	}
	
	@Test
	public void criarUsandoCaminhoEConteudoBytesTest() throws Exception {
		
		// Cenário
		final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String caminho = arquivo.getAbsolutePath();
        final String conteudo = "Cevadis im ampola pa arma uma pindureta.";
        
        // Ação
        ArquivoUtils.criar(caminho, conteudo.getBytes());
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
	}
	
	@Test
	public void criarUsandoArquivoEConteudoBytesTest() throws Exception {
		
		// Cenário
		final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String conteudo = "A ordem dos tratores não altera o pão duris.";
        
        // Ação
        ArquivoUtils.criar(arquivo, conteudo.getBytes());
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
	}
	
	@Test
	public void criarConteudoStringNuloTest() throws Exception {
		
		// Cenário
		final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        
		String msg = StringUtils.EMPTY;
		
        // Ação
		try {
	        ArquivoUtils.criar(arquivo, (String) null);
	        Assert.fail();
		} catch (IllegalArgumentException e) {
			msg = e.getMessage();
		}
		
		Assert.assertEquals(ArquivoUtils.ME001, msg);
	}
	
	@Test
	public void criarConteudoBytesNuloTest() throws Exception {
		
		// Cenário
		final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        
		String msg = StringUtils.EMPTY;
		
        // Ação
		try {
	        ArquivoUtils.criar(arquivo, (byte[]) null);
	        Assert.fail();
		} catch (IllegalArgumentException e) {
			msg = e.getMessage();
		}
		
		Assert.assertEquals(ArquivoUtils.ME001, msg);
	}
	
	@Test
    public void escreverUsandoCaminhoEConteudoStringTest() throws Exception {
    	
        // Cenário
    	final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
    	final String caminho = arquivo.getAbsolutePath();
        final String conteudo = "Suco de cevadiss, é um leite divinis.";
        
        // Ação
        ArquivoUtils.escrever(caminho, conteudo);
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
    }
    
    @Test
    public void escreverUsandoArquivoEConteudoStringTest() throws Exception {
    	
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
    public void escreverUsandoCaminhoEConteudoBytesTest() throws Exception {
    	
        // Cenário
    	final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
    	final String caminho = arquivo.getAbsolutePath();
        final String conteudo = "Praesent lacinia ultrices consectetur.";
        
        // Ação
        ArquivoUtils.escrever(caminho, conteudo.getBytes());
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
    }
    
    @Test
    public void escreverUsandoArquivoEConteudoBytesTest() throws Exception {
    	
        // Cenário
    	final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        final String conteudo = "Posuere libero varius.";
        
        // Ação
        ArquivoUtils.escrever(arquivo, conteudo.getBytes());
        
        // Verificação
        Assert.assertTrue(arquivo.exists());
        Assert.assertTrue(arquivo.length() > 0L);
    }
    
    @Test
    public void escreverConteudoStringNuloTest() throws Exception {
    	
        // Cenário
    	String msg = StringUtils.EMPTY;
        final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        
        // Ação
        try {
            ArquivoUtils.escrever(arquivo, (String) null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	msg = e.getMessage();
        }
        
        // Verificação
        Assert.assertEquals(msg, ArquivoUtils.ME001);
    }
    
    @Test
    public void escreverConteudoBytesNuloTest() throws Exception {
    	
    	// Cenário
    	String msg = StringUtils.EMPTY;
        final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
        
        // Ação
        try {
            ArquivoUtils.escrever(arquivo, (byte[]) null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	msg = e.getMessage();
        }
        
        // Verificação
        Assert.assertEquals(msg, ArquivoUtils.ME001);
    }
    
    @Test
    public void escreverEmArquivoExistenteTest() throws Exception {
    	
    	// Cenário
    	final File arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO);
    	
    	final String parte1 = "Lorem ipsum dolor sit amet." + ArquivoUtils.QUEBRAR_LINHA;
    	final String parte2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis libero.";
    	
    	// Ação
    	ArquivoUtils.criar(arquivo, parte1);
    	ArquivoUtils.escrever(arquivo, parte2);
    	
    	// Verificação
    	final String esperado = parte1 + parte2;
    	final String resultado = new String(ArquivoUtils.lerTudo(arquivo));
    	
    	Assert.assertEquals(esperado, resultado);
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
        Assert.assertTrue(hnd instanceof FileInputStream);
    }
    
    @Test
    public void abrirParaEscritaTest() throws Exception {
    	
        // Cenário
        final String arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO).getAbsolutePath();
        
        // Ação
        final FileOutputStream hnd = ArquivoUtils.abrirParaEscrita(arquivo);
        
        // Verificação
        Assert.assertTrue(hnd instanceof FileOutputStream);
    }
    
    @Test
    public void abrirParaEdicaoTest() throws Exception {
    	
        // Cenário
        final String arquivo = File.createTempFile(PREFIXO_ARQ, EXTENSAO).getAbsolutePath();
        
        // Ação
        final FileOutputStream hnd = ArquivoUtils.abrirParaEdicao(arquivo);
        
        // Verificação
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
