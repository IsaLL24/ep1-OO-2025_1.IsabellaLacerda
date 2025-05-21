package src.br.com.fcte.sistema.utils;

import src.br.com.fcte.sistema.models.*;

import java.io.*;
import java.util.*;

// Classe utilitária para gerenciar a persistência em arquivos.
 
public class FileManager {
    private static final String DATA_DIR = "data/";
    private static final String ALUNOS_FILE = DATA_DIR + "alunos.csv";
    private static final String DISCIPLINAS_FILE = DATA_DIR + "disciplinas.csv";
    private static final String TURMAS_FILE = DATA_DIR + "turmas.csv";
    
    // Construtor que garante a existência do diretório de dados.
     
    public FileManager() {
        // Garantir que o diretório de dados existe
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    // Salva a lista de alunos em arquivo.
     
    public void salvarAlunos(List<Aluno> alunos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ALUNOS_FILE))) {
            // Escrever cabeçalho
            writer.println("tipo,nome,id,matricula,curso,status");
            
            // Escrever dados dos alunos
            for (Aluno aluno : alunos) {
                String tipo = (aluno instanceof AlunoEspecial) ? "especial" : "normal";
                writer.println(String.format("%s,%s,%s,%s,%s,%s",
                        tipo, aluno.getNome(), aluno.getId(), aluno.getMatricula(),
                        aluno.getCurso(), aluno.getStatus()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }
    
    // Carrega a lista de alunos do arquivo.
     
    public List<Aluno> carregarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        File file = new File(ALUNOS_FILE);
        
        if (!file.exists()) {
            return alunos;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Pular cabeçalho
            String line = reader.readLine();
            
            // Ler dados dos alunos
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String tipo = parts[0];
                    String nome = parts[1];
                    String id = parts[2];
                    String matricula = parts[3];
                    String curso = parts[4];
                    String status = parts[5];
                    
                    Aluno aluno;
                    if ("especial".equals(tipo)) {
                        aluno = new AlunoEspecial(nome, id, matricula, curso);
                    } else {
                        aluno = new Aluno(nome, id, matricula, curso);
                    }
                    aluno.setStatus(status);
                    alunos.add(aluno);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar alunos: " + e.getMessage());
        }
        
        return alunos;
    }
    
    // Salva a lista de disciplinas em arquivo.
     
    public void salvarDisciplinas(List<Disciplina> disciplinas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DISCIPLINAS_FILE))) {
            // Escrever cabeçalho
            writer.println("codigo,nome,cargaHoraria,preRequisitos");
            
            // Escrever dados das disciplinas
            for (Disciplina disciplina : disciplinas) {
                StringBuilder preReqs = new StringBuilder();
                for (Disciplina preReq : disciplina.getPreRequisitos()) {
                    if (preReqs.length() > 0) {
                        preReqs.append(";");
                    }
                    preReqs.append(preReq.getCodigo());
                }
                
                writer.println(String.format("%s,%s,%d,%s",
                        disciplina.getCodigo(), disciplina.getNome(),
                        disciplina.getCargaHoraria(), preReqs.toString()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
    
    // Carrega a lista de disciplinas do arquivo.
     
    public List<Disciplina> carregarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        Map<String, Disciplina> disciplinasMap = new HashMap<>();
        Map<String, List<String>> preReqsMap = new HashMap<>();
        
        File file = new File(DISCIPLINAS_FILE);
        
        if (!file.exists()) {
            return disciplinas;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Pular cabeçalho
            String line = reader.readLine();
            
            // Ler dados das disciplinas
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String codigo = parts[0];
                    String nome = parts[1];
                    int cargaHoraria = Integer.parseInt(parts[2]);
                    String preReqsStr = parts[3];
                    
                    Disciplina disciplina = new Disciplina(codigo, nome, cargaHoraria);
                    disciplinas.add(disciplina);
                    disciplinasMap.put(codigo, disciplina);
                    
                    // Armazenar pré-requisitos para processamento posterior
                    if (!preReqsStr.isEmpty()) {
                        List<String> preReqs = Arrays.asList(preReqsStr.split(";"));
                        preReqsMap.put(codigo, preReqs);
                    }
                }
            }
            
            // Processar pré-requisitos
            for (String codigo : preReqsMap.keySet()) {
                Disciplina disciplina = disciplinasMap.get(codigo);
                for (String preReqCodigo : preReqsMap.get(codigo)) {
                    Disciplina preReq = disciplinasMap.get(preReqCodigo);
                    if (preReq != null) {
                        disciplina.adicionarPreRequisito(preReq);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
        
        return disciplinas;
    }
    
    // Salva a lista de turmas em arquivo.
     
    public void salvarTurmas(List<Turma> turmas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TURMAS_FILE))) {
            // Escrever cabeçalho
            writer.println("disciplina,professor,semestre,avaliacao,remota,sala,horario,capacidade");
            
            // Escrever dados das turmas
            for (Turma turma : turmas) {
                writer.println(String.format("%s,%s,%s,%s,%b,%s,%s,%d",
                        turma.getDisciplina().getCodigo(), turma.getProfessor(),
                        turma.getSemestre(), turma.getFormaAvaliacao().getTipo(),
                        turma.isRemota(), turma.getSala() != null ? turma.getSala() : "",
                        turma.getHorario(), turma.getCapacidade()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar turmas: " + e.getMessage());
        }
    }
    
    // Carrega a lista de turmas do arquivo.
     
    public List<Turma> carregarTurmas() {
        List<Turma> turmas = new ArrayList<>();
        File file = new File(TURMAS_FILE);
        
        if (!file.exists()) {
            return turmas;
        }
        
        // Primeiro, carregar as disciplinas
        List<Disciplina> disciplinas = carregarDisciplinas();
        Map<String, Disciplina> disciplinasMap = new HashMap<>();
        for (Disciplina disciplina : disciplinas) {
            disciplinasMap.put(disciplina.getCodigo(), disciplina);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Pular cabeçalho
            String line = reader.readLine();
            
            // Ler dados das turmas
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String codigoDisciplina = parts[0];
                    String professor = parts[1];
                    String semestre = parts[2];
                    String tipoAvaliacao = parts[3];
                    boolean remota = Boolean.parseBoolean(parts[4]);
                    String sala = parts[5].isEmpty() ? null : parts[5];
                    String horario = parts[6];
                    int capacidade = Integer.parseInt(parts[7]);
                    
                    // Buscar a disciplina
                    Disciplina disciplina = disciplinasMap.get(codigoDisciplina);
                    if (disciplina != null) {
                        // Criar a forma de avaliação
                        Avaliacao avaliacao;
                        if ("A".equals(tipoAvaliacao)) {
                            avaliacao = new AvaliacaoA();
                        } else {
                            avaliacao = new AvaliacaoB();
                        }
                        
                        // Criar a turma
                        Turma turma = new Turma(disciplina, professor, semestre, avaliacao, remota, sala, horario, capacidade);
                        turmas.add(turma);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar turmas: " + e.getMessage());
        }
        
        return turmas;
    }
}
