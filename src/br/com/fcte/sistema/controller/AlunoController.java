package br.com.fcte.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.fcte.sistema.models.Aluno;
import br.com.fcte.sistema.models.AlunoEspecial;
import br.com.fcte.sistema.models.Turma;
import br.com.fcte.sistema.utils.FileManager;

// Controlador para gerenciar operações relacionadas aos alunos.
 
public class AlunoController {
    private List<Aluno> alunos;
    private FileManager fileManager;
    
    // Inicializa uma nova instância do controlador de alunos.
     
    public AlunoController() {
        this.alunos = new ArrayList<>();
        this.fileManager = new FileManager();
        carregarAlunos();
    }
    
    // Cadastra um novo aluno normal.
     
    //return true se o cadastro foi bem-sucedido, false caso contrário
     
    public boolean cadastrarAluno(String nome, String id, String matricula, String curso) {
        // Verificar se já existe um aluno com a mesma matrícula
        if (buscarAlunoPorMatricula(matricula) != null) {
            return false;
        }
        
        // Criar e adicionar o aluno
        Aluno aluno = new Aluno(nome, id, matricula, curso);
        alunos.add(aluno);
        salvarAlunos();
        return true;
    }
    
    // Cadastra um novo aluno especial.
    //return true se o cadastro foi bem-sucedido, false caso contrário
     
    public boolean cadastrarAlunoEspecial(String nome, String id, String matricula, String curso) {
        // Verificar se já existe um aluno com a mesma matrícula
        if (buscarAlunoPorMatricula(matricula) != null) {
            return false;
        }
        
        // Criar e adicionar o aluno especial
        AlunoEspecial aluno = new AlunoEspecial(nome, id, matricula, curso);
        alunos.add(aluno);
        salvarAlunos();
        return true;
    }
    
    // Edita um aluno existente.
     
    public boolean editarAluno(String matricula, String nome, String curso) {
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            return false;
        }
        
        aluno.setNome(nome);
        aluno.setCurso(curso);
        salvarAlunos();
        return true;
    }
    
    // Lista todos os alunos cadastrados.
    
    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos);
    }
    
    // Matricula um aluno em uma turma.

    //return Um array contendo um booleano indicando sucesso ou falha e uma mensagem
     
    public Object[] matricularAluno(String matricula, Turma turma) {
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            return new Object[] {false, "Aluno não encontrado"};
        }
        
        Object[] resultado = aluno.matricular(turma);
        if ((boolean) resultado[0]) {
            salvarAlunos();
        }
        return resultado;
    }
    
    // Tranca a matrícula de um aluno em uma disciplina.
    //return Um array contendo um booleano indicando sucesso ou falha e uma mensagem
     
    public Object[] trancarDisciplina(String matricula, Turma turma) {
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            return new Object[] {false, "Aluno não encontrado"};
        }
        
        Object[] resultado = aluno.trancarDisciplina(turma);
        if ((boolean) resultado[0]) {
            salvarAlunos();
        }
        return resultado;
    }
    
    // Tranca o semestre de um aluno.
    //return Um array contendo um booleano indicando sucesso ou falha e uma mensagem
     
    public Object[] trancarSemestre(String matricula) {
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            return new Object[] {false, "Aluno não encontrado"};
        }
        
        Object[] resultado = aluno.trancarSemestre();
        if ((boolean) resultado[0]) {
            salvarAlunos();
        }
        return resultado;
    }
    
    // Busca um aluno pelo número de matrícula.
    //return O aluno encontrado ou null se não existir
     
    public Aluno buscarAlunoPorMatricula(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    }
    
    // Salva os dados dos alunos em arquivo.
     
    private void salvarAlunos() {
        fileManager.salvarAlunos(alunos);
    }
    
    // Carrega os dados dos alunos do arquivo.
     
    private void carregarAlunos() {
        List<Aluno> alunosCarregados = fileManager.carregarAlunos();
        if (alunosCarregados != null) {
            this.alunos = alunosCarregados;
        }
    }
}
