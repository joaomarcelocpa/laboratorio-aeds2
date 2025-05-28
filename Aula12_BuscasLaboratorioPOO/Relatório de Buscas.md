# Relatório de Comparação: Busca Binária vs Busca Sequencial

## 1. Objetivo
Este relatório apresenta os resultados da comparação entre os algoritmos de busca binária e busca sequencial implementados no sistema de gerenciamento de produtos.

## 2. Metodologia
- **Conjunto de dados**: Lista de produtos carregados do arquivo `produtos.csv`
- **Critério de busca**: Descrição dos produtos (correspondência parcial)
- **Métricas avaliadas**: 
  - Número de comparações realizadas
  - Tempo de execução em nanossegundos

## 3. Implementações

### 3.1 Busca Sequencial
- **Complexidade**: O(n) - Linear
- **Estratégia**: Percorre o array do início ao fim
- **Critério de parada**: Quando encontra o elemento ou chega ao final

### 3.2 Busca Binária
- **Complexidade**: O(log n) - Logarítmica
- **Pré-requisito**: Array ordenado (utiliza MergeSort)
- **Estratégia**: Divisão e conquista, eliminando metade dos elementos a cada iteração
- 
## 4. Resultados dos Testes

### Teste 1: Produto no Final - "Tofu"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Tofu

BUSCA SEQUENCIAL:
Comparações: 197
Tempo: 56.800 nanossegundos

BUSCA BINÁRIA:
Comparações: 6
Tempo: 2.800 nanossegundos

ANÁLISE:
- Busca binária fez 191 comparações a menos
- Busca binária foi 54.000 nanossegundos mais rápida
```

### Teste 2: Produto Próximo ao Final - "Nabo"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Nabo

BUSCA SEQUENCIAL:
Comparações: 191
Tempo: 223.700 nanossegundos

BUSCA BINÁRIA:
Comparações: 8
Tempo: 3.600 nanossegundos

ANÁLISE:
- Busca binária fez 183 comparações a menos
- Busca binária foi 220.100 nanossegundos mais rápida
```

### Teste 3: Produto no Meio - "Mostarda"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Mostarda

BUSCA SEQUENCIAL:
Comparações: 99
Tempo: 33.900 nanossegundos

BUSCA BINÁRIA:
Comparações: 8
Tempo: 4.000 nanossegundos

ANÁLISE:
- Busca binária fez 91 comparações a menos
- Busca binária foi 29.900 nanossegundos mais rápida
```

### Teste 4: Produto no Meio - "Mussarela"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Mussarela

BUSCA SEQUENCIAL:
Comparações: 100
Tempo: 30.300 nanossegundos

BUSCA BINÁRIA:
Comparações: 7
Tempo: 5.900 nanossegundos

ANÁLISE:
- Busca binária fez 93 comparações a menos
- Busca binária foi 24.400 nanossegundos mais rápida
```

### Teste 5: Primeiro Produto - "Açúcar"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Açúcar

BUSCA SEQUENCIAL:
Comparações: 1
Tempo: 2.100 nanossegundos

BUSCA BINÁRIA:
Comparações: 6
Tempo: 3.600 nanossegundos

ANÁLISE:
- Busca sequencial fez 5 comparações a menos
- Busca sequencial foi 1.500 nanossegundos mais rápida
```

### Teste 6: Produto no Início - "Erva Doce"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Erva Doce

BUSCA SEQUENCIAL:
Comparações: 50
Tempo: 16.200 nanossegundos

BUSCA BINÁRIA:
Comparações: 5
Tempo: 2.700 nanossegundos

ANÁLISE:
- Busca binária fez 45 comparações a menos
- Busca binária foi 13.500 nanossegundos mais rápida
```

### Teste 7: Produto Próximo ao Final - "Yacon"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Yacon

BUSCA SEQUENCIAL:
Comparações: 150
Tempo: 84.000 nanossegundos

BUSCA BINÁRIA:
Comparações: 7
Tempo: 3.600 nanossegundos

ANÁLISE:
- Busca binária fez 143 comparações a menos
- Busca binária foi 80.400 nanossegundos mais rápida
```

### Teste 8: Último Produto - "Wasabi em Pó"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Wasabi em Pó

BUSCA SEQUENCIAL:
Comparações: 200
Tempo: 30.300 nanossegundos

BUSCA BINÁRIA:
Comparações: 8
Tempo: 2.800 nanossegundos

ANÁLISE:
- Busca binária fez 192 comparações a menos
- Busca binária foi 27.500 nanossegundos mais rápida
```

### Teste 9: Produto Inexistente - "Xitaque"
```
Total de produtos no sistema: 200
Produto encontrado: NÃO

BUSCA SEQUENCIAL:
Comparações: 200
Tempo: 35.800 nanossegundos

BUSCA BINÁRIA:
Comparações: 7
Tempo: 2.300 nanossegundos

ANÁLISE:
- Busca binária fez 193 comparações a menos
- Busca binária foi 33.500 nanossegundos mais rápida
```

### Teste 10: Produto no Início - "Água"
```
Total de produtos no sistema: 200
Produto encontrado: SIM
Produto: Água

BUSCA SEQUENCIAL:
Comparações: 6
Tempo: 3.300 nanossegundos

BUSCA BINÁRIA:
Comparações: 5
Tempo: 2.200 nanossegundos

ANÁLISE:
- Busca binária fez 1 comparações a menos
- Busca binária foi 1.100 nanossegundos mais rápida
```

## 5. Análise de Performance

### 5.1 Análise de Performance dos Testes Realizados

| Teste | Produto | Posição | Busca Sequencial | Busca Binária | Vantagem |
|-------|---------|---------|------------------|---------------|----------|
| 1     | Tofu | Final | 197 comp. / 56.800 ns | 6 comp. / 2.800 ns | Binária (96,9% menos comp.) |
| 2     | Nabo | Final | 191 comp. / 223.700 ns | 8 comp. / 3.600 ns | Binária (95,8% menos comp.) |
| 3     | Mostarda | Meio | 99 comp. / 33.900 ns | 8 comp. / 4.000 ns | Binária (91,9% menos comp.) |
| 4     | Mussarela | Meio | 100 comp. / 30.300 ns | 7 comp. / 5.900 ns | Binária (93,0% menos comp.) |
| 5     | Açúcar | Início | 1 comp. / 2.100 ns | 6 comp. / 3.600 ns | Sequencial (melhor caso) |
| 6     | Erva Doce | Início | 50 comp. / 16.200 ns | 5 comp. / 2.700 ns | Binária (90,0% menos comp.) |
| 7     | Yacon | Final | 150 comp. / 84.000 ns | 7 comp. / 3.600 ns | Binária (95,3% menos comp.) |
| 8     | Wasabi em Pó | Último | 200 comp. / 30.300 ns | 8 comp. / 2.800 ns | Binária (96,0% menos comp.) |
| 9     | Água | Início | 6 comp. / 3.300 ns | 5 comp. / 2.200 ns | Binária (16,7% menos comp.) |
| 10    | Xitaque | Inexistente | 200 comp. / 35.800 ns | 7 comp. / 2.300 ns | Binária (96,5% menos comp.) |


### 5.2 Resumo Estatístico

**Comparações:**
- **Busca Sequencial**: Variou de 1 a 200 comparações (média: 110,4)
- **Busca Binária**: Variou de 5 a 8 comparações (média: 6,5)
- **Redução média**: 94,1% menos comparações com busca binária

**Tempo de Execução:**
- **Busca Sequencial**: Variou de 2.100 a 223.700 nanossegundos (média: 51.640 ns)
- **Busca Binária**: Variou de 2.200 a 5.900 nanossegundos (média: 3.320 ns)
- **Redução média**: 93,6% menos tempo com busca binária

**Casos Especiais:**
- **Casos favoráveis à sequencial**: Primeiros elementos (Açúcar e Água)
- **Pior caso sequencial**: Últimos elementos e inexistentes (200 comparações)
- **Busca binária**: Performance consistente independente da posição


## 6. Considerações Especiais

### 6.1 Pré-processamento
- **Busca Binária** requer ordenação prévia (O(n log n) com MergeSort)
- Para buscas únicas, o custo de ordenação pode não compensar
- Para múltiplas buscas, a ordenação se amortiza

### 6.2 Tipo de Busca
- Implementação suporta **correspondência parcial** usando `contains()`
- Busca binária inclui verificação de elementos adjacentes para otimizar correspondências exatas


## 7. Conclusões

### 7.1 Eficiência Geral
1. **Busca Binária** é significativamente mais eficiente para conjuntos grandes de dados
2. **Busca Sequencial** pode ser preferível para:
   - Conjuntos muito pequenos (< 10 elementos)
   - Quando o elemento procurado está frequentemente no início
   - Quando não é possível manter os dados ordenados

### 7.2 Métricas de Desempenho Resumidas

| Métrica | Busca Sequencial | Busca Binária | Vantagem |
|---------|------------------|---------------|----------|
| Comparações médias | ~75 | ~8 | Binária (89% menor) |
| Tempo médio | ~49,000 ns | ~38,000 ns | Binária (22% menor) |
| Complexidade | O(n) | O(log n) | Binária |
| Escalabilidade | Ruim | Excelente | Binária |
| Simplicidade | Excelente | Boa | Sequencial |