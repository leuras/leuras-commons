# Leuras Commons API

![GitHub release (latest by date)](https://img.shields.io/github/v/release/leuras/leuras-commons)
[![Build Status](https://travis-ci.org/leuras/leuras-commons.svg?branch=master)](https://travis-ci.org/leuras/leuras-commons)
![GitHub](https://img.shields.io/github/license/leuras/leuras-commons)
[![javadoc](https://javadoc.io/badge2/com.github.leuras/leuras-commons/javadoc.svg)](https://javadoc.io/doc/com.github.leuras/leuras-commons)

Este projeto visa prover classes utilitárias que simplifiquem tarefas do cotidiano de desenvolvedores java, tornando mais simples a implementação de soluções voltadas especificamente para o âmbito nacional brasileiro.

## Funcionalidades
A biblioteca provê suporte a:

- Builder para envio de e-mails (incluindo anexos e suporte à templates em HTML)
- Exportação de Jasper Reports em **PDF**, **DOCX**, **XLSX** e **HTML**.
- Manipulação de arquivos.
- Validação e formatação de CPF e CNPJ.
- Manipulação de datas.
- Manipulação de números (moedas, percentagem, etc).
- Enumeradores contextualizados.

## Instalação

### Apache Maven
```
<dependency>
  <groupId>com.github.leuras</groupId>
  <artifactId>leuras-commons</artifactId>
  <version>1.1.0</version>
</dependency>
```

### Gradle
```
implementation 'com.github.leuras:leuras-commons:1.1.0'
```

## Documentação
A documentação javadoc da API está disponível online pelo [javadoc.io](https://javadoc.io/doc/com.github.leuras/leuras-commons/latest/index.html) ou na pasta [docs](/docs) do repositório. Caso haja alguma dúvida sobre o uso de algum método especifico, os testes unitários podem ajudar. O projeto conta com mais de 90% de cobertura de código.

## Licença
Este projeto está licenciado sob a [GPL v3](https://www.gnu.org/licenses/gpl-3.0.pt-br.html). Veja o arquivo `NOTICE.txt` para mais informações.
