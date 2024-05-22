# Trabalho Prático de Teste de Software
## 1. Grupo
Frederico Abbud Reis <br/>
Joao Lucas Rocha dos Santos <br/>
Lucas Braz Rossetti <br/>
Lucas Gabriel Silveira Chaves <br/>

## 2. Sobre o sistema
O sistema é uma implementação simplificada de uma urna eletrônica, similar à utilizada nas eleições brasileiras. As entidades que constituem o sistema são: `Urn`, `Candidate`, `Credential`, `Party`, `Voter`.
O sistema não utiliza um banco de dados real, mas há um mockup utilizado para simular as consultas, especificado na pasta `DataBase`.
A classe principal do sistema é `Urn`, especificada no arquivo `Urn.java`, que implementa métodos de inicialização, finalização, autenticação de votante e leitura de candidatos escolhidos pelo votante.
O arquivo `Statistics.java` provê as estátiscas dos resultados das eleições.
Os testes comparam saídas obtidas com esperadas provindas de arquivos da pasta `TestsIO`.

## 3. Sobre as tecnologias utilizadas
Para a construção do sistema, utilizou-se Java (versão 17), JUnit pra a construção de testes e Maven para realizar a build e os testes automatizados utilizados no CI.
