package src.br.com.fcte.sistema.models;

//Interface que define o contrato para os diferentes tipos de avaliação
 
public interface Avaliacao {
    
    // Retorna o tipo de avaliação.
     
    String getTipo();
    
    // Calcula a média final com base nas notas fornecidas.
    // notas Mapa com as notas do aluno (P1, P2, P3, L, S)
     
    double calcularMedia(java.util.Map<String, Double> notas);
}

