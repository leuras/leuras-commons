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
     * Identificador utilizado pelo sistema operacional para separar linhas em um arquivo. 
     */
    public static final String EOL = System.getProperty("line.separator");
    
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

    }

    /**
     * Escreve os bytes no conteúdo do arquivo indicado.
     * 
     * @param arquivo
     *            Arquivo
     * @param conteudo
     *            Conteúdo a ser escrito no arquivo
     * @throws FileNotFoundException
     *             Caso o arquivo informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static void escrever(final File arquivo, final byte[] conteudo)
            throws FileNotFoundException, SecurityException, IOException {

        final FileOutputStream stream = new FileOutputStream(arquivo);

        stream.write(conteudo);
        stream.flush();
        stream.close();
    }

    /**
     * Escreve o <b>conteudo</b> no arquivo indicado.
     * 
     * @param arquivo
     *            Arquivo
     * @param conteudo
     *            Conteúdo a ser escrito no arquivo
     * @throws FileNotFoundException
     *             Caso o arquivo informado não exista.
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
     * Lê todo o conteúdo de um arquivo para um array de <b>bytes</b>.
     * 
     * @param arquivo
     *            Arquivo
     * @return Conteúdo do arquivo em forma de um array de bytes.
     * @throws FileNotFoundException
     *             Caso o arquivo informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] lerTudo(final File arquivo) throws FileNotFoundException, SecurityException, IOException {
        return ArquivoUtils.ler(arquivo, 0, (int) arquivo.length());
    }

    /**
     * Lê do arquivo o total de bytes definido em <b>tamanho</b> tomando como posição inicial <b>offset</b>.
     * 
     * @param arquivo
     *            Arquivo
     * @param offset
     *            Posição inicial de leitura do buffer
     * @param tamanho
     *            Número de bytes a serem lidos
     * @return Conteúdo do arquivo em forma de um array de bytes.
     * @throws FileNotFoundException
     *             Caso o arquivo informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static byte[] ler(final File arquivo, final int offset, final int tamanho)
            throws FileNotFoundException, SecurityException, IOException {

        final byte[] buffer = new byte[tamanho];
        final FileInputStream stream = new FileInputStream(arquivo);

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
     *             Caso o arquivo informado não exista.
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
     * Abre um arquivo para escrita.
     * 
     * @param arquivo
     *            Caminho para o arquivo
     * @return Uma <b>stream</b> de escrita para o arquivo.
     * @throws FileNotFoundException
     *             Caso o arquivo informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static FileOutputStream abrirParaEscrita(final String arquivo)
            throws FileNotFoundException, SecurityException, IOException {
        return new FileOutputStream(arquivo);
    }
    
    /**
     * Cria toda a estrutura de diretórios necessários e não existentes no <b>caminho</b> passado por parâmetro.
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
     *             Caso o arquivo informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     */
    public static String getTipoMime(final File arquivo) throws FileNotFoundException, SecurityException, IOException {

        final FileInputStream stream = new FileInputStream(arquivo);

        String mime = URLConnection.guessContentTypeFromStream(stream);

        if (StringUtils.isBlank(mime)) {
            mime = URLConnection.guessContentTypeFromName(arquivo.getAbsolutePath());
        }

        return mime;
    }
}
