package br.com.leuras.commons.util;

import java.lang.reflect.Constructor;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjetoUtilsTest {
    
    private static final String NOME_PESSOA = "Maria Eliane Freitas";
    
    private static final String OUTRA_PESSOA = "Rosa Olivia Castro";
    
	private Pessoa pessoa;
    
    @Before
    public void setUp() {
        this.pessoa = new Pessoa();
        this.pessoa.setNome(NOME_PESSOA);
    }
    
    @Test
    public void construtorTest() throws Exception {
        final Constructor<ObjetoUtils> constructor = ObjetoUtils.class.getDeclaredConstructor();
        
        Assert.assertEquals(constructor.isAccessible(), false);
        
        constructor.setAccessible(true);
        constructor.newInstance((Object[]) null);
    }
    
    @Test
    public void invokeSetTest() throws Exception {
    	
    	// Ação
        ObjetoUtils.invokeSet(this.pessoa, this.pessoa.getClass().getDeclaredField("nome"), OUTRA_PESSOA);
        
        // Verificação
        Assert.assertThat(pessoa.getNome(), CoreMatchers.is(OUTRA_PESSOA));
    }
    
    @Test
    public void invokeGetTest() throws Exception {
    	
    	// Ação
        String resultado = (String) ObjetoUtils.invokeGet(this.pessoa, this.pessoa.getClass().getDeclaredField("nome"));
        
        // Verificação
        Assert.assertThat(resultado, CoreMatchers.is(NOME_PESSOA));
    }
    
    class Pessoa {
        
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }
}
