package br.com.leuras.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;

public final class ArquivoUtils {

	/**
	 * Identificador utilizado pelo sistema operacional como quebra de linha.
	 */
	public static final String QUEBRAR_LINHA = System.getProperty("line.separator");

	/**
	 * Identificador utilizado pelo sistema operacional para separar diretórios e arquivos.
	 */
	public static final String SEPARADOR = System.getProperty("file.separator");

	/**
	 * Diretório temporário utilizado pelo sistema operacional.
	 */
	public static final String TEMP = System.getProperty("java.io.tmpdir");

	protected static final String ME001 = "O parâmetro não pode ser nulo.";

	private ArquivoUtils() {
		super();
	}

	/**
	 * Escreve o conteúdo no arquivo indicado. Se porventura o arquivo existir, seu conteúdo será
	 * sobrescrito.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void criar(final String arquivo, final String conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		ArquivoUtils.criar(new File(arquivo), conteudo);
	}

	/**
	 * Escreve o conteúdo no arquivo indicado. Se porventura o arquivo existir, seu conteúdo será
	 * sobrescrito.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void criar(final File arquivo, final String conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		if (conteudo == null) {
			throw new IllegalArgumentException(ArquivoUtils.ME001);
		}

		ArquivoUtils.criar(arquivo, conteudo.getBytes());
	}

	/**
	 * Escreve os bytes no conteúdo do arquivo indicado. Se porventura o arquivo existir, seu
	 * conteúdo será sobrescrito.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void criar(final String arquivo, final byte[] conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		ArquivoUtils.criar(new File(arquivo), conteudo);
	}

	/**
	 * Escreve os bytes no conteúdo do arquivo indicado. Se porventura o arquivo existir, seu
	 * conteúdo será sobrescrito.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void criar(final File arquivo, final byte[] conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		if (conteudo == null) {
			throw new IllegalArgumentException(ArquivoUtils.ME001);
		}

		final FileOutputStream saida = ArquivoUtils.abrirParaEscrita(arquivo.getAbsolutePath());

		ArquivoUtils.escrever(saida, conteudo);
	}

	/**
	 * Escreve o conteúdo no arquivo indicado. Se porventura o arquivo existir, o novo conteúdo será
	 * acrescentado ao final do arquivo.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void escrever(final String arquivo, final String conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		ArquivoUtils.escrever(new File(arquivo), conteudo);
	}

	/**
	 * Escreve o conteúdo no arquivo indicado. Se porventura o arquivo existir, o novo conteúdo será
	 * acrescentado ao final do arquivo.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void escrever(final File arquivo, final String conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		if (conteudo == null) {
			throw new IllegalArgumentException(ArquivoUtils.ME001);
		}

		ArquivoUtils.escrever(arquivo, conteudo.getBytes());
	}

	/**
	 * Escreve os bytes no conteúdo do arquivo indicado. Se porventura o arquivo existir, o novo
	 * conteúdo será acrescentado ao final do arquivo.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void escrever(final String arquivo, final byte[] conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		ArquivoUtils.escrever(new File(arquivo), conteudo);
	}

	/**
	 * Escreve os bytes no conteúdo do arquivo indicado. Se porventura o arquivo existir, o novo
	 * conteúdo será acrescentado ao final do arquivo.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void escrever(final File arquivo, final byte[] conteudo)
	        throws FileNotFoundException, SecurityException, IOException {

		final FileOutputStream saida = ArquivoUtils.abrirParaEdicao(arquivo.getAbsolutePath());

		ArquivoUtils.escrever(saida, conteudo);
	}

	/**
	 * Sobrescreve ou acrescenta o conteúdo no arquivo indicado.
	 * 
	 * @see #abrirParaEdicao(String) #abrirParaEscrita(String)
	 * 
	 * @param saida
	 *            Stream para o arquivo de saída
	 * @param conteudo
	 *            Conteúdo a ser escrito no arquivo
	 * @throws IllegalArgumentException
	 *             Caso o conteúdo informado seja nulo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static void escrever(final FileOutputStream saida, final byte[] conteudo)
	        throws IllegalArgumentException, FileNotFoundException, SecurityException, IOException {

		if (conteudo == null) {
			throw new IllegalArgumentException(ArquivoUtils.ME001);
		}

		saida.write(conteudo);
		saida.flush();
		saida.close();
	}

	/**
	 * Lê todo o conteúdo de um arquivo para um array de <b>bytes</b>.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @return Conteúdo do arquivo em forma de um array de bytes.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static byte[] lerTudo(final File arquivo)
	        throws FileNotFoundException, SecurityException, IOException {
		return ArquivoUtils.ler(arquivo, 0, (int) arquivo.length());
	}

	/**
	 * Lê do arquivo o total de bytes definido em <b>tamanho</b> tomando como posição inicial
	 * <b>offset</b>.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @param offset
	 *            Posição inicial de leitura do buffer
	 * @param tamanho
	 *            Número de bytes a serem lidos
	 * @return Conteúdo do arquivo em forma de um array de bytes.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static byte[] ler(final File arquivo, final int offset, final int tamanho)
	        throws FileNotFoundException, SecurityException, IOException {

		final byte[] buffer = new byte[tamanho];
		final FileInputStream stream = ArquivoUtils.abrirParaLeitura(arquivo.getAbsolutePath());

		stream.read(buffer, offset, tamanho);
		stream.close();

		return buffer;
	}

	/**
	 * Abre um arquivo para leitura.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @return Uma <b>stream</b> de leitura para o arquivo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static FileInputStream abrirParaLeitura(final String arquivo)
	        throws FileNotFoundException, SecurityException, IOException {
		return new FileInputStream(arquivo);
	}

	/**
	 * Abre um arquivo para escrita apagando quaisquer conteúdo que o mesmo já possua. Se o arquivo
	 * não existir, tenta criá-lo.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @return Uma <b>stream</b> de escrita para o arquivo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static FileOutputStream abrirParaEscrita(final String arquivo)
	        throws FileNotFoundException, SecurityException, IOException {
		return new FileOutputStream(arquivo, false);
	}

	/**
	 * Abre um arquivo para escrita sem apagar o conteúdo atual. Se o arquivo não existir, tenta
	 * criá-lo.
	 * 
	 * @param arquivo
	 *            Caminho para o arquivo
	 * @return Uma <b>stream</b> de escrita para o arquivo.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static FileOutputStream abrirParaEdicao(final String arquivo)
	        throws FileNotFoundException, SecurityException, IOException {
		return new FileOutputStream(arquivo, true);
	}

	/**
	 * Cria toda a estrutura de diretórios necessários e não existentes no <b>caminho</b> passado
	 * por parâmetro.
	 * 
	 * @param caminho
	 *            Caminho absoluto do diretório
	 * @return Representação do diretório.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 */
	public static File criarEstruturaDiretorios(final String caminho) throws SecurityException {

		final File diretorio = new File(caminho);

		diretorio.mkdirs();

		return diretorio;
	}

	/**
	 * Tenta determinar qual o tipo mime do arquivo.
	 * 
	 * @param arquivo
	 *            Arquivo
	 * @return Tipo mime obtido com base no conteúdo e no nome do arquivo dado.
	 * @throws FileNotFoundException
	 *             Caso o caminho informado não se trate de um arquivo ou não seja possível criá-lo.
	 * @throws SecurityException
	 *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
	 * @throws IOException
	 *             Caso algum erro de entrada e saída ocorra durante a operação.
	 */
	public static String getTipoMime(final File arquivo)
	        throws FileNotFoundException, SecurityException, IOException {

		final FileInputStream stream = ArquivoUtils.abrirParaLeitura(arquivo.getAbsolutePath());

		String mime = URLConnection.guessContentTypeFromStream(stream);

		if (StringUtils.isBlank(mime)) {
			mime = URLConnection.guessContentTypeFromName(arquivo.getAbsolutePath());
		}

		return mime;
	}
}
