package br.com.leuras.commons.enumerator;

import org.junit.Assert;
import org.junit.Test;

import br.com.leuras.commons.util.NumeralUtils;

public class SimNaoEnumTest {
	
	@Test
	public void valorDeBooleanoTest() {
		
		// Cenário
		boolean sim = true;
		
		// Ação
		final SimNaoEnum resultado = SimNaoEnum.valueOf(sim);
		
		// Verificação
		Assert.assertTrue(SimNaoEnum.SIM.equals(resultado));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void valorDeBooleanoInvalidoTest() {
		SimNaoEnum.valueOf((Boolean) null);
	}
	
	@Test
	public void valorDeInteiroTest() {
		
		// Cenário
		int nao = NumeralUtils.ZERO;
		
		// Ação
		final SimNaoEnum resultado = SimNaoEnum.valueOf(nao);
		
		// Verificação
		Assert.assertTrue(SimNaoEnum.NAO.equals(resultado));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void valorDeInteiroInvalidoTest() {
		SimNaoEnum.valueOf(NumeralUtils.DEZ);
	}
}
