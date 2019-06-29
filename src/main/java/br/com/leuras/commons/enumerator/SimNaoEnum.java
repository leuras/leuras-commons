package br.com.leuras.commons.enumerator;

import br.com.leuras.commons.util.NumeralUtils;

public enum SimNaoEnum {

	SIM ("Sim", true), NAO ("Não", false);

	private String label;

	private Boolean value;

	private SimNaoEnum(String label, Boolean value) {
		this.label = label;
		this.value = value;
	}
	
	/**
	 * Retorna o enum a partir do valor lógico especificado.
	 * 
	 * @param value
	 *            Valor lógico
	 * @return O enum com o valor lógico especificado.
	 * @throws IllegalArgumentException Se o argumento for nulo.
	 */
	public static SimNaoEnum valueOf(Boolean value) throws IllegalArgumentException {
		for (SimNaoEnum elm : SimNaoEnum.values()) {
			if (elm.getValue().equals(value)) {
				return elm;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	/**
	 * Retorna o enum a partir do valor inteiro especificado.
	 * 
	 * @param value
	 *            Valor lógico
	 * @return O enum com o valor lógico especificado.
	 * @throws IllegalArgumentException Se o inteiro informado for diferente de <b>zero</b> e <b>um</b>.
	 */
	public static SimNaoEnum valueOf(int value) throws IllegalArgumentException {
		if (NumeralUtils.ZERO.equals(value)) {
			return SimNaoEnum.NAO;
		} else if (NumeralUtils.UM.equals(value)) {
			return SimNaoEnum.SIM;
		}
		
		throw new IllegalArgumentException();
	}

	public String getLabel() {
		return label;
	}

	public Boolean getValue() {
		return value;
	}
}
