package br.com.leuras.commons.export;

import java.util.List;
import java.util.Map;

import br.com.leuras.commons.exception.ExportacaoException;

public interface FormatoExportavel<T> {

    /**
     * Responsável por escrever o cabeçalho do documento exportado.
     * 
     * @param parametros
     *            Parâmetros
     * @throws ExportacaoException
     *             Caso algum erro durante a exportação.
     * @see Exportador Exportador            
     */
    void cabecalho(Map<String, Object> parametros) throws ExportacaoException;

    /**
     * Responsável por escrever o corpo (detalhe) do documento exportado.
     * 
     * @param registros
     *            Coleção de dados a serem exportados
     * @throws ExportacaoException
     *             Caso algum erro durante a exportação.
     * @see Exportador Exportador
     */
    void detalhes(List<T> registros) throws ExportacaoException;

    /**
     * Responsável por escrever o rodapé do documento exportado.
     * 
     * @param parametros
     *            Parâmetros
     * @throws ExportacaoException
     *             Caso algum erro durante a exportação.
     * @see Exportador Exportador            
     */
    void rodape(Map<String, Object> parametros) throws ExportacaoException;

    /**
     * Responsável por obter o conteúdo do documento.
     * 
     * @return Conteúdo do documento em forma de um array de bytes.
     * @throws ExportacaoException
     *             Caso algum erro durante a exportação.
     * @see Exportador Exportador            
     */
    byte[] getBytes() throws ExportacaoException;

    /**
     * Retorna o tipo genérico de <b>T</b>. Este método não deve retornar nulo.
     * 
     * @return O tipo da classe genérica <b>T</b>.
     * @see Exportador Exportador
     */
    Class<T> getType();
}
