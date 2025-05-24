package src.br.com.fcte.sistema.controller;

import src.br.com.fcte.sistema.models.Disciplina;
import src.br.com.fcte.sistema.models.Turma;
import src.br.com.fcte.sistema.utils.FileManager;
import src.br.com.fcte.sistema.models.Avaliacao;
import src.br.com.fcte.sistema.models.AvaliacaoA;
import src.br.com.fcte.sistema.models.AvaliacaoB;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gerenciar operações relacionadas às disciplinas e turmas.
 */
public class DisciplinaController {
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;
    private FileManager fileManager;
    
    // Inicializa uma nova instância do controlador de disciplinas.
     
    public DisciplinaController() {
        this.disciplinas = new ArrayList<>();
        this.turmas = new ArrayList<>();
        this.fileManager = new FileManager();
        carregarDisciplinas();
        carregarTurmas();
    }
    
    // Cadastra uma nova disciplina.
     
    public boolean cadastrarDisciplina(String codigo, String nome, int cargaHoraria) {
        // Verificar se já existe uma disciplina com o mesmo código
        if (buscarDisciplinaPorCodigo(codigo) != null) {
            return false;
        }
        
        // Criar e adicionar a disciplina
        Disciplina disciplina = new Disciplina(codigo, nome, cargaHoraria);
        disciplinas.add(disciplina);
        salvarDisciplinas();
        return true;
    }
    
    // Adiciona um pré-requisito a uma disciplina.
     
    public boolean adicionarPreRequisito(String codigoDisciplina, String codigoPreRequisito) {
        Disciplina disciplina = buscarDisciplinaPorCodigo(codigoDisciplina);
        Disciplina preRequisito = buscarDisciplinaPorCodigo(codigoPreRequisito);
        
        if (disciplina == null || preRequisito == null) {
            return false;
        }
        
        disciplina.adicionarPreRequisito(preRequisito);
        salvarDisciplinas();
        return true;
    }
    
    // Cria uma nova turma.
     
    public boolean criarTurma(String codigoDisciplina, String professor, String semestre,
                             String tipoAvaliacao, boolean remota, String sala,
                             String horario, int capacidade) {
        Disciplina disciplina = buscarDisciplinaPorCodigo(codigoDisciplina);
        if (disciplina == null) {
            return false;
        }
        
        // Verificar se já existe uma turma para esta disciplina no mesmo horário
        for (Turma turma : turmas) {
            if (turma.getDisciplina().getCodigo().equals(codigoDisciplina) && 
                turma.getHorario().equals(horario) &&
                turma.getSemestre().equals(semestre)) {
                return false;
            }
        }
        
        // Criar a forma de avaliação
        Avaliacao avaliacao;
        if ("A".equals(tipoAvaliacao)) {
            avaliacao = new AvaliacaoA();
        } else if ("B".equals(tipoAvaliacao)) {
            avaliacao = new AvaliacaoB();
        } else {
            return false;
        }
        
        // Criar e adicionar a turma
        Turma turma = new Turma(disciplina, professor, semestre, avaliacao, remota, sala, horario, capacidade);
        turmas.add(turma);
        salvarTurmas();
        return true;
    }
    
    // Lista todas as disciplinas cadastradas.
     
    public List<Disciplina> listarDisciplinas() {
        return new ArrayList<>(disciplinas);
    }
    
    // Lista todas as turmas disponíveis.
    
    public List<Turma> listarTurmas() {
        return new ArrayList<>(turmas);
    }
    
    // Lista as turmas de uma disciplina específica.
     
    public List<Turma> listarTurmasPorDisciplina(String codigoDisciplina) {
        List<Turma> turmasDisciplina = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getDisciplina().getCodigo().equals(codigoDisciplina)) {
                turmasDisciplina.add(turma);
            }
        }
        return turmasDisciplina;
    }
    
    // Lista as turmas de um professor específico.
     
    public List<Turma> listarTurmasPorProfessor(String professor) {
        List<Turma> turmasProfessor = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getProfessor().equals(professor)) {
                turmasProfessor.add(turma);
            }
        }
        return turmasProfessor;
    }
    
    // Busca uma disciplina pelo código.
    
    public Disciplina buscarDisciplinaPorCodigo(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equals(codigo)) {
                return disciplina;
            }
        }
        return null;
    }
    
    // Busca uma turma pelo código da disciplina, semestre e horário.
     
    public Turma buscarTurma(String codigoDisciplina, String semestre, String horario) {
        for (Turma turma : turmas) {
            if (turma.getDisciplina().getCodigo().equals(codigoDisciplina) &&
                turma.getSemestre().equals(semestre) &&
                turma.getHorario().equals(horario)) {
                return turma;
            }
        }
        return null;
    }
    
    // Salva os dados das disciplinas em arquivo.
     
    private void salvarDisciplinas() {
        fileManager.salvarDisciplinas(disciplinas);
    }
    
    // Carrega os dados das disciplinas do arquivo.
     
    private void carregarDisciplinas() {
        List<Disciplina> disciplinasCarregadas = fileManager.carregarDisciplinas();
        if (disciplinasCarregadas != null) {
            this.disciplinas = disciplinasCarregadas;
        }
    }
    
    // Salva os dados das turmas em arquivo.
     
    private void salvarTurmas() {
        fileManager.salvarTurmas(turmas);
    }
    
    // Carrega os dados das turmas do arquivo.
     
    private void carregarTurmas() {
        List<Turma> turmasCarregadas = fileManager.carregarTurmas();
        if (turmasCarregadas != null) {
            this.turmas = turmasCarregadas;
        }
    }
}
