package src.br.com.fcte.sistema.models;

// Classe que representa um aluno especial no sistema acadêmico.
// Alunos especiais podem cursar no máximo 2 disciplinas e não recebem notas, apenas presença.
 
public class AlunoEspecial extends Aluno {
    
    // Inicializa uma nova instância da classe AlunoEspecial.
     
    public AlunoEspecial(String nome, String id, String matricula, String curso) {
        super(nome, id, matricula, curso);
    }
    
    // Matricula o aluno especial em uma turma, com limite de 2 disciplinas.
     
    @Override
    public Object[] matricular(Turma turma) {
        // Verificar se o aluno já atingiu o limite de disciplinas
        if (getDisciplinas().size() >= 2) {
            return new Object[] {false, "Alunos especiais podem cursar no máximo 2 disciplinas"};
        }
        
        // Chamar o método da classe pai
        return super.matricular(turma);
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Aluno Especial)";
    }
}

