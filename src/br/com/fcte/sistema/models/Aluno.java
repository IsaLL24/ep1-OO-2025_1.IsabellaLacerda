package src.br.com.fcte.sistema.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


 // Classe que representa um aluno no sistema acadêmico.
 
public class Aluno extends Pessoa {
    private String matricula;
    private String curso;
    private List<Turma> disciplinas;
    private Map<String, Map<String, Object>> historico;
    private String status;
    
    //Inicializa uma nova instância da classe Aluno.
    // matricula Número de matrícula do aluno
    //curso Curso de graduação do aluno
     
    public Aluno(String nome, String id, String matricula, String curso) {
        super(nome, id);
        this.matricula = matricula;
        this.curso = curso;
        this.disciplinas = new ArrayList<>();
        this.historico = new HashMap<>();
        this.status = "ativo";
    }
    
    // Retorna o número de matrícula do aluno
    public String getMatricula() {
        return matricula;
    }
    
    //Define o número de matrícula do aluno.
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    // Retorna o curso de graduação do aluno.
  
    public String getCurso() {
        return curso;
    }
    
    // Define o curso de graduação do aluno.
  
    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    // 
    //return Lista de turmas em que o aluno está matriculado
     
    public List<Turma> getDisciplinas() {
        return disciplinas;
    }
    
    // Retorna o histórico acadêmico do aluno.
    
    public Map<String, Map<String, Object>> getHistorico() {
        return historico;
    }
    
    // Retorna o status do aluno.
     
    public String getStatus() {
        return status;
    }
    
    // Define o status do aluno.
     
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Matricula o aluno em uma turma.
     
    public Object[] matricular(Turma turma) {
        // Verificar se a turma tem vagas disponíveis
        if (turma.getAlunos().size() >= turma.getCapacidade()) {
            return new Object[] {false, "Turma sem vagas disponíveis"};
        }
        
        // Verificar se o aluno já está matriculado na turma
        if (disciplinas.contains(turma)) {
            return new Object[] {false, "Aluno já matriculado nesta turma"};
        }
        
        // Verificar se o aluno possui os pré-requisitos
        if (!verificarPreRequisitos(turma.getDisciplina())) {
            return new Object[] {false, "Aluno não possui os pré-requisitos necessários"};
        }
        
        // Adicionar a turma à lista de disciplinas do aluno
        disciplinas.add(turma);
        
        // Adicionar o aluno à lista de alunos da turma
        turma.adicionarAluno(this);
        
        return new Object[] {true, "Matrícula realizada com sucesso"};
    }
    
    //Tranca a matrícula do aluno em uma turma.
     
    public Object[] trancarDisciplina(Turma turma) {
        if (disciplinas.contains(turma)) {
            disciplinas.remove(turma);
            turma.removerAluno(this);
            return new Object[] {true, "Disciplina trancada com sucesso"};
        }
        return new Object[] {false, "Aluno não está matriculado nesta turma"};
    }
    
     // Tranca o semestre do aluno.
     // return Um array contendo um booleano indicando sucesso ou falha e uma mensagem
     
    public Object[] trancarSemestre() {
        // Trancar todas as disciplinas
        List<Turma> disciplinasTemp = new ArrayList<>(disciplinas);
        for (Turma turma : disciplinasTemp) {
            trancarDisciplina(turma);
        }
        
        // Atualizar o status do aluno
        status = "trancado";
        
        return new Object[] {true, "Semestre trancado com sucesso"};
    }
    
    // Verifica se o aluno possui os pré-requisitos para cursar uma disciplina.
    // disciplina A disciplina a ser verificada
    //return true se o aluno possui os pré-requisitos, false caso contrário
    
    public boolean verificarPreRequisitos(Disciplina disciplina) {
        // Verificar se a disciplina tem pré-requisitos
        if (disciplina.getPreRequisitos().isEmpty()) {
            return true;
        }
        
        // Verificar se o aluno já cursou todos os pré-requisitos
        for (Disciplina preReq : disciplina.getPreRequisitos()) {
            if (!historico.containsKey(preReq.getCodigo())) {
                return false;
            }
            
            // Verificar se o aluno foi aprovado no pré-requisito
            if (!"aprovado".equals(historico.get(preReq.getCodigo()).get("situacao"))) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Matrícula: " + matricula + ", Curso: " + curso + ", Status: " + status;
    }
}
