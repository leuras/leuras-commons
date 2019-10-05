package br.com.leuras.commons.rede;

import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Test;

import br.com.leuras.commons.builder.CorreioEletronicoBuilder;
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
}
