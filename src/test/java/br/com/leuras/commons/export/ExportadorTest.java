package br.com.leuras.commons.export;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.leuras.commons.exception.ExportacaoException;
import br.com.leuras.commons.export.AtributoExportavel;
import br.com.leuras.commons.export.Exportador;
import br.com.leuras.commons.export.Exportavel;
import br.com.leuras.commons.export.FormatoAtributoEnum;
import br.com.leuras.commons.export.FormatoExportavel;
import br.com.leuras.commons.export.FormatoExportavelExcel;
import br.com.leuras.commons.util.ArquivoUtils;

public class ExportadorTest {
    
    private List<Funcionario> registros = new ArrayList<>();
    
    @Before
    public void setup() {
        
        this.registros.add(new Funcionario("Fernando Libório", new Date(), new BigDecimal(6500.50).setScale(2, RoundingMode.HALF_EVEN)));
        this.registros.add(new Funcionario("Lívia Dantas", new Date(), new BigDecimal(7200.45).setScale(2, RoundingMode.HALF_EVEN), true));
        this.registros.add(new Funcionario("Pablo Mustafa", new Date(), new BigDecimal(8100.13).setScale(2, RoundingMode.HALF_EVEN), true));
        this.registros.add(new Funcionario("Tiago Arnaldo", new Date(), new BigDecimal(6500.99).setScale(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    public void exportarExcelTest() throws Exception {
        // Cenário
        final String arquivo = String.format("%s/%s.xls", ArquivoUtils.TEMP, System.currentTimeMillis());
        
        // Ação
        final Exportador<Funcionario> exportador = new Exportador<Funcionario>(new FormatoExportavelExcel<Funcionario>(Funcionario.class));
            
        exportador.exportar(arquivo, this.registros);
        
        // Verificação
        Assert.assertTrue(new File(arquivo).exists());
    }
    
    @Test
    public void classeNaoExportavelTest() throws Exception {
        try {
            
            new Exportador<Funcao>(new FormatoExportavelExcel<Funcao>(Funcao.class));
            Assert.fail();
            
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("A anotação \"@Exportavel\" não foi encontrada na classe \"Funcao\"."));
        }
    }
    
    @Test
    public void getTypeNaoImplementadoTest() throws Exception {
        try {
            
            new Exportador<Funcionario>(new FormatoExportavel<Funcionario>() {

                @Override
                public void cabecalho(Map<String, Object> parametros) throws ExportacaoException {
                    
                }

                @Override
                public void detalhes(List<Funcionario> registros) throws ExportacaoException {
                    
                }

                @Override
                public void rodape(Map<String, Object> parametros) throws ExportacaoException {
                    
                }

                @Override
                public byte[] getBytes() throws ExportacaoException {
                    return null;
                }

                @Override
                public Class<Funcionario> getType() {
                    return null;
                }
                
            });
            Assert.fail();
            
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("O retorno do método \"getType()\" da implementação de \"FormatoExportavel\" não pode ser nulo."));
        }
    }
    
    public class Funcao {
        
        @AtributoExportavel(coluna = "Nome da Função")
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }
    
    @Exportavel(titulo = "Listagem de Funcionários")
    public class Funcionario {
        
        @AtributoExportavel(coluna = "Nome do Funcionário")
        private String nome;
        
        @AtributoExportavel(coluna = "Data de Admissão", formato = FormatoAtributoEnum.HORA)
        private Date admissao;
        
        @AtributoExportavel(coluna = "Salário (R$)", formato = FormatoAtributoEnum.MOEDA)
        private BigDecimal salario;
        
        @AtributoExportavel(coluna = "Gestor")
        private Boolean gestor;
        
        public Funcionario(String nome, Date admissao, BigDecimal salario) {
            this.nome = nome;
            this.admissao = admissao;
            this.salario = salario;
            this.gestor = false;
        }
        
        public Funcionario(String nome, Date admissao, BigDecimal salario, Boolean gestor) {
            this.nome = nome;
            this.admissao = admissao;
            this.salario = salario;
            this.gestor = gestor;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Date getAdmissao() {
            return admissao;
        }

        public void setAdmissao(Date admissao) {
            this.admissao = admissao;
        }

        public BigDecimal getSalario() {
            return salario;
        }

        public void setSalario(BigDecimal salario) {
            this.salario = salario;
        }

        public Boolean getGestor() {
            return gestor;
        }

        public void setGestor(Boolean gestor) {
            this.gestor = gestor;
        }
    }
}
