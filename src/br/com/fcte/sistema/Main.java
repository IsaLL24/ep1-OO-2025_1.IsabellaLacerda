package src.br.com.fcte.sistema;

import java.util.List;
import java.util.Map;

import src.br.com.fcte.sistema.controller.AlunoController;
import src.br.com.fcte.sistema.controller.AvaliacaoController;
import src.br.com.fcte.sistema.controller.DisciplinaController;
import src.br.com.fcte.sistema.models.Aluno;
import src.br.com.fcte.sistema.models.Disciplina;
import src.br.com.fcte.sistema.models.Turma;
import src.br.com.fcte.sistema.utils.Menu;

// Classe principal do sistema acadêmico.
 
public class Main {
    private static AlunoController alunoController;
    private static DisciplinaController disciplinaController;
    private static AvaliacaoController avaliacaoController;
    private static Menu menu;
    // Removido atributo não utilizado 'idade'
    
    public static void main(String[] args) {
        // Inicializar controladores
        alunoController = new AlunoController();
        disciplinaController = new DisciplinaController();
        avaliacaoController = new AvaliacaoController(alunoController, disciplinaController);
        
        // Inicializar menu
        menu = new Menu();
        
        // Exibir menu principal
        boolean sair = false;
        while (!sair) {
            int opcao = menu.exibirMenuPrincipal();
            
            switch (opcao) {
                case 1:
                    modoAluno();
                    break;
                case 2:
                    modoDisciplina();
                    break;
                case 3:
                    modoAvaliacao();
                    break;
                case 0:
                    sair = true;
                    System.out.println("\nSaindo do sistema...");
                    break;
            }
        }
        
        // Fechar menu
        menu.fechar();
    }
    
    // Executa o modo aluno.
     
    private static void modoAluno() {
        boolean voltar = false;
        
        while (!voltar) {
            int opcao = menu.exibirMenuAluno();
            
            switch (opcao) {
                case 1:
                    cadastrarAlunoNormal();
                    break;
                case 2:
                    cadastrarAlunoEspecial();
                    break;
                case 3:
                    editarAluno();
                    break;
                case 4:
                    listarAlunos();
                    break;
                case 5:
                    matricularAluno();
                    break;
                case 6:
                    trancarDisciplina();
                    break;
                case 7:
                    trancarSemestre();
                    break;
                case 0:
                    voltar = true;
                    break;
            }
        }
    }
    
    // Executa o modo disciplina/turma.
     
    private static void modoDisciplina() {
        boolean voltar = false;
        
        while (!voltar) {
            int opcao = menu.exibirMenuDisciplina();
            
            switch (opcao) {
                case 1:
                    cadastrarDisciplina();
                    break;
                case 2:
                    adicionarPreRequisito();
                    break;
                case 3:
                    criarTurma(); // Descomentado
                    break;
                case 4:
                    listarDisciplinas(); // Descomentado
                    break;
                case 5:
                    listarTurmas(); // Descomentado
                    break;
                case 6:
                    listarTurmasPorDisciplina(); // Descomentado
                    break;
                case 7:
                    listarTurmasPorProfessor(); // Descomentado
                    break;
                case 0:
                    voltar = true;
                    break;
            }
        }
    }
    
    // Executa o modo avaliação/frequência.
     
    private static void modoAvaliacao() {
        boolean voltar = false;
        
        while (!voltar) {
            int opcao = menu.exibirMenuAvaliacao();
            
            switch (opcao) {
                case 1:
                    lancarNota(); // Descomentado
                    break;
                case 2:
                    lancarFrequencia(); // Descomentado
                    break;
                case 3:
                    calcularMediaFinal(); // Descomentado
                    break;
                case 4:
                    calcularFrequencia(); // Descomentado
                    break;
                case 5:
                    verificarAprovacao(); // Descomentado
                    break;
                case 6:
                    gerarRelatorioPorTurma(); // Descomentado
                    break;
                case 7:
                    gerarRelatorioPorDisciplina(); // Descomentado
                    break;
                case 8:
                    gerarRelatorioPorProfessor(); // Descomentado
                    break;
                case 9:
                    gerarBoletimAluno(); // Descomentado
                    break;
                case 0:
                    voltar = true;
                    break;
            }
        }
    }
    
    // --- Métodos do Modo Aluno ---
    
    private static void cadastrarAlunoNormal() {
        System.out.println("\n=== Cadastrar Aluno Normal ===");
        String nome = menu.lerString("Nome: ");
        String id = menu.lerString("ID: ");
        String matricula = menu.lerString("Matrícula: ");
        String curso = menu.lerString("Curso: ");
        boolean sucesso = alunoController.cadastrarAluno(nome, id, matricula, curso);
        if (sucesso) {
            System.out.println("Aluno cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar aluno. Matrícula já existe.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void cadastrarAlunoEspecial() {
        System.out.println("\n=== Cadastrar Aluno Especial ===");
        String nome = menu.lerString("Nome: ");
        String id = menu.lerString("ID: ");
        String matricula = menu.lerString("Matrícula: ");
        String curso = menu.lerString("Curso: ");
        boolean sucesso = alunoController.cadastrarAlunoEspecial(nome, id, matricula, curso);
        if (sucesso) {
            System.out.println("Aluno especial cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar aluno especial. Matrícula já existe.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void editarAluno() {
        System.out.println("\n=== Editar Aluno ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno encontrado: " + aluno.getNome());
            String nome = menu.lerString("Novo nome (deixe em branco para manter o atual): ");
            String curso = menu.lerString("Novo curso (deixe em branco para manter o atual): ");
            if (nome.isEmpty()) nome = aluno.getNome();
            if (curso.isEmpty()) curso = aluno.getCurso();
            boolean sucesso = alunoController.editarAluno(matricula, nome, curso);
            if (sucesso) {
                System.out.println("Aluno editado com sucesso!");
            } else {
                System.out.println("Erro ao editar aluno.");
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void listarAlunos() {
        System.out.println("\n=== Lista de Alunos ===");
        List<Aluno> alunos = alunoController.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
            }
            System.out.println("\nTotal de alunos: " + alunos.size());
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void matricularAluno() {
        System.out.println("\n=== Matricular Aluno em Disciplina ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno encontrado: " + aluno.getNome());
            List<Turma> turmas = disciplinaController.listarTurmas();
            if (turmas.isEmpty()) {
                System.out.println("Nenhuma turma disponível para matrícula.");
            } else {
                System.out.println("\nTurmas disponíveis:");
                for (int i = 0; i < turmas.size(); i++) {
                    System.out.println((i + 1) + ". " + turmas.get(i));
                }
                int opcao = menu.lerInteiro("\nEscolha uma turma (0 para cancelar): ");
                if (opcao > 0 && opcao <= turmas.size()) {
                    Turma turma = turmas.get(opcao - 1);
                    Object[] resultado = alunoController.matricularAluno(matricula, turma);
                    boolean sucesso = (boolean) resultado[0];
                    String mensagem = (String) resultado[1];
                    System.out.println(mensagem);
                } else if (opcao != 0) {
                    System.out.println("Opção inválida.");
                }
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void trancarDisciplina() {
        System.out.println("\n=== Trancar Disciplina ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno encontrado: " + aluno.getNome());
            List<Turma> disciplinas = aluno.getDisciplinas();
            if (disciplinas.isEmpty()) {
                System.out.println("Aluno não está matriculado em nenhuma disciplina.");
            } else {
                System.out.println("\nDisciplinas matriculadas:");
                for (int i = 0; i < disciplinas.size(); i++) {
                    System.out.println((i + 1) + ". " + disciplinas.get(i).getDisciplina().getNome() +
                                      " (" + disciplinas.get(i).getSemestre() + ", " +
                                      disciplinas.get(i).getHorario() + ")");
                }
                int opcao = menu.lerInteiro("\nEscolha uma disciplina para trancar (0 para cancelar): ");
                if (opcao > 0 && opcao <= disciplinas.size()) {
                    Turma turma = disciplinas.get(opcao - 1);
                    Object[] resultado = alunoController.trancarDisciplina(matricula, turma);
                    boolean sucesso = (boolean) resultado[0];
                    String mensagem = (String) resultado[1];
                    System.out.println(mensagem);
                } else if (opcao != 0) {
                    System.out.println("Opção inválida.");
                }
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void trancarSemestre() {
        System.out.println("\n=== Trancar Semestre ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno encontrado: " + aluno.getNome());
            boolean confirmar = menu.lerBooleano("Confirma o trancamento do semestre?");
            if (confirmar) {
                Object[] resultado = alunoController.trancarSemestre(matricula);
                boolean sucesso = (boolean) resultado[0];
                String mensagem = (String) resultado[1];
                System.out.println(mensagem);
            } else {
                System.out.println("Operação cancelada.");
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    // --- Métodos do Modo Disciplina/Turma ---
    
    private static void cadastrarDisciplina() {
        System.out.println("\n=== Cadastrar Disciplina ===");
        String codigo = menu.lerString("Código: ");
        String nome = menu.lerString("Nome: ");
        int cargaHoraria = menu.lerInteiro("Carga horária (em horas): ");
        boolean sucesso = disciplinaController.cadastrarDisciplina(codigo, nome, cargaHoraria);
        if (sucesso) {
            System.out.println("Disciplina cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar disciplina. Código já existe.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    private static void adicionarPreRequisito() {
        System.out.println("\n=== Adicionar Pré-requisito ===");
        String codigoDisciplina = menu.lerString("Código da disciplina: ");
        Disciplina disciplina = disciplinaController.buscarDisciplinaPorCodigo(codigoDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
        } else {
            System.out.println("Disciplina encontrada: " + disciplina.getNome());
            String codigoPreRequisito = menu.lerString("Código do pré-requisito: ");
            Disciplina preRequisito = disciplinaController.buscarDisciplinaPorCodigo(codigoPreRequisito);
            if (preRequisito == null) {
                System.out.println("Disciplina pré-requisito não encontrada.");
            } else if (codigoDisciplina.equals(codigoPreRequisito)) {
                System.out.println("Uma disciplina não pode ser pré-requisito dela mesma.");
            } else {
                boolean sucesso = disciplinaController.adicionarPreRequisito(codigoDisciplina, codigoPreRequisito);
                if (sucesso) {
                    System.out.println("Pré-requisito adicionado com sucesso!");
                } else {
                    System.out.println("Erro ao adicionar pré-requisito (talvez já exista).");
                }
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void criarTurma() {
        System.out.println("\n=== Criar Turma ===");
        String codigoDisciplina = menu.lerString("Código da disciplina: ");
        Disciplina disciplina = disciplinaController.buscarDisciplinaPorCodigo(codigoDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            menu.pressionarEnter("\nPressione Enter para continuar...");
            return;
        }
        String professor = menu.lerString("Nome do professor: ");
        String semestre = menu.lerString("Semestre (ex: 2025.1): ");
        String tipoAvaliacao = menu.lerString("Tipo de avaliação (A ou B): ").toUpperCase();
        if (!tipoAvaliacao.equals("A") && !tipoAvaliacao.equals("B")) {
             System.out.println("Tipo de avaliação inválido. Use A ou B.");
             menu.pressionarEnter("\nPressione Enter para continuar...");
             return;
        }
        boolean remota = menu.lerBooleano("Turma remota?");
        String sala = "";
        if (!remota) {
            sala = menu.lerString("Sala: ");
        }
        String horario = menu.lerString("Horário (ex: Seg 19:00-21:00): ");
        int capacidade = menu.lerInteiro("Capacidade máxima: ");

        boolean sucesso = disciplinaController.criarTurma(codigoDisciplina, professor, semestre, tipoAvaliacao, remota, sala, horario, capacidade);
        if (sucesso) {
            System.out.println("Turma criada com sucesso!");
        } else {
            System.out.println("Erro ao criar turma (verifique se já existe turma com mesmo código/semestre/horário ou tipo de avaliação inválido).");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void listarDisciplinas() {
        System.out.println("\n=== Lista de Disciplinas ===");
        List<Disciplina> disciplinas = disciplinaController.listarDisciplinas();
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            for (Disciplina disciplina : disciplinas) {
                System.out.println(disciplina);
            }
            System.out.println("\nTotal de disciplinas: " + disciplinas.size());
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void listarTurmas() {
        System.out.println("\n=== Lista de Turmas ===");
        List<Turma> turmas = disciplinaController.listarTurmas();
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            for (Turma turma : turmas) {
                System.out.println(turma);
            }
            System.out.println("\nTotal de turmas: " + turmas.size());
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void listarTurmasPorDisciplina() {
        System.out.println("\n=== Listar Turmas por Disciplina ===");
        String codigoDisciplina = menu.lerString("Código da disciplina: ");
        List<Turma> turmas = disciplinaController.listarTurmasPorDisciplina(codigoDisciplina);
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma encontrada para esta disciplina.");
        } else {
            System.out.println("\nTurmas da disciplina " + codigoDisciplina + ":");
            for (Turma turma : turmas) {
                System.out.println(turma);
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void listarTurmasPorProfessor() {
        System.out.println("\n=== Listar Turmas por Professor ===");
        String professor = menu.lerString("Nome do professor: ");
        List<Turma> turmas = disciplinaController.listarTurmasPorProfessor(professor);
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma encontrada para este professor.");
        } else {
            System.out.println("\nTurmas do professor " + professor + ":");
            for (Turma turma : turmas) {
                System.out.println(turma);
            }
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    // --- Métodos do Modo Avaliação/Frequência ---

    private static void lancarNota() {
        System.out.println("\n=== Lançar Nota ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        String codigoDisciplina = menu.lerString("Código da disciplina da turma: ");
        String semestre = menu.lerString("Semestre da turma: ");
        String horario = menu.lerString("Horário da turma: ");
        String tipoNota = menu.lerString("Tipo de nota (P1, P2, P3, L, S): ").toUpperCase();
        // Validar tipo de nota
        if (!List.of("P1", "P2", "P3", "L", "S").contains(tipoNota)) {
             System.out.println("Tipo de nota inválido. Use P1, P2, P3, L ou S.");
             menu.pressionarEnter("\nPressione Enter para continuar...");
             return;
        }
        double valor = menu.lerDouble("Valor da nota: ");

        boolean sucesso = avaliacaoController.lancarNota(matricula, codigoDisciplina, semestre, horario, tipoNota, valor);
        if (sucesso) {
            System.out.println("Nota lançada com sucesso!");
        } else {
            System.out.println("Erro ao lançar nota (verifique aluno/turma, se aluno é especial ou se nota já foi lançada).");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void lancarFrequencia() {
        System.out.println("\n=== Lançar Frequência ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        String codigoDisciplina = menu.lerString("Código da disciplina da turma: ");
        String semestre = menu.lerString("Semestre da turma: ");
        String horario = menu.lerString("Horário da turma: ");
        int aula = menu.lerInteiro("Número da aula (1 a N): ");
        boolean presente = menu.lerBooleano("Aluno presente?");

        boolean sucesso = avaliacaoController.lancarFrequencia(matricula, codigoDisciplina, semestre, horario, aula, presente);
        if (sucesso) {
            System.out.println("Frequência lançada com sucesso!");
        } else {
            System.out.println("Erro ao lançar frequência (verifique aluno/turma ou se frequência já foi lançada para esta aula).");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void calcularMediaFinal() {
        System.out.println("\n=== Calcular Média Final ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        String codigoDisciplina = menu.lerString("Código da disciplina da turma: ");
        String semestre = menu.lerString("Semestre da turma: ");
        String horario = menu.lerString("Horário da turma: ");

        double media = avaliacaoController.calcularMediaFinal(matricula, codigoDisciplina, semestre, horario);
        if (media >= 0) {
            System.out.printf("Média final do aluno: %.1f\n", media);
        } else {
            System.out.println("Não foi possível calcular a média (verifique aluno/turma ou se todas as notas foram lançadas).");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void calcularFrequencia() {
        System.out.println("\n=== Calcular Frequência ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        String codigoDisciplina = menu.lerString("Código da disciplina da turma: ");
        String semestre = menu.lerString("Semestre da turma: ");
        String horario = menu.lerString("Horário da turma: ");

        double frequencia = avaliacaoController.calcularFrequencia(matricula, codigoDisciplina, semestre, horario);
        if (frequencia >= 0) {
            System.out.printf("Frequência do aluno: %.1f%%\n", frequencia);
        } else {
            System.out.println("Não foi possível calcular a frequência (verifique aluno/turma).");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void verificarAprovacao() {
        System.out.println("\n=== Verificar Aprovação ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        String codigoDisciplina = menu.lerString("Código da disciplina da turma: ");
        String semestre = menu.lerString("Semestre da turma: ");
        String horario = menu.lerString("Horário da turma: ");

        String situacao = avaliacaoController.verificarAprovacao(matricula, codigoDisciplina, semestre, horario);
        System.out.println("Situação do aluno na turma: " + situacao);
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void gerarRelatorioPorTurma() {
        System.out.println("\n=== Gerar Relatório por Turma ===");
        String codigoDisciplina = menu.lerString("Código da disciplina da turma: ");
        String semestre = menu.lerString("Semestre da turma: ");
        String horario = menu.lerString("Horário da turma: ");

        String relatorio = avaliacaoController.gerarRelatorioPorTurma(codigoDisciplina, semestre, horario);
        if (relatorio != null) {
            System.out.println("\n" + relatorio);
        } else {
            System.out.println("Turma não encontrada.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void gerarRelatorioPorDisciplina() {
        System.out.println("\n=== Gerar Relatório por Disciplina ===");
        String codigoDisciplina = menu.lerString("Código da disciplina: ");

        String relatorio = avaliacaoController.gerarRelatorioPorDisciplina(codigoDisciplina);
        if (relatorio != null) {
            System.out.println("\n" + relatorio);
        } else {
            System.out.println("Disciplina não encontrada.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void gerarRelatorioPorProfessor() {
        System.out.println("\n=== Gerar Relatório por Professor ===");
        String professor = menu.lerString("Nome do professor: ");

        String relatorio = avaliacaoController.gerarRelatorioPorProfessor(professor);
        if (relatorio != null && !relatorio.contains("Turmas: 0")) { // Verifica se há turmas
            System.out.println("\n" + relatorio);
        } else {
            System.out.println("Nenhuma turma encontrada para este professor.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }

    private static void gerarBoletimAluno() {
        System.out.println("\n=== Gerar Boletim do Aluno ===");
        String matricula = menu.lerString("Matrícula do aluno: ");
        String semestre = menu.lerString("Semestre (deixe em branco para todos): ");
        boolean incluirDadosTurma = menu.lerBooleano("Incluir dados da turma (professor, modalidade)?");

        String boletim = avaliacaoController.gerarBoletimAluno(matricula, semestre.isEmpty() ? null : semestre, incluirDadosTurma);
        if (boletim != null) {
            System.out.println("\n" + boletim);
        } else {
            System.out.println("Aluno não encontrado.");
        }
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
}

