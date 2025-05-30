# Sistema Acadêmico - FCTE

## Descrição do Projeto

Desenvolvimento de um sistema acadêmico para gerenciar alunos, disciplinas, professores, turmas, avaliações e frequência, utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

O enunciado do trabalho pode ser encontrado aqui:
- [Trabalho 1 - Sistema Acadêmico](https://github.com/lboaventura25/OO-T06_2025.1_UnB_FCTE/blob/main/trabalhos/ep1/README.md)

## Dados do Aluno

- **Nome completo:** Isabella Lacerda Lima
- **Matrícula:** 232003269
- **Curso:** Engenharia de Software
- **Turma:** 06

---

## Instruções para Compilação e Execução

1. **Compilação:**
  javac Main.java
   
3. **Execução:**  
   java Main

4. **Estrutura de Pastas:**  
├── src/
│   ├── br/com/fcte/sistema/          # Pacote principal do sistema
│   │   ├── controller/              # Controladores responsáveis pela lógica (AlunoController, AvaliacaoController, DisciplinaController)
│   │   ├── model/                   # Classes de modelo de dados (Pessoa, Aluno, Disciplina, Turma, Avaliacao)
│   │   ├── utils/                   # Classes utilitárias (FileManager, Menu)
│   │   ├── Main.java                # Classe principal que inicia o sistema
│
├── bin/                             # Diretório onde os arquivos .class compilados serão armazenados (criado durante a compilação)
│
├── data/                            # Diretório para arquivos de dados (.csv) utilizados pelo sistema (gerados/salvos pelo FileManager)
│
├── .classpath                       # Arquivo de configuração do projeto (gerado pelo Eclipse)
├── .gitignore                       # Arquivo para ignorar arquivos/pastas no controle de versão
├── .project                         # Arquivo de configuração do Eclipse
├── README.md                        # Arquivo de instruções e documentação do projeto

3. **Versão do JAVA utilizada:**  
   JRE (23.0.2)

---

## Vídeo de Demonstração

- https://youtu.be/JRR1woAJ4DE?si=bn_B6AXgqnQjZY0u

---

## Prints da Execução

1. Menu Principal:  
<img width="1280" alt="print:menu" src="https://github.com/user-attachments/assets/76248e68-37bb-43a2-9336-f380ea5b5922" />

2. Cadastro de Aluno:  
   ![image](https://github.com/user-attachments/assets/5872b624-16f5-4b19-9478-6722aa8b7a3a)

3. Relatório de Frequência/Notas:  

![image](https://github.com/user-attachments/assets/7a67f697-ffb3-41d2-9785-b8a209faf4bd)
![image](https://github.com/user-attachments/assets/64b3d8dd-0160-4e44-bd52-328e96f25eda)


---

## Principais Funcionalidades Implementadas

- [ ] Cadastro, listagem, matrícula e trancamento de alunos (Normais e Especiais)
- [ ] Cadastro de disciplinas e criação de turmas (presenciais e remotas)
- [ ] Matrícula de alunos em turmas, respeitando vagas e pré-requisitos
- [ ] Lançamento de notas e controle de presença
- [ ] Cálculo de média final e verificação de aprovação/reprovação
- [ ] Relatórios de desempenho acadêmico por aluno, turma e disciplina
- [ ] Persistência de dados em arquivos (.txt ou .csv)
- [ ] Tratamento de duplicidade de matrículas
- [ ] Uso de herança, polimorfismo e encapsulamento

---

## Observações (Extras ou Dificuldades)

- 1. Manipulação de Arquivos e I/O (Input/Output)

Leitura e escrita de arquivos (FileReader, FileWriter, BufferedReader, PrintWriter)
Controle de diretórios (mkdirs(), exists())
Formatação de dados para persistência (CSV)

2. Conversão de Tipos e Parsing

Integer.parseInt() para converter strings em números
Processamento manual de strings com split() para ler CSV
Conversão entre objetos e sua representação em texto

3. Gerenciamento de Recursos

Uso de try-with-resources para garantir fechamento adequado de arquivos
Controle explícito de fluxos de dados (streams)

4. Tratamento de Erros Específicos

Captura e tratamento de IOException
Verificações manuais de consistência de dados

5. Persistência de Relacionamentos Complexos

Reconstrução de grafos de objetos (como pré-requisitos de disciplinas)
Mapeamento entre identidades (códigos) e objetos

6. Serialização Customizada

Implementação manual de serialização/deserialização em formato CSV
Controle explícito de versão/formato dos dados persistentes

7. Gerenciamento de Estado Persistente

Manutenção da consistência entre objetos em memória e sua representação em disco
Controle de concorrência implícito (cada operação abre/fecha arquivos)

---

## Contato

- isalima2004@gmail.com
