package src.br.com.fcte.sistema;

import java.util.List;
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
    private int idade;
    
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
//                    criarTurma();
                    break;
                case 4:
//                    listarDisciplinas();
                    break;
                case 5:
//                    listarTurmas();
                    break;
                case 6:
//                    listarTurmasPorDisciplina();
                    break;
                case 7:
//                    listarTurmasPorProfessor();
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
//                    lancarNota();
                    break;
                case 2:
//                    lancarFrequencia();
                    break;
                case 3:
//                    calcularMediaFinal();
                    break;
                case 4:
//                    calcularFrequencia();
                    break;
                case 5:
//                    verificarAprovacao();
                    break;
                case 6:
//                    gerarRelatorioPorTurma();
                    break;
                case 7:
//                    gerarRelatorioPorDisciplina();
                    break;
                case 8:
//                    gerarRelatorioPorProfessor();
                    break;
                case 9:
//                    gerarBoletimAluno();
                    break;
                case 0:
                    voltar = true;
                    break;
            }
        }
    }
    
    // Métodos do modo aluno
    
    // Cadastra um aluno normal
    
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
    
    // Cadastra um aluno especial.
     
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
    
    // Edita um aluno existente.
     
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
            
            if (nome.isEmpty()) {
                nome = aluno.getNome();
            }
            
            if (curso.isEmpty()) {
                curso = aluno.getCurso();
            }
            
            boolean sucesso = alunoController.editarAluno(matricula, nome, curso);
            
            if (sucesso) {
                System.out.println("Aluno editado com sucesso!");
            } else {
                System.out.println("Erro ao editar aluno.");
            }
        }
        
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    // Lista todos os alunos cadastrados.
     
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
    
    // Matricula um aluno em uma disciplina.
     
    private static void matricularAluno() {
        System.out.println("\n=== Matricular Aluno em Disciplina ===");
        
        String matricula = menu.lerString("Matrícula do aluno: ");
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno encontrado: " + aluno.getNome());
            
            // Listar turmas disponíveis
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
                    
                    if (sucesso) {
                        System.out.println("Matrícula realizada com sucesso!");
                    } else {
                        System.out.println("Erro ao matricular aluno: " + mensagem);
                    }
                } else if (opcao != 0) {
                    System.out.println("Opção inválida.");
                }
            }
        }
        
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    //Tranca a matrícula de um aluno em uma disciplina.
     
    private static void trancarDisciplina() {
        System.out.println("\n=== Trancar Disciplina ===");
        
        String matricula = menu.lerString("Matrícula do aluno: ");
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno encontrado: " + aluno.getNome());
            
            // Listar disciplinas do aluno
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
                    
                    if (sucesso) {
                        System.out.println("Disciplina trancada com sucesso!");
                    } else {
                        System.out.println("Erro ao trancar disciplina: " + mensagem);
                    }
                } else if (opcao != 0) {
                    System.out.println("Opção inválida.");
                }
            }
        }
        
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    //Tranca o semestre de um aluno.
     
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
                
                if (sucesso) {
                    System.out.println("Semestre trancado com sucesso!");
                } else {
                    System.out.println("Erro ao trancar semestre: " + mensagem);
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        }
        
        menu.pressionarEnter("\nPressione Enter para continuar...");
    }
    
    // Métodos do modo disciplina/turma
    
    // Cadastra uma nova disciplina.
     
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
    
    // Adiciona um pré-requisito a uma disciplina.
     
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
                    System.out.println("Content truncated due to size limit. Use line ranges to read in chunks");
                }
            }
        }
    }
}
