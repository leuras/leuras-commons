package br.com.leuras.commons.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.leuras.commons.exception.ExportacaoException;
import br.com.leuras.commons.util.ArquivoUtils;

public class Exportador<T> {

    private FormatoExportavel<T> formato;

    /**
     * Constrói uma nova instância de Exportador utilizando formato especificado.
     * <pre>
     * <code>
     *  
     * {@literal @}Exportavel(titulo = "Listagem de Funcionários")
     *  public class Funcionario {
     * 
     *    {@literal @}AtributoExportavel(coluna = "Nome do Funcionário")
     *     private String nome;
     *     ...
     *  }
     * 
     * final FormatoExportavelExcel formato = new FormatoExportavelExcel{@literal <Funcionario>}(Funcionario.class);
     * final Exportador{@literal <Funcionario>} exportador = new Exportador{@literal <Funcionario>}(formato);
     * 
     * byte[] buffer = exportador.exportar(registros);
     * 
     * </code>
     * </pre>
     * 
     * @param formato
     *            Formato de exportação desejado.
     * @throws IllegalArgumentException
     *             Caso o método <i>getType()</i> de <b>T</b> retorne nulo ou não possua a anotação <i>@Exportavel</i>.
     * @see Exportavel Exportavel             
     * @see AtributoExportavel AtributoExportavel             
     */
    public Exportador(final FormatoExportavel<T> formato) throws IllegalArgumentException {

        if (formato.getType() == null) {
            throw new IllegalArgumentException(
                    "O retorno do método \"getType()\" da implementação de \"FormatoExportavel\" não pode ser nulo.");
        }

        final Exportavel anotacao = formato.getType().getAnnotation(Exportavel.class);

        if (anotacao == null) {
            final String classe = formato.getType().getSimpleName();
            throw new IllegalArgumentException(
                    String.format("A anotação \"@Exportavel\" não foi encontrada na classe \"%s\".", classe));
        }

        this.formato = formato;
    }
    
    /**
     * Exporta uma coleção de dados no formato especificado.
     * 
     * @param registros
     *            Coleção de dados a serem exportados
     * @return Conteúdo exportado em forma de um array de bytes.
     * @throws ExportacaoException
     *             Caso ocorra algum erro durante a exportação.
     */
    public byte[] exportar(final List<T> registros) throws ExportacaoException {
        return this.exportar(registros, new HashMap<String, Object>());
    }
    
    /**
     * Exporta uma coleção de dados no formato especificado.
     * 
     * @param registros
     *            Coleção de dados a serem exportados
     * @param parametros
     *            Parâmetros extras que serão passados para o exportador (cabeçalho e rodapé)
     * @return Conteúdo exportado em forma de um array de bytes.
     * @throws ExportacaoException
     *             Caso ocorra algum erro durante a exportação.
     */
    public byte[] exportar(final List<T> registros, final Map<String, Object> parametros) throws ExportacaoException {

        this.formato.cabecalho(parametros);
        this.formato.detalhes(registros);
        this.formato.rodape(parametros);

        return this.formato.getBytes();
    }
    
    /**
     * Exporta uma coleção de dados no formato especificado para um arquivo.
     * 
     * @param arquivo
     *            Arquivo
     * @param registros
     *            Coleção de dados a serem exportados
     * @throws FileNotFoundException
     *             Caso o caminho informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     * @throws ExportacaoException
     *             Caso ocorra algum erro durante a exportação.
     */
    public void exportar(final String arquivo, final List<T> registros)
            throws FileNotFoundException, SecurityException, IOException, ExportacaoException {
        this.exportar(arquivo, registros, new HashMap<String, Object>());
    }
    
    /**
     * Exporta uma coleção de dados no formato especificado para um arquivo.
     * 
     * @param arquivo
     *            Arquivo
     * @param registros
     *            Coleção de dados a serem exportados
     * @param parametros
     *            Parâmetros extras que serão passados para o exportador (cabeçalho e rodapé)
     * @throws FileNotFoundException
     *             Caso o caminho informado não exista.
     * @throws SecurityException
     *             Caso haja alguma restrição de acesso ao arquivo que impeça a sua manipulação.
     * @throws IOException
     *             Caso algum erro de entrada e saída ocorra durante a operação.
     * @throws ExportacaoException
     *             Caso ocorra algum erro durante a exportação.
     */
    public void exportar(final String arquivo, final List<T> registros, final Map<String, Object> parametros)
            throws FileNotFoundException, SecurityException, IOException, ExportacaoException {

        final byte[] conteudo = this.exportar(registros, parametros);

        ArquivoUtils.escrever(new File(arquivo), conteudo);
    }
}
