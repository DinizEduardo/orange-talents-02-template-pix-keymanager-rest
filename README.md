# Desafio pix - Rest

## Setup do Projeto - Key-Manager REST API

### Objetivo

Sabemos que está ansioso(a) para começar a codificar, porém antes precisamos preparar nosso ambiente, portanto esse
será nosso objetivo nessa tarefa.

### Descrição

Nessa tarefa, precisamos criar um projeto para expor algumas das funcionalidades implementadas no **Key-Manager** para nosso frontend mobile através de uma **API REST**, para tal, temos alguns pré-requisitos de linguagem de programação e tecnologia, pois precisamos que esse projeto seja evoluído e mantido por anos, portanto é extremamente importante a escolha das mesmas.

Com base no nosso contexto atual, nosso mais experiente membro do time sugeriu os seguintes itens:

Linguagem de programação

- [Kotlin](https://kotlinlang.org/)

Tecnologias

- [Micronaut](http://micronaut.io/)

Gerenciador de dependência

- [Gradle](https://gradle.org/)

### Resultado Esperado

Projeto gerado com as tecnologias sugeridas:

- [Kotlin](https://kotlinlang.org/)
- [Micronaut](http://micronaut.io/)
- [Gradle](https://gradle.org/)

## Expondo para o frontend: Registro da chave Pix

### Necessidades

Nosso frontend precisa registrar uma nova chave Pix para que o usuário possa gerar cobranças e efetuar pagamentos de cobranças diretamente do nosso aplicativo mobile. Portanto, a primeira funcionalidade que iremos expor via nossa API REST é justamente a operação para [registrar uma nova chave Pix](005-registrando-uma-nova-chave-pix.md).

### Restrições

Para registrar uma chave Pix, precisamos que o usuário informe os dados necessários para tal. Você pode levantar essas informações com detalhes a partir da [atividade de registro de chaves Pix que implementamos no módulo Key-Manager gRPC](005-registrando-uma-nova-chave-pix.md).

Lembre-se de seguir as boas práticas de design de APIs REST, pois o time de mobile leva isso em consideração.

### Resultado Esperado

- Em caso de sucesso, a chave Pix deve ser registrada e armazenada no sistema;

- Em caso de chave já existente, deve-se retornar o status de erro `422-UNPROCESSABLE_ENTITY` com uma mensagem amigável para o usuário;

- Em caso de erro, deve-se retornar o erro específico e amigável para o usuário final;