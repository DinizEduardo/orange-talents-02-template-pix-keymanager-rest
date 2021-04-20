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

## Expondo para o frontend: Remoção de uma chave Pix existente

### Necessidades

Dessa vez, nosso frontend precisa permitir que seu usuário consiga excluir suas chaves Pix cadastradas diretamente do aplicativo mobile, desse modo ele poderá substituir as chaves sempre que necessário. Portanto, devemos expor em nossa API REST a [operação de exclusão de chaves](010-removendo-uma-chave-pix-existente.md).

### Restrições

Para excluir uma chave Pix existente, precisamos que o usuário informe os dados necessários para tal. Você pode levantar essas informações com detalhes a partir da [atividade de remoção de chaves Pix que implementamos no módulo Key-Manager gRPC](010-removendo-uma-chave-pix-existente.md).

Lembre-se de seguir as boas práticas de design de APIs REST, pois o time de mobile leva isso em consideração.

### Resultado Esperado

- Em caso de sucesso, a chave Pix deve ser excluída do sistema;

- Em caso de chave não encontrada, deve-se retornar status de erro `404-NOT_FOUND` com uma mensagem amigável para o usuário;

- Em caso de erro, deve-se retornar o erro específico e amigável para o usuário final;

## Expondo para o frontend: Detalhamento de uma chave Pix

### Necessidades

Agora precisamos permitir que o frontend consulte uma chave Pix para que nosso usuário consiga ver os detalhes da sua chave. Basicamente, o que iremos fazer é expor em nossa API REST a [operação de consulta de uma chave Pix](020-consultando-os-dados-de-uma-chave-pix.md).

### Restrições

Para esta funcionalidade, precisamos somente consultar uma chave Pix pelo **Pix ID** e **identificador do cliente**. Para isso, você pode levantar essas informações com detalhes a partir da [atividade de consulta de chave Pix que implementamos no módulo Key-Manager gRPC](020-consultando-os-dados-de-uma-chave-pix.md).

Lembre-se de seguir as boas práticas de design de APIs REST, pois o time de mobile leva isso em consideração.

### Resultado Esperado

- Em caso de sucesso, deve-se retornar os dados da chave Pix encontrada;

- Em caso de chave não encontrada, deve-se retornar status de erro `404-NOT_FOUND` com uma mensagem amigável para o usuário;

- Em caso de erro, deve-se retornar o erro específico e amigável para o usuário final;

## Expondo para o frontend: Listagem de todas as chaves Pix

### Necessidades

Nosso usuário precisa visualizar todas suas chaves Pix cadastradas até o momento, para isso precisamos expor em nossa API REST a [operação de listagem de chaves Pix](021-listando-todas-as-chaves-pix-do-cliente.md).

### Restrições

Listar todas as chaves Pix do usuário a partir das informações da [atividade de listagem de chaves Pix que implementamos no módulo Key-Manager gRPC](021-listando-todas-as-chaves-pix-do-cliente.md).

Lembre-se de seguir as boas práticas de design de APIs REST, pois o time de mobile leva isso em consideração.

### Resultado Esperado

- Em caso de sucesso, deve-se retornar uma coleção com todas as chaves Pix;

- Em caso de erro, deve-se retornar o erro específico e amigável para o usuário final;

## Testando a Exposição do serviço de registro de chave Pix

### Necessidades

Com o fim da implementação do endpoint responsável por [expor o registro de uma chave Pix](030-expondo-para-o-frontend-registro-da-chave-pix.md) o próximo passo é cobrir a funcionalidade com testes antes de mandá-la para produção.

Nessa tarefa não estamos mais expondo uma API gRPC, mas sim consumindo uma API criada por nós. Consegue perceber o impacto nos testes? Tudo que aprendemos até agora se aplica, mas em uma perspectiva diferente, pois agora somos um cliente gRPC.

Existem duas características importantes que ajudam a definir bons testes de unidade: **eles são isolados e independentes uns dos outros**. Isso quer dizer que um teste de unidade não quebra com mudanças no ambiente (data e hora, fuso horário, idoma, variáveis de ambiente etc) e que ele também pode ser executado em qualquer ordem (um teste não depende de outro). Se você conseguir aplicar essas mesmas características para os testes de integração então você tem o melhor dos dois mundos!

Você acha que consegue aplicar as características acima?

### Restrições

Escrever testes automatizados para a API REST que cuida da Exposição de Registro de chave Pix de tal forma que os testes garantam o que foi especificado na atividade.

Para guia-lo(a) nessa atividade, elencamos algumas restrições e pontos de atenção:

- favoreça a escrita de **testes de unidade** para lógicas de negócio que não fazem integração com serviços externos (banco de dados, APIs REST, mensageria, sistema de arquivos etc);
- favoreça a escrita de **testes de integração** para lógicas de negócio que conversam com serviços externos, como banco de dados, APIs REST etc;
- para tornar o teste mais próximo da produção, nos testes de integração **levante um servidor gRPC embarcado** e consuma os endpoints nos testes de integração;
- lembre-se de **testar os fluxos alternativos**, como cenários de erros do sistema ou entrada de dados inválida pelo usuário/serviço;
- favoreça o uso de um **banco de dados em memória** para facilitar a limpeza dos dados e simplificar o ambiente na sua pipeline de CI/CD;
- favoreça **mocks para chamadas à serviços externos**, como a API REST do Sistema ERP-ITAU e do Sistema Pix do BCB;
- fique sempre de olho na **cobertura do seu código**, especialmente nas branches de código, como `if`, `else`, `while`, `for`, `try-catch` etc;

Quer entender por que adotamos as restrições acima? [Assiste a esse vídeo](https://www.youtube.com/watch?v=IMvjNpG6320) para entender os detalhes do porquê acreditamos que esse é um bom caminho.

### Resultado Esperado

O que esperamos ao final dessa atividade e que também consideramos importante:

- ter um percentual de cobertura de no mínimo **90% do código de produção**;
- ter coberto cenários felizes (happy-path) e fluxos alternativos;
- não precisar de instruções especiais para preparar o ambiente ou para rodar sua bateria de testes;
- sua bateria de testes deve rodar tanto na sua IDE quanto via **linha de comando**;
- que outro desenvolvedor(a) do time consiga rodar facilmente a bateria de testes do seu serviço;

## Testando a Exposição do serviço de remoção de uma chave Pix existente

### Necessidades

Agora vamos escrever os testes para o código de produção do endpoint responsável por [expor o exclusão de uma chave Pix existente](035-expondo-para-o-frontend-remocao-de-uma-chave-pix-existente.md).

Quanto **menor a dependência com meio externo melhor para manutenção dos testes**. Depender do estado de bancos de dados, cache e outros sistemas terceiros pode simplificar a escrita do código de testes, da preparação do ambiente e principalmente diminuir a fragilidade dos testes.

Lembre-se, tudo que estudamos e aprendemos até este momento também se aplica para testes de APIs REST e também para código que consome alguma API gRPC.

### Restrições

Escrever testes automatizados para a API REST que cuida da Exposição de Remoção de uma chave Pix existente de modo que os testes garantam o que foi especificado na atividade.

Para guia-lo(a) nessa atividade, elencamos algumas restrições e pontos de atenção:

- favoreça a escrita de **testes de unidade** para lógicas de negócio que não fazem integração com serviços externos (banco de dados, APIs REST, mensageria, sistema de arquivos etc);
- favoreça a escrita de **testes de integração** para lógicas de negócio que conversam com serviços externos, como banco de dados, APIs REST etc;
- para tornar o teste mais próximo da produção, nos testes de integração **levante um servidor gRPC embarcado** e consuma os endpoints nos testes de integração;
- lembre-se de **testar os fluxos alternativos**, como cenários de erros do sistema ou entrada de dados inválida pelo usuário/serviço;
- favoreça o uso de um **banco de dados em memória** para facilitar a limpeza dos dados e simplificar o ambiente na sua pipeline de CI/CD;
- favoreça **mocks para chamadas à serviços externos**, como a API REST do Sistema ERP-ITAU e do Sistema Pix do BCB;
- fique sempre de olho na **cobertura do seu código**, especialmente nas branches de código, como `if`, `else`, `while`, `for`, `try-catch` etc;

Quer entender por que adotamos as restrições acima? [Assiste a esse vídeo](https://www.youtube.com/watch?v=IMvjNpG6320) para entender os detalhes do porquê acreditamos que esse é um bom caminho.

### Resultado Esperado

O que esperamos ao final dessa atividade e que também consideramos importante:

- ter um percentual de cobertura de no mínimo **90% do código de produção**;
- ter coberto cenários felizes (happy-path) e fluxos alternativos;
- não precisar de instruções especiais para preparar o ambiente ou para rodar sua bateria de testes;
- sua bateria de testes deve rodar tanto na sua IDE quanto via **linha de comando**;
- que outro desenvolvedor(a) do time consiga rodar facilmente a bateria de testes do seu serviço;

## Testando a Exposição do serviço de detalhamento de uma chave Pix existente

### Necessidades

É hora de cobrir o código de produção do endpoint que [expõe o detalhamento de uma chave Pix existente](040-expondo-para-o-frontend-detalhamento-de-uma-chave-pix.md) com testes automatizados.

Lembre-se, tudo que estudamos e aprendemos até este momento também se aplica para testes de APIs REST e também para código que consome alguma API gRPC.

### Restrições

Escrever testes automatizados para a API REST que cuida da Exposição do Detalhamento de uma chave Pix existente de modo que os testes garantam o que foi especificado na atividade.

Para guia-lo(a) nessa atividade, elencamos algumas restrições e pontos de atenção:

- favoreça a escrita de **testes de unidade** para lógicas de negócio que não fazem integração com serviços externos (banco de dados, APIs REST, mensageria, sistema de arquivos etc);
- favoreça a escrita de **testes de integração** para lógicas de negócio que conversam com serviços externos, como banco de dados, APIs REST etc;
- para tornar o teste mais próximo da produção, nos testes de integração **levante um servidor gRPC embarcado** e consuma os endpoints nos testes de integração;
- lembre-se de **testar os fluxos alternativos**, como cenários de erros do sistema ou entrada de dados inválida pelo usuário/serviço;
- favoreça o uso de um **banco de dados em memória** para facilitar a limpeza dos dados e simplificar o ambiente na sua pipeline de CI/CD;
- favoreça **mocks para chamadas à serviços externos**, como a API REST do Sistema ERP-ITAU e do Sistema Pix do BCB;
- fique sempre de olho na **cobertura do seu código**, especialmente nas branches de código, como `if`, `else`, `while`, `for`, `try-catch` etc;

Quer entender por que adotamos as restrições acima? [Assiste a esse vídeo](https://www.youtube.com/watch?v=IMvjNpG6320) para entender os detalhes do porquê acreditamos que esse é um bom caminho.

### Resultado Esperado

O que esperamos ao final dessa atividade e que também consideramos importante:

- ter um percentual de cobertura de no mínimo **90% do código de produção**;
- ter coberto cenários felizes (happy-path) e fluxos alternativos;
- não precisar de instruções especiais para preparar o ambiente ou para rodar sua bateria de testes;
- sua bateria de testes deve rodar tanto na sua IDE quanto via **linha de comando**;
- que outro desenvolvedor(a) do time consiga rodar facilmente a bateria de testes do seu serviço;