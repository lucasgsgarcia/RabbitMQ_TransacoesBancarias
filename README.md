# RabbitMQ_TransacoesBancarias

Um projeto simples utilizando Event-Driven Architecture com RabbitMQ. </br>
Funciona da seguinte forma: </br>
1. Um producer lê as linhas de um arquivo .csv - que representa uma lista de transações feitas por pessoas, e o coloca na fila inicial chamada "transacoes.financeiras".</br>
2. O primeiro consumer lê esses registros, classificando-os como uma simples transação ou então uma transação suspeita, a condição é que a transação seja de mais de $40.000.</br>
3. Se essa condição for satisfeita, ele jogará em um Exchange do tipo Fanout, que entregará integralmente para duas outras filas - uma da polícia federal, outra da receita federal.</br>
4. Dentro de cada respectiva fila, existe um consumer para processar essas transações da forma como quiserem.</br>
<div align="center">
<img align="center" alt="Estrutura" src="https://github.com/lucasgsgarcia/RabbitMQ_TransacoesBancarias/blob/master/RabbitMQ_Estrutura.png">
</div>
