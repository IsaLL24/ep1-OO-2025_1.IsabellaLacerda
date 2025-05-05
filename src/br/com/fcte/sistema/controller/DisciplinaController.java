package br.com.fcte.sistema.controller;

import br.com.fcte.sistema.models.Disciplina;
import br.com.fcte.sistema.models.Turma;
import br.com.fcte.sistema.utils.FileManager;
import br.com.fcte.sistema.models.Avaliacao;
import br.com.fcte.sistema.models.AvaliacaoA;
import br.com.fcte.sistema.models.AvaliacaoB;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gerenciar operações relacionadas às disciplinas e turmas.
 */
public class DisciplinaController {
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;
    private FileManager fileManager;
    
    /**
     * Inicializa uma nova instância do controlador de disciplinas.
     */
    public DisciplinaController() {
        this.disciplinas = new ArrayList<>();
        this.turmas = new ArrayList<>();
        this.fileManager = new FileManager();
        carregarDisciplinas();
        carregarTurmas();
    }
    
    /**
     * Cadastra uma nova disciplina.
     * 
     * @param codigo Código da disciplina
     * @param nome Nome da disciplina
     * @param cargaHoraria Carga horária em horas
     * @return true se o cadastro foi bem-sucedido, false caso contrário
     */
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
    
    /**
     * Adiciona um pré-requisito a uma disciplina.
     * 
     * @param codigoDisciplina Código da disciplina
     * @param codigoPreRequisito Código do pré-requisito
     * @return true se a adição foi bem-sucedida, false caso contrário
     */
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
    
    /**
     * Cria uma nova turma.
     * 
     * @param codigoDisciplina Código da disciplina
     * @param professor Professor responsável
     * @param semestre Semestre (ex: "2025.1")
     * @param tipoAvaliacao Tipo de avaliação ("A" ou "B")
     * @param remota Se é remota ou presencial
     * @param sala Sala (null se for remota)
     * @param horario Horário das aulas
     * @param capacidade Capacidade máxima de alunos
     * @return true se a criação foi bem-sucedida, false caso contrário
     */
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
    
    /**
     * Lista todas as disciplinas cadastradas.
     * 
     * @return Lista de disciplinas cadastradas
     */
    public List<Disciplina> listarDisciplinas() {
        return new ArrayList<>(disciplinas);
    }
    
    /**
     * Lista todas as turmas disponíveis.
     * 
     * @return Lista de turmas disponíveis
     */
    public List<Turma> listarTurmas() {
        return new ArrayList<>(turmas);
    }
    
    /**
     * Lista as turmas de uma disciplina específica.
     * 
     * @param codigoDisciplina Código da disciplina
     * @return Lista de turmas da disciplina
     */
    public List<Turma> listarTurmasPorDisciplina(String codigoDisciplina) {
        List<Turma> turmasDisciplina = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getDisciplina().getCodigo().equals(codigoDisciplina)) {
                turmasDisciplina.add(turma);
            }
        }
        return turmasDisciplina;
    }
    
    /**
     * Lista as turmas de um professor específico.
     * 
     * @param professor Nome do professor
     * @return Lista de turmas do professor
     */
    public List<Turma> listarTurmasPorProfessor(String professor) {
        List<Turma> turmasProfessor = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getProfessor().equals(professor)) {
                turmasProfessor.add(turma);
            }
        }
        return turmasProfessor;
    }
    
    /**
     * Busca uma disciplina pelo código.
     * 
     * @param codigo Código da disciplina
     * @return A disciplina encontrada ou null se não existir
     */
    public Disciplina buscarDisciplinaPorCodigo(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equals(codigo)) {
                return disciplina;
            }
        }
        return null;
    }
    
    /**
     * Busca uma turma pelo código da disciplina, semestre e horário.
     * 
     * @param codigoDisciplina Código da disciplina
     * @param semestre Semestre da turma
     * @param horario Horário da turma
     * @return A turma encontrada ou null se não existir
     */
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
    
    /**
     * Salva os dados das disciplinas em arquivo.
     */
    private void salvarDisciplinas() {
        fileManager.salvarDisciplinas(disciplinas);
    }
    
    /**
     * Carrega os dados das disciplinas do arquivo.
     */
    private void carregarDisciplinas() {
        List<Disciplina> disciplinasCarregadas = fileManager.carregarDisciplinas();
        if (disciplinasCarregadas != null) {
            this.disciplinas = disciplinasCarregadas;
        }
    }
    
    /**
     * Salva os dados das turmas em arquivo.
     */
    private void salvarTurmas() {
        fileManager.salvarTurmas(turmas);
    }
    
    /**
     * Carrega os dados das turmas do arquivo.
     */
    private void carregarTurmas() {
        List<Turma> turmasCarregadas = fileManager.carregarTurmas();
        if (turmasCarregadas != null) {
            this.turmas = turmasCarregadas;
        }
    }
}