package br.com.leuras.commons.util;

import static org.hamcrest.CoreMatchers.is;

import java.lang.reflect.Constructor;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class DocumentoUtilsTest {

	@Test
	public void construtorTest() throws Exception {
		final Constructor<DocumentoUtils> constructor = DocumentoUtils.class.getDeclaredConstructor();

		Assert.assertEquals(constructor.isAccessible(), false);

		constructor.setAccessible(true);
		constructor.newInstance((Object[]) null);
	}

	@Test
	public void formataCpfTest() {
		
		// Cenário
		final String numero = "70498542041";

		// Ação
		final String resultado = DocumentoUtils.formataCpf(numero);
		final String esperado = "704.985.420-41";

		// Verificação
		Assert.assertThat(resultado, is(esperado));
	}

	@Test
	public void formataCnpjTest() {
		
		// Cenário
		final String numero = "16205908000171";

		// Ação
		final String resultado = DocumentoUtils.formataCnpj(numero);
		final String esperado = "16.205.908/0001-71";

		// Verificação
		Assert.assertThat(resultado, is(esperado));
	}

	@Test
	public void formataCpfNuloTest() {
		
		// Cenário
		IllegalArgumentException exception = null;
		
		// Ação
		try {
			DocumentoUtils.formataCpf(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		
		// Verificação
		Assert.assertThat(exception.getMessage(), CoreMatchers.is(DocumentoUtils.ME001));
	}

	@Test
	public void formataCnpjNuloTest() {
		
		// Cenário
		IllegalArgumentException exception = null;
				
		// Ação
		try {
			DocumentoUtils.formataCnpj(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		
		// Verificação
		Assert.assertThat(exception.getMessage(), CoreMatchers.is(DocumentoUtils.ME001));
	}

	@Test
	public void formataCpfInvalidoTest() {
		
		// Cenário
		IllegalArgumentException exception = null;
		
		// Ação
		final String numero = "11111111111";
		
		try {
			DocumentoUtils.formataCpf(numero);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		
		// Verificação
		Assert.assertThat(exception.getMessage(), CoreMatchers.is(String.format(DocumentoUtils.ME002, numero)));
	}

	@Test
	public void formataCnpjInvalidoTest() {
		
		// Cenário
		IllegalArgumentException exception = null;
		
		// Ação
		final String numero = "00000000000000";
		try {
			DocumentoUtils.formataCnpj(numero);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		
		// Verificação
		Assert.assertThat(exception.getMessage(), CoreMatchers.is(String.format(DocumentoUtils.ME002, numero)));
	}

	@Test
	public void cpfInvalidoTest() {
		
		// Cenário
		final String numero = "12345678900";

		// Ação
		final Boolean resultado = DocumentoUtils.isCpf(numero);

		// Verificação
		Assert.assertFalse(resultado);
	}

	@Test
	public void cnpjInvalidoTest() {
		
		// Cenário
		final String numero = "12345678000123";

		// Ação
		final Boolean resultado = DocumentoUtils.isCnpj(numero);

		// Verificação
		Assert.assertFalse(resultado);
	}
}
