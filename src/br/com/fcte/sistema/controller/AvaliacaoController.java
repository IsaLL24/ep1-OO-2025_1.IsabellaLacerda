package br.com.fcte.sistema.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fcte.sistema.models.Aluno;
import br.com.fcte.sistema.models.AlunoEspecial;
import br.com.fcte.sistema.models.Turma;
import br.com.fcte.sistema.persistence.FileManager;

//Controlador para gerenciar operações relacionadas às avaliações e frequências.
 
public class AvaliacaoController {
    private AlunoController alunoController;
    private DisciplinaController disciplinaController;
    private FileManager fileManager;
    
    // Inicializa uma nova instância do controlador de avaliações.
     
    public AvaliacaoController(AlunoController alunoController, DisciplinaController disciplinaController) {
        this.alunoController = alunoController;
        this.disciplinaController = disciplinaController;
        this.fileManager = new FileManager();
    }
    
    // Lança uma nota para um aluno em uma turma.
    
    public boolean lancarNota(String matricula, String codigoDisciplina, String semestre, 
                             String horario, String tipoNota, double valor) {
        // Buscar o aluno e a turma
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        Turma turma = disciplinaController.buscarTurma(codigoDisciplina, semestre, horario);
        
        if (aluno == null || turma == null) {
            return false;
        }
        
        // Verificar se o aluno é especial (não recebe notas)
        if (aluno instanceof AlunoEspecial) {
            return false;
        }
        
        // Lançar a nota
        return turma.lancarNota(aluno, tipoNota, valor);
    }
    
    // Lança a frequência para um aluno em uma turma.
     
    public boolean lancarFrequencia(String matricula, String codigoDisciplina, String semestre, 
                                   String horario, int aula, boolean presente) {
        // Buscar o aluno e a turma
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        Turma turma = disciplinaController.buscarTurma(codigoDisciplina, semestre, horario);
        
        if (aluno == null || turma == null) {
            return false;
        }
        
        // Lançar a frequência
        return turma.lancarFrequencia(aluno, aula, presente);
    }
    
    // Calcula a média final de um aluno em uma turma.
     
    public double calcularMediaFinal(String matricula, String codigoDisciplina, String semestre, String horario) {
        // Buscar o aluno e a turma
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        Turma turma = disciplinaController.buscarTurma(codigoDisciplina, semestre, horario);
        
        if (aluno == null || turma == null) {
            return -1;
        }
        
        // Calcular a média
        return turma.calcularMediaFinal(aluno);
    }
    
    // Calcula a frequência de um aluno em uma turma.

    public double calcularFrequencia(String matricula, String codigoDisciplina, String semestre, String horario) {
        // Buscar o aluno e a turma
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        Turma turma = disciplinaController.buscarTurma(codigoDisciplina, semestre, horario);
        
        if (aluno == null || turma == null) {
            return -1;
        }
        
        // Calcular a frequência
        return turma.calcularFrequencia(aluno);
    }
    
    //Verifica se o aluno foi aprovado em uma turma.
    
    public String verificarAprovacao(String matricula, String codigoDisciplina, String semestre, String horario) {
        // Buscar o aluno e a turma
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        Turma turma = disciplinaController.buscarTurma(codigoDisciplina, semestre, horario);
        
        if (aluno == null || turma == null) {
            return "Aluno ou turma não encontrados";
        }
        
        // Verificar aprovação
        return turma.verificarAprovacao(aluno);
    }
    
    // Gera um relatório por turma.
     
    public String gerarRelatorioPorTurma(String codigoDisciplina, String semestre, String horario) {
        Turma turma = disciplinaController.buscarTurma(codigoDisciplina, semestre, horario);
        if (turma == null) {
            return null;
        }
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DA TURMA\n");
        relatorio.append("=================\n\n");
        relatorio.append(turma.toString()).append("\n\n");
        relatorio.append("Alunos matriculados: ").append(turma.getAlunos().size()).append("\n\n");
        
        for (Aluno aluno : turma.getAlunos()) {
            relatorio.append("- ").append(aluno.getNome())
                    .append(" (").append(aluno.getMatricula()).append("): ");
            
            if (aluno instanceof AlunoEspecial) {
                double freq = turma.calcularFrequencia(aluno);
                relatorio.append("Aluno Especial, Frequência: ")
                        .append(freq >= 0 ? String.format("%.1f%%", freq) : "N/A")
                        .append(", Situação: ").append(turma.verificarAprovacao(aluno));
            } else {
                double media = turma.calcularMediaFinal(aluno);
                double freq = turma.calcularFrequencia(aluno);
                relatorio.append("Média: ").append(media >= 0 ? String.format("%.1f", media) : "N/A")
                        .append(", Frequência: ").append(freq >= 0 ? String.format("%.1f%%", freq) : "N/A")
                        .append(", Situação: ").append(turma.verificarAprovacao(aluno));
            }
            
            relatorio.append("\n");
        }
        
        return relatorio.toString();
    }
    
    // Gera um relatório por disciplina.
    
    public String gerarRelatorioPorDisciplina(String codigoDisciplina) {
        // Buscar a disciplina
        if (disciplinaController.buscarDisciplinaPorCodigo(codigoDisciplina) == null) {
            return null;
        }
        
        // Buscar as turmas da disciplina
        List<Turma> turmasDisciplina = disciplinaController.listarTurmasPorDisciplina(codigoDisciplina);
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DA DISCIPLINA\n");
        relatorio.append("=====================\n\n");
        relatorio.append("Código: ").append(codigoDisciplina).append("\n");
        relatorio.append("Nome: ").append(disciplinaController.buscarDisciplinaPorCodigo(codigoDisciplina).getNome()).append("\n");
        relatorio.append("Carga Horária: ").append(disciplinaController.buscarDisciplinaPorCodigo(codigoDisciplina).getCargaHoraria()).append("h\n\n");
        relatorio.append("Turmas: ").append(turmasDisciplina.size()).append("\n\n");
        
        for (Turma turma : turmasDisciplina) {
            relatorio.append("- Semestre: ").append(turma.getSemestre())
                    .append(", Professor: ").append(turma.getProfessor())
                    .append(", Horário: ").append(turma.getHorario())
                    .append(", Modalidade: ").append(turma.isRemota() ? "Remota" : "Presencial")
                    .append(", Alunos: ").append(turma.getAlunos().size())
                    .append("/").append(turma.getCapacidade())
                    .append("\n");
        }
        
        return relatorio.toString();
    }
    
    // Gera um relatório por professor.
     
    public String gerarRelatorioPorProfessor(String professor) {
        // Buscar as turmas do professor
        List<Turma> turmasProfessor = disciplinaController.listarTurmasPorProfessor(professor);
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DO PROFESSOR\n");
        relatorio.append("=====================\n\n");
        relatorio.append("Professor: ").append(professor).append("\n\n");
        relatorio.append("Turmas: ").append(turmasProfessor.size()).append("\n\n");
        
        for (Turma turma : turmasProfessor) {
            relatorio.append("- Disciplina: ").append(turma.getDisciplina().getNome())
                    .append(" (").append(turma.getDisciplina().getCodigo()).append(")")
                    .append(", Semestre: ").append(turma.getSemestre())
                    .append(", Horário: ").append(turma.getHorario())
                    .append(", Modalidade: ").append(turma.isRemota() ? "Remota" : "Presencial")
                    .append(", Alunos: ").append(turma.getAlunos().size())
                    .append("/").append(turma.getCapacidade())
                    .append("\n");
        }
        
        return relatorio.toString();
    }
    
    // Gera o boletim de um aluno.
     
    public String gerarBoletimAluno(String matricula, String semestre, boolean incluirDadosTurma) {
        // Buscar o aluno
        Aluno aluno = alunoController.buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            return null;
        }
        
        StringBuilder boletim = new StringBuilder();
        boletim.append("BOLETIM DO ALUNO\n");
        boletim.append("===============\n\n");
        boletim.append("Nome: ").append(aluno.getNome()).append("\n");
        boletim.append("Matrícula: ").append(aluno.getMatricula()).append("\n");
        boletim.append("Curso: ").append(aluno.getCurso()).append("\n");
        boletim.append("Status: ").append(aluno.getStatus()).append("\n\n");
        
        // Organizar disciplinas por semestre
        Map<String, List<Turma>> disciplinasPorSemestre = new HashMap<>();
        
        for (Turma turma : aluno.getDisciplinas()) {
            if (semestre == null || turma.getSemestre().equals(semestre)) {
                if (!disciplinasPorSemestre.containsKey(turma.getSemestre())) {
                    disciplinasPorSemestre.put(turma.getSemestre(), new ArrayList<>());
                }
                disciplinasPorSemestre.get(turma.getSemestre()).add(turma);
            }
        }
        
        // Gerar boletim por semestre
        for (String sem : disciplinasPorSemestre.keySet()) {
            boletim.append("Semestre: ").append(sem).append("\n");
            boletim.append("------------------\n\n");
            
            for (Turma turma : disciplinasPorSemestre.get(sem)) {
                boletim.append("- Disciplina: ").append(turma.getDisciplina().getNome())
                       .append(" (").append(turma.getDisciplina().getCodigo()).append(")");
                
                if (incluirDadosTurma) {
                    boletim.append("\n  Professor: ").append(turma.getProfessor())
                           .append("\n  Modalidade: ").append(turma.isRemota() ? "Remota" : "Presencial")
                           .append("\n  Carga Horária: ").append(turma.getDisciplina().getCargaHoraria()).append("h");
                }
                
                if (aluno instanceof AlunoEspecial) {
                    double freq = turma.calcularFrequencia(aluno);
                    boletim.append("\n  Frequência: ").append(freq >= 0 ? String.format("%.1f%%", freq) : "N/A")
                           .append("\n  Situação: ").append(turma.verificarAprovacao(aluno));
                } else {
                    double media = turma.calcularMediaFinal(aluno);
                    double freq = turma.calcularFrequencia(aluno);
                    boletim.append("\n  Média: ").append(media >= 0 ? String.format("%.1f", media) : "N/A")
                           .append("\n  Frequência: ").append(freq >= 0 ? String.format("%.1f%%", freq) : "N/A")
                           .append("\n  Situação: ").append(turma.verificarAprovacao(aluno));
                }
                
                boletim.append("\n\n");
            }
        }
        
        return boletim.toString();
    }
}

