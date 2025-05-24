package src.br.com.fcte.sistema.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Classe que representa uma turma no sistema acadêmico.
 
public class Turma {
    private Disciplina disciplina;
    private String professor;
    private String semestre;
    private Avaliacao formaAvaliacao;
    private boolean remota;
    private String sala;
    private String horario;
    private int capacidade;
    private List<Aluno> alunos;
    private Map<String, Map<String, Double>> notas;
    private Map<String, Map<Integer, Boolean>> frequencia;
    
    //Inicializa uma nova instância da classe Turma.
    
    public Turma(Disciplina disciplina, String professor, String semestre, 
                 Avaliacao formaAvaliacao, boolean remota, String sala, 
                 String horario, int capacidade) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.remota = remota;
        this.sala = remota ? null : sala;
        this.horario = horario;
        this.capacidade = capacidade;
        this.alunos = new ArrayList<>();
        this.notas = new HashMap<>();
        this.frequencia = new HashMap<>();
    }
    
    // Retorna a disciplina da turma
     
    public Disciplina getDisciplina() {
        return disciplina;
    }
    
    //Define a disciplina da turma.
     
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    // Retorna o professor responsável pela turma.
     
    public String getProfessor() {
        return professor;
    }
    
     
     
    public void setProfessor(String professor) {
        this.professor = professor;
    }
    
    // Retorna o semestre da turma.
     
    public String getSemestre() {
        return semestre;
    }
    
    // Define o semestre da turma.
     
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
    
    //Retorna a forma de avaliação da turma.
    
    public Avaliacao getFormaAvaliacao() {
        return formaAvaliacao;
    }
    
    // Define a forma de avaliação da turma.
    
    public void setFormaAvaliacao(Avaliacao formaAvaliacao) {
        this.formaAvaliacao = formaAvaliacao;
    }
    
    // Verifica se a turma é remota.
     
    public boolean isRemota() {
        return remota;
    }
    
    //Define se a turma é remota.
     
    public void setRemota(boolean remota) {
        this.remota = remota;
        if (remota) {
            this.sala = null;
        }
    }
    
    // Retorna a sala da turma.
     
    public String getSala() {
        return sala;
    }
    
    // Define a sala da turma.
     
    public void setSala(String sala) {
        if (!remota) {
            this.sala = sala;
        }
    }
    
    // Retorna o horário das aulas.
    
    public String getHorario() {
        return horario;
    }
    
    // Define o horário das aulas.
   
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    // Retorna a capacidade máxima de alunos.
    
    public int getCapacidade() {
        return capacidade;
    }
    
    // Define a capacidade máxima de alunos.
     
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    // Retorna a lista de alunos matriculados.
   
    public List<Aluno> getAlunos() {
        return alunos;
    }
    
    //Adiciona um aluno à turma.
     
    public boolean adicionarAluno(Aluno aluno) {
        if (alunos.size() < capacidade && !alunos.contains(aluno)) {
            alunos.add(aluno);
            notas.put(aluno.getMatricula(), new HashMap<>());
            frequencia.put(aluno.getMatricula(), new HashMap<>());
            return true;
        }
        return false;
    }
    
    // Remove um aluno da turma

    public boolean removerAluno(Aluno aluno) {
        if (alunos.contains(aluno)) {
            alunos.remove(aluno);
            notas.remove(aluno.getMatricula());
            frequencia.remove(aluno.getMatricula());
            return true;
        }
        return false;
    }
    
    // Lança uma nota para um aluno.
    
    public boolean lancarNota(Aluno aluno, String tipo, double valor) {
        // Verificar se o aluno está matriculado na turma
        if (!alunos.contains(aluno)) {
            return false;
        }
        
        // Verificar se o aluno é especial (não recebe notas)
        if (aluno instanceof AlunoEspecial) {
            return false;
        }
        
        // Lançar a nota
        Map<String, Double> notasAluno = notas.get(aluno.getMatricula());
        notasAluno.put(tipo, valor);
        return true;
    }
    
    // Lança a frequência para um aluno.
    
    public boolean lancarFrequencia(Aluno aluno, int aula, boolean presente) {
        // Verificar se o aluno está matriculado na turma
        if (!alunos.contains(aluno)) {
            return false;
        }
        
        // Lançar a frequência
        Map<Integer, Boolean> frequenciaAluno = frequencia.get(aluno.getMatricula());
        frequenciaAluno.put(aula, presente);
        return true;
    }
    
    //Calcula a média final de um aluno.
   
    public double calcularMediaFinal(Aluno aluno) {
        // Verificar se o aluno está matriculado na turma
        if (!alunos.contains(aluno)) {
            return -1;
        }
        
        // Verificar se o aluno é especial (não recebe notas)
        if (aluno instanceof AlunoEspecial) {
            return -1;
        }
        
        // Calcular a média
        return formaAvaliacao.calcularMedia(notas.get(aluno.getMatricula()));
    }
    
    // Calcula a frequência de um aluno.

    public double calcularFrequencia(Aluno aluno) {
        // Verificar se o aluno está matriculado na turma
        if (!alunos.contains(aluno)) {
            return -1;
        }
        
        // Calcular a frequência
        Map<Integer, Boolean> frequenciaAluno = frequencia.get(aluno.getMatricula());
        if (frequenciaAluno.isEmpty()) {
            return -1;
        }
        
        int totalAulas = frequenciaAluno.size();
        int presencas = 0;
        
        for (boolean presente : frequenciaAluno.values()) {
            if (presente) {
                presencas++;
            }
        }
        
        return (double) presencas / totalAulas * 100;
    }
    
    // Verifica se o aluno foi aprovado.
   
    public String verificarAprovacao(Aluno aluno) {
        // Verificar se o aluno está matriculado na turma
        if (!alunos.contains(aluno)) {
            return "aluno não matriculado";
        }
        
        // Verificar se o aluno é especial (não recebe notas)
        if (aluno instanceof AlunoEspecial) {
            double frequencia = calcularFrequencia(aluno);
            return frequencia >= 75 ? "aprovado" : "reprovado por falta";
        }
        
        // Calcular média e frequência
        double media = calcularMediaFinal(aluno);
        double frequencia = calcularFrequencia(aluno);
        
        // Verificar aprovação
        if (frequencia < 75) {
            return "reprovado por falta";
        } else if (media < 5) {
            return "reprovado por nota";
        } else {
            return "aprovado";
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Disciplina: ").append(disciplina.getNome())
          .append(" (").append(disciplina.getCodigo()).append(")")
          .append(", Professor: ").append(professor)
          .append(", Semestre: ").append(semestre)
          .append(", Avaliação: ").append(formaAvaliacao.getTipo())
          .append(", Modalidade: ").append(remota ? "Remota" : "Presencial");
        
        if (!remota) {
            sb.append(", Sala: ").append(sala);
        }
        
        sb.append(", Horário: ").append(horario)
          .append(", Capacidade: ").append(capacidade)
          .append(", Alunos matriculados: ").append(alunos.size());
        
        return sb.toString();
    }
}

