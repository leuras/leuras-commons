package br.com.leuras.commons.util;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class DataUtilsTest {

	@Test
	public void construtorTest() throws Exception {

		final Constructor<DataUtils> constructor = DataUtils.class.getDeclaredConstructor();

		Assert.assertEquals(constructor.isAccessible(), false);

		constructor.setAccessible(true);
		constructor.newInstance((Object[]) null);
	}

	@Test
	public void construtorFormatoTest() throws Exception {

		final Constructor<DataUtils.Formato> constructor = DataUtils.Formato.class.getDeclaredConstructor();

		Assert.assertEquals(constructor.isAccessible(), false);

		constructor.setAccessible(true);
		constructor.newInstance((Object[]) null);
	}

	@Test
	public void primeiraHoraDiaTest() {

		// Cenário
		final Calendar calendario = Calendar.getInstance();

		calendario.set(Calendar.HOUR_OF_DAY, 14);
		calendario.set(Calendar.MINUTE, 37);
		calendario.set(Calendar.SECOND, 23);

		// Ação
		calendario.setTime(DataUtils.setPrimeiraHoraDia(calendario.getTime()));

		// Verificação
		Assert.assertThat(calendario.get(Calendar.HOUR_OF_DAY), CoreMatchers.is(0));
		Assert.assertThat(calendario.get(Calendar.MINUTE), CoreMatchers.is(0));
		Assert.assertThat(calendario.get(Calendar.SECOND), CoreMatchers.is(0));
	}

	@Test
	public void ultimaHoraDiaTest() {

		// Cenário
		final Calendar calendario = Calendar.getInstance();

		// Ação
		calendario.setTime(DataUtils.setUltimaHoraDia(new Date()));

		// Verificação
		Assert.assertThat(calendario.get(Calendar.HOUR_OF_DAY), CoreMatchers.is(23));
		Assert.assertThat(calendario.get(Calendar.MINUTE), CoreMatchers.is(59));
		Assert.assertThat(calendario.get(Calendar.SECOND), CoreMatchers.is(59));
	}

	@Test
	public void anoDoisDigitosTest() {

		// Cenário
		final Calendar calendario = Calendar.getInstance();

		calendario.set(Calendar.YEAR, 2019);

		// Ação
		final Integer resultado = DataUtils.getAnoDoisDigitos(calendario);

		// Verificação
		Assert.assertThat(resultado, CoreMatchers.is(19));
	}

	@Test
	public void paraDataTest() {

		// Cenário
		final String data = "1981-01-18 08:00:00";
		final Calendar calendario = Calendar.getInstance();

		// Ação
		calendario.setTime(DataUtils.paraData(data, null));

		// Verificação
		Assert.assertNotNull(calendario);
		Assert.assertThat(calendario.get(Calendar.DAY_OF_MONTH), CoreMatchers.is(18));
		Assert.assertThat(calendario.get(Calendar.MONTH), CoreMatchers.is(0));
		Assert.assertThat(calendario.get(Calendar.YEAR), CoreMatchers.is(1981));
	}

	@Test
	public void paraDataInvalidaTest() {
		Assert.assertNull(DataUtils.paraData("99/99/9999", null));
	}

	@Test
	public void diffTest() {

		// Cenário
		final Date d1 = DataUtils.paraData("01/01/2019", null, DataUtils.Formato.PADRAO);
		final Date d2 = DataUtils.paraData("05/01/2019", null, DataUtils.Formato.PADRAO);

		// Ação
		final Integer dias = DataUtils.diff(d2, d1);

		// Verificação
		Assert.assertThat(dias, CoreMatchers.is(4));
	}

	@Test
	public void formatarDataTest() {
		
		// Cenário
		final Date data = DataUtils.paraData("18/01/1981", null, DataUtils.Formato.PADRAO);
		
		// Ação
		final String resultado = DataUtils.formatar(data, DataUtils.Formato.MES_E_ANO);
		
		// Verificação
		Assert.assertEquals("01/1981", resultado);
	}

	@Test
	public void formatarDataInvalidaTest() {
		
		// Ação
		final String resultado = DataUtils.formatar(null, DataUtils.Formato.MES_E_ANO);
		
		// Verificação
		Assert.assertEquals("", resultado);
	}

	@Test
	public void porExtensoTest() {
		
		// Cenário
		final Date data = DataUtils.paraData("18/01/1981", null, DataUtils.Formato.PADRAO);

		// Ação
		final String resultado = DataUtils.porExtenso(data);

		// Verificação
		final String esperado = "18 de Janeiro de 1981";

		Assert.assertThat(resultado, CoreMatchers.is(esperado));
	}
}
