package br.com.leuras.commons.util;

import java.util.InputMismatchException;

import org.apache.commons.lang3.StringUtils;

public final class DocumentoUtils {

	protected static final String ME001 = "O parâmetro não pode ser nulo.";

	protected static final String ME002 = "O parâmetro '%s', é inválido.";

	protected static final String ME003 = "O parâmetro '%s' está em um formato inválido.";

	private DocumentoUtils() {

	}

	/**
	 * Identifica se o número do CPF informado é válido.
	 * 
	 * @param cpf
	 *            Número do CPF
	 * @return <b>True</b> se o CPF é válido e <b>false</b> caso contrário.
	 */
	public static boolean isCpf(String cpf) {

		if (cpf == null)
			return false;

		cpf = NumeralUtils.somenteNumeros(cpf);
		cpf = StringUtils.leftPad(cpf, 11, '0');

		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
		        || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
		        || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
		        || cpf.equals("99999999999")) {
			return false;
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {

			sm = 0;
			peso = 10;

			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);

			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48);
			}

			sm = 0;
			peso = 11;

			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);

			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}

			return ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) ? true : false;
		} catch (InputMismatchException erro) {
			return false;
		}
	}

	/**
	 * Identifica se o número do CNPJ informado é válido.
	 * 
	 * @param cnpj
	 *            Número do CNPJ
	 * @return <b>True</b> se o CNPJ é válido e <b>false</b> caso contrário.
	 */
	public static boolean isCnpj(String cnpj) {

		if (cnpj == null)
			return false;

		cnpj = NumeralUtils.somenteNumeros(cnpj);
		cnpj = StringUtils.leftPad(cnpj, 14, '0');

		int soma = 0, digito;

		try {

			if (cnpj == "00000000000000" || cnpj == "11111111111111" || cnpj == "22222222222222"
			        || cnpj == "33333333333333" || cnpj == "44444444444444" || cnpj == "55555555555555"
			        || cnpj == "66666666666666" || cnpj == "77777777777777" || cnpj == "88888888888888"
			        || cnpj == "99999999999999") {
				return false;
			}

			String auxCNPJ = cnpj.substring(0, 12);

			char[] charCNPJ = cnpj.toCharArray();

			for (int i = 0; i < 4; i++) {
				if (charCNPJ[i] - 48 >= 0 && charCNPJ[i] - 48 <= 9) {
					soma += (charCNPJ[i] - 48) * (6 - (i + 1));
				}
			}

			for (int i = 0; i < 8; i++) {
				if (charCNPJ[i + 4] - 48 >= 0 && charCNPJ[i + 4] - 48 <= 9) {
					soma += (charCNPJ[i + 4] - 48) * (10 - (i + 1));
				}
			}

			digito = 11 - (soma % 11);

			auxCNPJ += (digito == 10 || digito == 11) ? "0" : Integer.toString(digito);

			soma = 0;

			for (int i = 0; i < 5; i++) {
				if (charCNPJ[i] - 48 >= 0 && charCNPJ[i] - 48 <= 9) {
					soma += (charCNPJ[i] - 48) * (7 - (i + 1));
				}
			}

			for (int i = 0; i < 8; i++) {
				if (charCNPJ[i + 5] - 48 >= 0 && charCNPJ[i + 5] - 48 <= 9) {
					soma += (charCNPJ[i + 5] - 48) * (10 - (i + 1));
				}
			}

			digito = 11 - (soma % 11);
			auxCNPJ += (digito == 10 || digito == 11) ? "0" : Integer.toString(digito);

			return cnpj.equals(auxCNPJ);

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Aplica a máscara de formatação de CPF à cadeia dada.
	 * 
	 * @param cpf
	 *            Número do CPF
	 * @return Número de CPF formatado.
	 * @throws IllegalArgumentException
	 *             Se o CPF informado for considerado inválido.
	 */
	public static String formataCpf(String cpf) throws IllegalArgumentException {

		if (cpf == null) {
			throw new IllegalArgumentException(DocumentoUtils.ME001);
		}

		if (!DocumentoUtils.isCpf(cpf)) {
			throw new IllegalArgumentException(String.format(DocumentoUtils.ME002, cpf));
		}

		cpf = NumeralUtils.somenteNumeros(cpf);
		cpf = StringUtils.leftPad(cpf, 11, '0');

		final StringBuffer cpfHolder = new StringBuffer(cpf);

		cpfHolder.insert(3, ".");
		cpfHolder.insert(7, ".");
		cpfHolder.insert(11, "-");

		return cpfHolder.toString();
	}

	/**
	 * Aplica a máscara de formatação de CNPJ à cadeia dada.
	 * 
	 * @param cnpj
	 *            Número do CNPJ
	 * @return Número de CNPJ formatado.
	 * @throws IllegalArgumentException
	 *             Se o CNPJ informado for considerado inválido.
	 */
	public static String formataCnpj(String cnpj) throws IllegalArgumentException {

		if (cnpj == null) {
			throw new IllegalArgumentException(DocumentoUtils.ME001);
		}

		if (!DocumentoUtils.isCnpj(cnpj)) {
			throw new IllegalArgumentException(String.format(DocumentoUtils.ME002, cnpj));
		}

		cnpj = NumeralUtils.somenteNumeros(cnpj);
		cnpj = StringUtils.leftPad(cnpj, 14, '0');

		final StringBuffer cnpjHolder = new StringBuffer(cnpj);

		cnpjHolder.insert(2, ".");
		cnpjHolder.insert(6, ".");
		cnpjHolder.insert(10, "/");
		cnpjHolder.insert(15, "-");

		return cnpjHolder.toString();
	}
}
