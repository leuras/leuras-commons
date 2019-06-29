package br.com.leuras.commons.builder;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.leuras.commons.rede.CorreioEletronico;

public class CorreioEletronicoBuilder implements Builder<CorreioEletronico> {

    private static final int SMTP_PORT = 25;

    private HtmlEmail configuracao = null;

    /**
     * Constrói uma nova instância de CorreioEletronicoBuilder. Por padrão, a nova instância é criada com as seguintes
     * definições:
     * <p>
     *     <ul>
     * 	       <li>Porta SMTP: 25</li>
     * 		   <li>SSL/TLS: desabilitado</li>
     * 		   <li>Charset: UTF-8</li>
     * 	   </ul>
     * </p>
     * 
     * <pre>
     * {@code
     * CorreioEletronico correio = new CorreioEletronicoBuilder()
     * .comServidorSmtp("smtp.mailtrap.io")
     * .comPorta(587)
     * .comRemetente("fulano@mail.com")
     * .comAssunto("Mussum Ipsum")
     * .comCorpo("<h1>Suco de cevadiss deixa as pessoas mais interessantis.</h1>")
     * .comDestinatarios("ciclano@mail.com", "beltrano@mail.com")
     * .build();
     * }
     * </pre>
     */
    public CorreioEletronicoBuilder() {
        this.configuracao = new HtmlEmail();

        this.configuracao.setSmtpPort(SMTP_PORT);
        this.configuracao.setSSLOnConnect(false);
        this.configuracao.setStartTLSEnabled(false);
        this.configuracao.setCharset(StandardCharsets.UTF_8.name());
    }

    /**
     * Determina qual o nome do servidor SMTP que será utilizado.
     * 
     * @param servidor
     *            Nome do servidor (FQDN)
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     */
    public CorreioEletronicoBuilder comServidorSmtp(final String servidor) {
        this.configuracao.setHostName(servidor);

        return this;
    }

    /**
     * Determina qual a porta do servidor SMTP que será utilizada.
     * 
     * @param porta
     *            Número da porta
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     */
    public CorreioEletronicoBuilder comPorta(final int porta) {
        this.configuracao.setSmtpPort(porta);

        return this;
    }

    /**
     * Gerencia o uso do <b>SSL/TLS</b> na conexão.
     * 
     * @param habilitado
     *            Estado do SSL/TLS
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     */
    public CorreioEletronicoBuilder comTLS(final boolean habilitado) {
        this.configuracao.setStartTLSEnabled(habilitado);

        return this;
    }

    /**
     * Define quais credenciais serão utilizadas para envio de e-mail com autenticação.
     * 
     * @param usuario
     *            Nome de usuário
     * @param senha
     *            Senha
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     */
    public CorreioEletronicoBuilder comCredenciais(final String usuario, final String senha) {
        this.configuracao.setAuthentication(usuario, senha);

        return this;
    }

    /**
     * Define qual o endereço de e-mail do remetente (<i>mail from</i>).
     * 
     * @param remetente
     *            Endereço de e-mail
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     * @throws EmailException
     *             Caso o endereço de e-mail informado seja considerado inválido.
     */
    public CorreioEletronicoBuilder comRemetente(final String remetente) throws EmailException {
        this.configuracao.setFrom(remetente);

        return this;
    }

    /**
     * Define qual(ais) o(s) endereço(s) de e-mail do(s) destinatário(s) (<i>mail to</i>).
     * 
     * @param destinatarios
     *            Endereço(s) de e-mail
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     * @throws EmailException
     *             Caso o endereço de e-mail informado seja considerado inválido.Caso um ou mais endereços de e-mail
     *             informados sejam considerados inválidos.
     */
    public CorreioEletronicoBuilder comDestinatarios(final String... destinatarios) throws EmailException {
        this.configuracao.addTo(destinatarios);
        return this;
    }

    /**
     * Define qual(ais) o(s) endereço(s) de e-mail que estarão em cópia (<i>mail cc</i>).
     * 
     * @param emails
     *            Endereço(s) de e-mail
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     * @throws EmailException
     *             Caso um ou mais endereços de e-mail informados sejam considerados inválidos.
     */
    public CorreioEletronicoBuilder comCopiaPara(final String... emails) throws EmailException {
        this.configuracao.addCc(emails);

        return this;
    }

    /**
     * Define qual será o assunto da mensagem.
     * 
     * @param assunto
     *            Assunto desejado
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     */
    public CorreioEletronicoBuilder comAssunto(final String assunto) {
        this.configuracao.setSubject(assunto);

        return this;
    }
    
    /**
     * Define qual será o conteúdo do corpo da mensagem.
     * 
     * @param corpo
     *            Conteúdo da mensagem
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     * @throws EmailException
     *             Caso haja algum erro no processamento do conteúdo da mensagem.
     */
    public CorreioEletronicoBuilder comCorpo(final String corpo) throws EmailException {
        this.configuracao.setHtmlMsg(corpo);

        return this;
    }
    
    /**
     * Inclui um ou mais arquivos como anexo da mensagem.
     * 
     * @param arquivos
     *            Arquivos que se deseja anexar ao e-mail
     * @return A instância de CorreioEletronicoBuilder para as demais configurações.
     * @throws EmailException
     *             Caso haja algum erro ao anexar os arquivo à mensagem.
     */
    public CorreioEletronicoBuilder comAnexo(final File... arquivos) throws EmailException {
        for (final File anexo : arquivos) {
            this.configuracao.attach(anexo);
        }

        return this;
    }
    
    @Override
    public CorreioEletronico build() {
        return new CorreioEletronico(this.configuracao);
    }

}
