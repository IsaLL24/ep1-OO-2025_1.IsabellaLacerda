package src.br.com.fcte.sistema.utils;

import java.util.Scanner;

/**
 * Classe utilitária para gerenciar os menus do sistema.
 */
public class Menu {
    private Scanner scanner;
    
    /**
     * Inicializa uma nova instância da classe Menu.
     */
    public Menu() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Exibe o menu principal e retorna a opção selecionada.
     * 
     * @return Opção selecionada
     */
    public int exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA ACADÊMICO FCTE =====");
        System.out.println("1. Modo Aluno");
        System.out.println("2. Modo Disciplina/Turma");
        System.out.println("3. Modo Avaliação/Frequência");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        
        return lerOpcao(0, 3);
    }
    
    /**
     * Exibe o menu do modo aluno e retorna a opção selecionada.
     * 
     * @return Opção selecionada
     */
    public int exibirMenuAluno() {
        System.out.println("\n===== MODO ALUNO =====");
        System.out.println("1. Cadastrar aluno normal");
        System.out.println("2. Cadastrar aluno especial");
        System.out.println("3. Editar aluno");
        System.out.println("4. Listar alunos");
        System.out.println("5. Matricular aluno em disciplina");
        System.out.println("6. Trancar disciplina");
        System.out.println("7. Trancar semestre");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        
        return lerOpcao(0, 7);
    }
    
    /**
     * Exibe o menu do modo disciplina/turma e retorna a opção selecionada.
     * 
     * @return Opção selecionada
     */
    public int exibirMenuDisciplina() {
        System.out.println("\n===== MODO DISCIPLINA/TURMA =====");
        System.out.println("1. Cadastrar disciplina");
        System.out.println("2. Adicionar pré-requisito");
        System.out.println("3. Criar turma");
        System.out.println("4. Listar disciplinas");
        System.out.println("5. Listar turmas");
        System.out.println("6. Listar turmas por disciplina");
        System.out.println("7. Listar turmas por professor");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        
        return lerOpcao(0, 7);
    }
    
    /**
     * Exibe o menu do modo avaliação/frequência e retorna a opção selecionada.
     * 
     * @return Opção selecionada
     */
    public int exibirMenuAvaliacao() {
        System.out.println("\n===== MODO AVALIAÇÃO/FREQUÊNCIA =====");
        System.out.println("1. Lançar nota");
        System.out.println("2. Lançar frequência");
        System.out.println("3. Calcular média final");
        System.out.println("4. Calcular frequência");
        System.out.println("5. Verificar aprovação");
        System.out.println("6. Gerar relatório por turma");
        System.out.println("7. Gerar relatório por disciplina");
        System.out.println("8. Gerar relatório por professor");
        System.out.println("9. Gerar boletim de aluno");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        
        return lerOpcao(0, 9);
    }
    
    /**
     * Lê uma opção do usuário dentro de um intervalo válido.
     * 
     * @param min Valor mínimo válido
     * @param max Valor máximo válido
     * @return Opção selecionada
     */
    public int lerOpcao(int min, int max) {
        int opcao = -1;
        boolean opcaoValida = false;
        
        while (!opcaoValida) {
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= min && opcao <= max) {
                    opcaoValida = true;
                } else {
                    System.out.print("Opção inválida. Digite novamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
        
        return opcao;
    }
    
    /**
     * Lê uma string do usuário.
     * 
     * @param mensagem Mensagem a ser exibida
     * @return String lida
     */
    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
    /**
     * Lê um inteiro do usuário.
     * 
     * @param mensagem Mensagem a ser exibida
     * @return Inteiro lido
     */
    public int lerInteiro(String mensagem) {
        int valor = 0;
        boolean valorValido = false;
        
        while (!valorValido) {
            try {
                System.out.print(mensagem);
                valor = Integer.parseInt(scanner.nextLine());
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
        
        return valor;
    }
    
    /**
     * Lê um double do usuário.
     * 
     * @param mensagem Mensagem a ser exibida
     * @return Double lido
     */
    public double lerDouble(String mensagem) {
        double valor = 0;
        boolean valorValido = false;
        
        while (!valorValido) {
            try {
                System.out.print(mensagem);
                valor = Double.parseDouble(scanner.nextLine());
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número decimal.");
            }
        }
        
        return valor;
    }
    
    /**
     * Lê um booleano do usuário (S/N).
     * 
     * @param mensagem Mensagem a ser exibida
     * @return Booleano lido
     */
    public boolean lerBooleano(String mensagem) {
        System.out.print(mensagem + " (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        
        while (!resposta.equals("S") && !resposta.equals("N")) {
            System.out.print("Resposta inválida. Digite S ou N: ");
            resposta = scanner.nextLine().trim().toUpperCase();
        }
        
        return resposta.equals("S");
    }
    
    /**
     * Exibe uma mensagem e aguarda o usuário pressionar Enter.
     * 
     * @param mensagem Mensagem a ser exibida
     */
    public void pressionarEnter(String mensagem) {
        System.out.print(mensagem);
        scanner.nextLine();
    }
    
    /**
     * Fecha o scanner.
     */
    public void fechar() {
        scanner.close();
    }
}
