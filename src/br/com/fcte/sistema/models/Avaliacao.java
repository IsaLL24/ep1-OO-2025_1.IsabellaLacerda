package br.com.fcte.sistema.models;

/**
 * Interface que define o contrato para os diferentes tipos de avaliação.
 */
public interface Avaliacao {
    
    /**
     * Retorna o tipo de avaliação.
     * 
     * @return Tipo de avaliação
     */
    String getTipo();
    
    /**
     * Calcula a média final com base nas notas fornecidas.
     * 
     * @param notas Mapa com as notas do aluno (P1, P2, P3, L, S)
     * @return Média final calculada
     */
    double calcularMedia(java.util.Map<String, Double> notas);
}

