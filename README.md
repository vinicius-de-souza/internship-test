# Analisador de HTML - Documentação Técnica

## Visão Geral
O projeto consiste em um analisador de HTML que identifica o texto mais profundamente aninhado em uma estrutura HTML, seja ela bem formada ou não.

## Estrutura do Projeto

### 1. HtmlAnalyzer.java
- **Função Principal**: Ponto de entrada do programa
- **Responsabilidades**:
  - Recebe URL como argumento via linha de comando
  - Coordena o processo de busca e análise do HTML
  - Gerencia e exibe erros de execução

### 2. UrlFetcher.java
- **Função**: Recuperação de conteúdo HTML
- **Responsabilidades**:
  - Conecta-se à URL fornecida
  - Lê o conteúdo HTML
  - Retorna o HTML como uma string

### 3. HtmlParser.java
- **Função**: Análise do conteúdo HTML
- **Responsabilidades**:
  - Processa o HTML linha por linha
  - Mantém controle da profundidade usando pilha
  - Identifica tags de abertura/fechamento e texto
  - Detecta HTML mal formado
  - Encontra o texto mais profundo

### 4. HtmlNode.java
- **Função**: Estrutura de dados para nós HTML
- **Atributos**:
  - content: conteúdo do nó
  - depth: profundidade do nó
  - type: tipo do nó (definido pelo enum NodeType)

### 5. NodeType.java
- **Função**: Enum para tipos de nós
- **Valores**:
  - TEXT: conteúdo textual
  - OPENING_TAG: tag de abertura
  - CLOSING_TAG: tag de fechamento

## Fluxo de Execução

1. **Entrada**
   - Usuário fornece URL como argumento

2. **Recuperação**
   - UrlFetcher busca o conteúdo HTML da URL

3. **Processamento**
   - HtmlParser analisa o conteúdo usando:
     - Pilha para rastrear profundidade
     - Identificação de tipos de nós
     - Validação de estrutura HTML

4. **Resultado**
   - Retorna um dos três possíveis resultados:
     - Texto mais profundo encontrado
     - "malformed HTML"
     - "URL connection error"

## Características Técnicas

### Estrutura de Dados
- **Stack<String>**: Utilizada para rastrear tags HTML
- **HtmlNode**: Armazena informações de cada nó

### Validações
- Verifica HTML mal formado
- Valida correspondência de tags
- Trata erros de conexão URL

### Limitações
- Não processa tags HTML na mesma linha
- Sensível a espaços e formatação
- Requer URL válida e acessível

## Uso

```bash
javac HtmlAnalyzer.java
java HtmlAnalyzer <url>
```

## Exemplos de Saída

```
# Sucesso
Texto mais profundo encontrado: "texto aqui"

# HTML Mal Formado
Result: malformed HTML

# Erro de URL
URL connection error
```