package br.com.leuras.commons.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public final class SegurancaUtils {

	/**
	 * Permitir <b>CPF</b> como critério de aceitação para nome de usuário.
	 */
	public static final int PERMITE_CPF = 0;

	/**
	 * Permitir <b>CNPJ</b> como critério de aceitação para nome de usuário.
	 */
	public static final int PERMITE_CNPJ = 1;

	/**
	 * Permitir <b>EMAIL</b> como critério de aceitação para nome de usuário.
	 */
	public static final int PERMITE_EMAIL = 2;

	/**
	 * Permitir <b>UID LDAP</b> como critério de aceitação para nome de usuário. Um <b>UID LDAP</b>
	 * pode conter letras, números e "." como por exemplo: s099872, 80239987, john.doe, root,
	 * Administrador.
	 */
	public static final int PERMITE_LDAP = 3;

	private static final String REGEXP_LDAP = "^([a-z0-9]+(\\.[a-z0-9]+)?)$";

	private SegurancaUtils() {
		super();
	}

	/**
	 * Verifica se o nome de usuário informado é um <i>CPF</i> ou um <i>email</i> válido.
	 * 
	 * @param usuario
	 *            Nome de usuário (login)
	 * @return <b>True</b> se o nome de usuário for um <i>CPF</i> ou um <i>email</i> válido e
	 *         <b>false</b> caso nenhum dos critérios mencionados tenha sido atendido.
	 */
	public static boolean isUsuarioValido(final String usuario) {
		return SegurancaUtils.isUsuarioValido(usuario, PERMITE_CPF, PERMITE_EMAIL);
	}

	/**
	 * Verifica se o nome de usuário atende a pelo menos um dos critérios de aceitação informados.
	 * 
	 * @param usuario
	 *            Nome de usuário (login)
	 * @param criterios
	 *            Critérios de aceitação: {@link #PERMITE_CNPJ} {@link #PERMITE_CPF}
	 *            {@link #PERMITE_EMAIL} {@link #PERMITE_LDAP}
	 * @return <b>True</b> se o nome de usuário atender pelo menos um dos critérios informados por
	 *         parâmetro e <b>false</b> caso contrário.
	 */
	public static boolean isUsuarioValido(final String usuario, final Integer... criterios) {
		
		if (StringUtils.isBlank(usuario)) {
			return false;
		}
		
		final List<Integer> criteriosPermitidos = Arrays.asList(criterios);

		if (criteriosPermitidos.contains(PERMITE_CPF) && DocumentoUtils.isCpf(usuario)) {
			return true;
		}

		if (criteriosPermitidos.contains(PERMITE_CNPJ) && DocumentoUtils.isCnpj(usuario)) {
			return true;
		}

		if (criteriosPermitidos.contains(PERMITE_EMAIL) && EmailValidator.getInstance().isValid(usuario)) {
			return true;
		}

		if (criteriosPermitidos.contains(PERMITE_LDAP) && isValido(usuario.toLowerCase(), REGEXP_LDAP)) {
			return true;
		}

		return false;
	}

	private static boolean isValido(final String valor, final String expressao) {

		final Pattern pattern = Pattern.compile(expressao);
		final Matcher matcher = pattern.matcher(valor);

		return matcher.find();
	}
}
