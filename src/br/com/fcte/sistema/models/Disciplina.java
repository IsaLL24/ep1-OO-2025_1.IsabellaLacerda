package br.com.fcte.sistema.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma disciplina no sistema acadêmico.
 */
public class Disciplina {
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private List<Disciplina> preRequisitos;
    
    /**
     * Inicializa uma nova instância da classe Disciplina.
     * 
     * @param codigo Código da disciplina
     * @param nome Nome da disciplina
     * @param cargaHoraria Carga horária em horas
     */
    public Disciplina(String codigo, String nome, int cargaHoraria) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>();
    }
    
    /**
     * Retorna o código da disciplina.
     * 
     * @return Código da disciplina
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Define o código da disciplina.
     * 
     * @param codigo Novo código da disciplina
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Retorna o nome da disciplina.
     * 
     * @return Nome da disciplina
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define o nome da disciplina.
     * 
     * @param nome Novo nome da disciplina
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna a carga horária da disciplina.
     * 
     * @return Carga horária da disciplina
     */
    public int getCargaHoraria() {
        return cargaHoraria;
    }
    
    /**
     * Define a carga horária da disciplina.
     * 
     * @param cargaHoraria Nova carga horária da disciplina
     */
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    /**
     * Retorna a lista de disciplinas pré-requisito.
     * 
     * @return Lista de disciplinas pré-requisito
     */
    public List<Disciplina> getPreRequisitos() {
        return preRequisitos;
    }
    
    /**
     * Adiciona uma disciplina como pré-requisito.
     * 
     * @param disciplina A disciplina a ser adicionada como pré-requisito
     */
    public void adicionarPreRequisito(Disciplina disciplina) {
        if (!preRequisitos.contains(disciplina)) {
            preRequisitos.add(disciplina);
        }
    }
    
    /**
     * Remove uma disciplina da lista de pré-requisitos.
     * 
     * @param disciplina A disciplina a ser removida
     */
    public void removerPreRequisito(Disciplina disciplina) {
        preRequisitos.remove(disciplina);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigo)
          .append(", Nome: ").append(nome)
          .append(", Carga Horária: ").append(cargaHoraria).append("h")
          .append(", Pré-requisitos: ");
        
        if (preRequisitos.isEmpty()) {
            sb.append("Nenhum");
        } else {
            for (int i = 0; i < preRequisitos.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(preRequisitos.get(i).getCodigo());
            }
        }
        
        return sb.toString();
    }
}
