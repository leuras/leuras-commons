package br.com.leuras.commons.rede;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Test;

import br.com.leuras.commons.builder.CorreioEletronicoBuilder;
import br.com.leuras.commons.util.ArquivoUtils;
import junit.framework.Assert;

public class CorreioEletronicoTest {
    
    private static final String FULANO = "fulano@mail.com";
    
    private static final String BELTRANO = "beltrano@mail.com";
    
    private static final String SICRANO = "sicrano@mail.com";
    
    private CorreioEletronicoBuilder builder = null; 
    
    @Before
    public void setup() throws EmailException {
    	
         this.builder = new CorreioEletronicoBuilder()
        		 .comServidorSmtp("smtp.mailtrap.io")
        		 .comCredenciais("688ee315279a67", "8f117231c31466")
                 .comPorta(25)
                 .comTLS(false)
                 .comRemetente(FULANO)
                 .comCorpo("Mé faiz elementum girarzis, nisi eros vermeio.");
    }
    
    @Test
    public void builderTest() throws EmailException, IOException {
        
    	// Cenário
        this.builder
        	.comDestinatarios(BELTRANO)
            .comCopiaPara(SICRANO);
        
        // Ação
        final CorreioEletronico correio = this.builder.build();
        
        // Verificação
        Assert.assertEquals(BELTRANO, correio.getMail().getToAddresses().get(0).getAddress());
        Assert.assertEquals(SICRANO, correio.getMail().getCcAddresses().get(0).getAddress());
    }
    
    @Test
    public void emailSobrescrevendoCorpoTest() throws EmailException, IOException {
        
    	this.builder.comAssunto("Leuras Commons API - Sobrescrever Corpo e Destinatários")
    		.comDestinatarios(BELTRANO)
    		.build()
    		.enviar("Bacon ipsum dolor amet pig picanha pork loin pork buffalo.", SICRANO);
    }
    
    @Test
	public void emailPorTemplateComAnexoTest() throws EmailException, IOException {

		// Cenário
    	final List<String> expressoes = new ArrayList<>();
		final Map<String, Object> variaveis = new HashMap<>();
		
		expressoes.add("Quem tem tempo caga longe");
		expressoes.add("Lá ele");
		expressoes.add("É barril");
		expressoes.add("Boca de me dê");
		expressoes.add("Afine o seu pescoço");
		expressoes.add("Farinha pouca, meu pirão primeiro");
		
		variaveis.put("expressoes", expressoes);
		
		final File arquivo = File.createTempFile("leuras_", ".txt");
        
        ArquivoUtils.escrever(arquivo, "Mussum Ipsum, cacilds vidis litro abertis.");

		// Ação
		this.builder.comAssunto("Leuras Commons API - Usando Templates & Anexo")
			.comDestinatarios(FULANO)
			.comAnexo(arquivo)
			.build()
			.enviar("templates/exemplo.twig", variaveis, SICRANO);
	}

}
