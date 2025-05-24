package src.br.com.fcte.sistema.models;

public class Pessoa {
	private String nome;
    private String id;

     // Inicializa uma nova instância da classe Pessoa.
     
     //nome Nome da pessoa.
     //id Identificador único da pessoa.
     
    public Pessoa(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }
    
    // Retorna o nome da pessoa.
    
    public String getNome() {
        return nome;
    }
    // Define o nome da pessoa.
    // nome Novo nome da pessoa

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //Retorna o identificador único da pessoa.
    
    public String getId() {
        return id;
    }
    
    // Define o identificador único da pessoa.
     
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome;
    }
}



