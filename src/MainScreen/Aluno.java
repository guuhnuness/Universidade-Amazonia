package MainScreen;

import javafx.beans.property.*;

public class Aluno {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty status;
    private final DoubleProperty np1;
    private final DoubleProperty np2;
    private final IntegerProperty cursoId;

    // Carregado do BD (com id)
    public Aluno(int id, String nome, String status, double np1, double np2, int cursoId) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.status = new SimpleStringProperty(status);
        this.np1 = new SimpleDoubleProperty(np1);
        this.np2 = new SimpleDoubleProperty(np2);
    
        this.cursoId = new SimpleIntegerProperty(cursoId);
    }

    // Para inserir novo (sem id)
    public Aluno(String nome, String status, double np1, double np2, int cursoId) {
        this(0, nome, status, np1, np2, cursoId);
    }

    public int getId(){ return id.get(); }
    public IntegerProperty idProperty(){ return id; }
    public void setId(int id){ this.id.set(id); }

    public String getNome(){ return nome.get(); }
    public StringProperty nomeProperty(){ return nome; }
    public void setNome(String nome){ this.nome.set(nome); }

    public String getStatus(){ return status.get(); }
    public StringProperty statusProperty(){ return status; }
    public void setStatus(String status){ this.status.set(status); }

    public double getNp1(){ return np1.get(); }
    public DoubleProperty np1Property(){ return np1; }
    public void setNp1(double np1){ this.np1.set(np1); }

    public double getNp2(){ return np2.get(); }
    public DoubleProperty np2Property(){ return np2; }
    public void setNp2(double np2){ this.np2.set(np2); }

  

    public int getCursoId(){ return cursoId.get(); }
    public IntegerProperty cursoIdProperty(){ return cursoId; }
    public void setCursoId(int cursoId){ this.cursoId.set(cursoId); }
}
