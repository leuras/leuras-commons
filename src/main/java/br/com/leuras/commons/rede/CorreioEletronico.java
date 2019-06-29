package br.com.leuras.commons.rede;

import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class CorreioEletronico {

    private HtmlEmail mail = null;

    /**
     * Constrói uma nova instância de CorreioEletronico utilizando definições contidas em <b>mail</b>.
     * 
     * @param mail
     *            Configurações
     * @see br.com.leuras.commons.builder.CorreioEletronicoBuilder#build() CorreioEletronicoBuilder.build()
     */
    public CorreioEletronico(final HtmlEmail mail) {
        this.mail = mail;
    }

    /**
     * Envia a mensagem utilizando as parametrizações definidas no atributo <i>mail</i>.
     * 
     * @throws EmailException
     *             Caso ocorra um erro durante o envio da mensagem.
     * @see #getMail() getMail()
     * @see br.com.leuras.commons.builder.CorreioEletronicoBuilder CorreioEletronicoBuilder
     */
    public void enviar() throws EmailException {
        this.mail.send();
    }

    /**
     * Envia a mensagem sobrescrevendo o conteúdo e os destinatários parametrizados no atributo <i>mail</i>.
     * 
     * @param corpo
     *            Texto em <i>html</i> que representa o conteúdo da mensagem
     * @param destinatarios
     *            Endereço(s) de e-mail do(s) destinatário(s)
     * @throws EmailException
     *             Caso ocorra um erro durante o envio da mensagem.
     * @see #getMail() getMail()
     * @see br.com.leuras.commons.builder.CorreioEletronicoBuilder CorreioEletronicoBuilder
     */
    public void enviar(final String corpo, final String... destinatarios) throws EmailException {
        if (this.mail.getToAddresses() != null && !this.mail.getToAddresses().isEmpty()) {
            this.mail.getToAddresses().clear();
        }

        this.mail.addTo(destinatarios);
        this.mail.setHtmlMsg(corpo);
        this.mail.send();
    }

    /**
     * Envia a mensagem usando um template engine para processar um arquivo de conteúdo dinâmico. Este método
     * sobrescreve o copor e os destinatários parametrizados no atributo <i>mail</i>.
     * 
     * <p>A engine utilizada para processar os templates é o <b>JTwig</b>.</p>
     * 
     * @param template
     *            Arquivo de template em <i>html</i> com o conteúdo da mensagem
     * @param variavies
     *            Coleção de variáveis que serão passadas para a engine do template
     * @param destinatarios
     *            Endereço(s) de e-mail do(s) destinatário(s)
     * @throws EmailException
     *             Caso ocorra um erro durante o envio da mensagem.
     * @see #getMail() getMail()
     * @see br.com.leuras.commons.builder.CorreioEletronicoBuilder CorreioEletronicoBuilder
     * @see <a href="http://www.jtwig.org">http://www.jtwig.org</a>
     */
    public void enviar(final String template, final Map<String, Object> variavies, final String... destinatarios)
            throws EmailException {

        final JtwigTemplate engine = JtwigTemplate.classpathTemplate(template);

        this.enviar(engine.render(JtwigModel.newModel(variavies)), destinatarios);
    }
    
    /**
     * Obtém o conjunto de parametrizações definidas para o envio de e-mail.
     * 
     * @return A instância de HtmlEmail.
     */
    public HtmlEmail getMail() {
        return this.mail;
    }
}
