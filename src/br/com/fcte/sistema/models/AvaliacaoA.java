package src.br.com.fcte.sistema.models;

import java.util.Map;

// Implementação da avaliação do tipo A: Media Final = (P1 + P2 + P3 + L + S) / 5

public class AvaliacaoA implements Avaliacao {
    
    // Retorna o tipo de avaliação.
     
    @Override
    public String getTipo() {
        return "A";
    }
    
    // Calcula a média final usando a fórmula: (P1 + P2 + P3 + L + S) / 5
     
    @Override
    public double calcularMedia(Map<String, Double> notas) {
        // Verificar se todas as notas necessárias estão presentes
        if (!notas.containsKey("P1") || !notas.containsKey("P2") || 
            !notas.containsKey("P3") || !notas.containsKey("L") || 
            !notas.containsKey("S")) {
            return -1;
        }
        
        // Calcular a média.
        return (notas.get("P1") + notas.get("P2") + notas.get("P3") + 
                notas.get("L") + notas.get("S")) / 5.0;
    }
    
    @Override
    public String toString() {
        return "Tipo de Avaliação: " + getTipo() + " - Fórmula: (P1 + P2 + P3 + L + S) / 5";
    }
}
