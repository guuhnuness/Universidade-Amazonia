package MainScreen;

import javafx.beans.property.*;

public class Cursos {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty nivel;
    private final IntegerProperty ano;

    public Cursos(int id, String nome, String nivel, int ano) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.nivel = new SimpleStringProperty(nivel);
        this.ano = new SimpleIntegerProperty(ano);
    }

    public Cursos(String nome, String nivel, int ano) {
        this(0, nome, nivel, ano);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty(){ return id; }
    public void setId(int id){ this.id.set(id); }

    public String getNome(){ return nome.get(); }
    public StringProperty nomeProperty(){ return nome; }
    public void setNome(String nome){ this.nome.set(nome); }

    public String getNivel(){ return nivel.get(); }
    public StringProperty nivelProperty(){ return nivel; }
    public void setNivel(String nivel){ this.nivel.set(nivel); }

    public int getAno(){ return ano.get(); }
    public IntegerProperty anoProperty(){ return ano; }
    public void setAno(int ano){ this.ano.set(ano); }

    @Override
    public String toString() { return getNome(); } // para ComboBox mostrar nome
}
