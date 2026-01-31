> Projeto acadêmico/pessoal em Java focado em estruturas de dados, arquitetura em camadas e controle transacional (undo/Stack).

# Sistema de Loja em Java

## Descrição
Sistema em terminal para controle de produtos, vendas, estoque e caixa.
Inclui histórico de vendas e desfazer última operação usando Stack (LIFO).

## Funcionalidades
- Cadastro e busca de produtos
- Venda com validação de estoque
- Controle de caixa
- Histórico de vendas
- Desfazer última venda (CTRL+Z)
- Relatório com total de vendas

## Estruturas de Dados
- HashMap → produtos
- List → histórico de vendas
- Stack → desfazer última venda

## Como executar
```bash
javac src/*.java
java src.Main

