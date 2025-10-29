package MainScreen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import universidade.amazonia.AlunoDAO;

public class MainController implements Initializable {

    // ==== TABELA DE ALUNOS ====
    @FXML private TableView<Aluno> tabelaAlunos;
    @FXML private TableColumn<Aluno, Integer> colunaId;
    @FXML private TableColumn<Aluno, String> colunaNome;
    @FXML private TableColumn<Aluno, Double> colunaNp1;
    @FXML private TableColumn<Aluno, Double> colunaNp2;
    @FXML private TableColumn<Aluno, String> colunaCurso;
    @FXML private TableColumn<Aluno, String> colunaStatus;

    // Campos de aluno (UI)
    @FXML private TextField campoNome;
    @FXML private TextField campoNp1;
    @FXML private TextField campoNp2;
    @FXML private ComboBox<String> campoStatus;
    @FXML private ComboBox<Cursos> campoCurso;

    private final ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();

    // ==== PÁGINAS ====
    @FXML private Pane home_page;
    @FXML private Pane alunos_page;
    @FXML private Pane cursos_page;
    @FXML private Pane notas_page;
    @FXML private Pane top_bar;
    @FXML private Button gerenciar_alunos;
    @FXML private Button btn_home;
    @FXML private Button btn_cursos;
    @FXML private Button btn_sair;
    @FXML private Button btn_notas;

    // ==== TABELA DE CURSOS ====
    @FXML private TextField campo_nome_curso;
    @FXML private ComboBox<String> campo_nivel_curso;
    @FXML private TextField campo_ano_curso;
    @FXML private TableView<Cursos> cursoTable;
    @FXML private TableColumn<Cursos, String> coluna_nome_curso;
    @FXML private TableColumn<Cursos, String> coluna_nivel_curso;
    @FXML private TableColumn<Cursos, Integer> coluna_ano_curso;

    private final ObservableList<Cursos> cursos = FXCollections.observableArrayList();

    // ==== DAOs ====
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    // ==== NOTAS UI ====
    @FXML private ComboBox<Aluno> cbAlunos;
    @FXML private Label lblId;
    @FXML private Label lblNome;
    @FXML private Label lblCurso;
    @FXML private Button btnCalcular;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Label lblMedia;
    @FXML private Label lblStatusResumo;
    @FXML private Label lblSituacaoResumo;
    @FXML private Label lblExameResumo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ===== CONFIGURAÇÃO DA TABELA DE ALUNOS =====
        colunaId.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        colunaNome.setCellValueFactory(cell -> cell.getValue().nomeProperty());
        colunaStatus.setCellValueFactory(cell -> cell.getValue().statusProperty());
        colunaNp1.setCellValueFactory(cell -> cell.getValue().np1Property().asObject());
        colunaNp2.setCellValueFactory(cell -> cell.getValue().np2Property().asObject());
        colunaCurso.setCellValueFactory(data -> {
            int cursoId = data.getValue().getCursoId();
            Cursos c = cursos.stream().filter(x -> x.getId() == cursoId).findFirst().orElse(null);
            return new SimpleStringProperty(c != null ? c.getNome() : "N/A");
        });
        campoStatus.setItems(FXCollections.observableArrayList("Ativo", "Inativo"));
        tabelaAlunos.setItems(listaAlunos);

        // ===== CONFIGURAÇÃO DA TABELA DE CURSOS =====
        coluna_nome_curso.setCellValueFactory(c -> c.getValue().nomeProperty());
        coluna_nivel_curso.setCellValueFactory(c -> c.getValue().nivelProperty());
        coluna_ano_curso.setCellValueFactory(c -> c.getValue().anoProperty().asObject());
        cursoTable.setItems(cursos);
        campo_nivel_curso.setItems(FXCollections.observableArrayList(
                "Graduação","Pós-graduação","Doutorado","Bacharelado","Tecnólogo"));

        // ===== CARREGAR DADOS DO BD =====
        cursos.addAll(cursoDAO.listarCursos());
        if (campoCurso != null) campoCurso.setItems(cursos);
        listaAlunos.addAll(alunoDAO.listarAlunos());

        // ===== CONFIGURAÇÃO TELA DE NOTAS =====
        cbAlunos.setItems(listaAlunos);
        cbAlunos.setCellFactory(lv -> new ListCell<Aluno>() {
            @Override
            protected void updateItem(Aluno item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
        cbAlunos.setButtonCell(new ListCell<Aluno>() {
            @Override
            protected void updateItem(Aluno item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });

        cbAlunos.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                lblId.setText(String.valueOf(newV.getId()));
                lblNome.setText(newV.getNome());
                Cursos c = cursos.stream().filter(x -> x.getId() == newV.getCursoId()).findFirst().orElse(null);
                lblCurso.setText(c != null ? c.getNome() : "N/A");

                progressIndicator.setProgress(0.0);
                lblMedia.setText("0.0");
                progressIndicator.setStyle("");

                // Limpar resumo
                lblStatusResumo.setText("-");
                lblSituacaoResumo.setText("-");
                lblExameResumo.setText("-");
            }
        });

        btnCalcular.setOnAction(e -> {
            Aluno a = cbAlunos.getValue();
            if (a != null) {
                double media = (a.getNp1() + a.getNp2()) / 2.0;
                lblMedia.setText(String.format("%.2f", media));
                progressIndicator.setProgress(media / 10.0);

                if (media < 7.0) progressIndicator.setStyle("-fx-progress-color: red;");
                else progressIndicator.setStyle("-fx-progress-color: green;");

                lblStatusResumo.setText(a.getStatus());
                lblSituacaoResumo.setText(media >= 7.0 ? "Aprovado" : "Reprovado");
                lblExameResumo.setText(media < 7.0 ? "Sim" : "Não");
            }
        });
    }

    // ====================== ALUNOS ======================
    @FXML
    private void adicionarAluno(MouseEvent event) {
        try {
            String nome = campoNome.getText().trim();
            String np1Text = campoNp1.getText().trim();
            String np2Text = campoNp2.getText().trim();
            String status = campoStatus.getValue();
            Cursos cursoSelecionado = campoCurso.getValue();

            if (nome.isEmpty() || np1Text.isEmpty() || np2Text.isEmpty()
                    || status == null || cursoSelecionado == null) {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            double np1 = Double.parseDouble(np1Text);
            double np2 = Double.parseDouble(np2Text);
           

            if (np1 < 0 || np1 > 10 || np2 < 0 || np2 > 10 ) {
                mostrarAlerta("Erro", "As notas devem estar entre 0 e 10.");
                return;
            }

            Aluno novo = new Aluno(nome, status, np1, np2, cursoSelecionado.getId());
            alunoDAO.inserirAluno(novo);
            listaAlunos.add(novo);
            limparCampos(null);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite números válidos para NP1/NP2.");
        }
    }

    @FXML
    private void removerAluno(MouseEvent event) {
        Aluno sel = tabelaAlunos.getSelectionModel().getSelectedItem();
        if (sel != null) {
            alunoDAO.deletarAluno(sel.getId());
            listaAlunos.remove(sel);
        } else mostrarAlerta("Erro", "Selecione um aluno para remover.");
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        campoNome.clear();
        campoNp1.clear();
        campoNp2.clear();
        campoStatus.setValue(null);
        campoCurso.setValue(null);
    }

    // ====================== CURSOS ======================
    @FXML
    public void adicionarCurso(MouseEvent event) {
        try {
            String nome = campo_nome_curso.getText().trim();
            String nivel = campo_nivel_curso.getValue();
            String anoText = campo_ano_curso.getText().trim();

            if (nome.isEmpty() || nivel == null || anoText.isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            int ano = Integer.parseInt(anoText);
            Cursos curso = new Cursos(nome, nivel, ano);
            cursoDAO.inserirCurso(curso);
            cursos.add(curso);

            if (campoCurso != null) campoCurso.setItems(cursos);
            limparCursos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Ano inválido.");
        }
    }

    @FXML
    public void removerCurso(MouseEvent event) {
        Cursos sel = cursoTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            cursoDAO.deletarCurso(sel.getId());
            cursos.remove(sel);
            if (campoCurso != null) campoCurso.setItems(cursos);
        } else mostrarAlerta("Erro", "Selecione um curso para remover.");
    }

    @FXML
    public void limparCursos() {
        campo_nome_curso.clear();
        campo_ano_curso.clear();
        campo_nivel_curso.setValue(null);
    }

    // ====================== PÁGINAS ======================
    @FXML
    public void BtnAlunos(MouseEvent event) {
        home_page.setVisible(false);
        cursos_page.setVisible(false);
        notas_page.setVisible(false);
        alunos_page.setVisible(true);
        top_bar.setVisible(true);
        resetarBotoes();
        gerenciar_alunos.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    public void BtnHome(MouseEvent event) {
        home_page.setVisible(true);
        cursos_page.setVisible(false);
        alunos_page.setVisible(false);
        notas_page.setVisible(false);
        top_bar.setVisible(true);
        resetarBotoes();
        btn_home.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    public void BtnCursos(MouseEvent event) {
        home_page.setVisible(false);
        cursos_page.setVisible(true);
        alunos_page.setVisible(false);
        notas_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_cursos.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    public void BtnNotas(MouseEvent event) {
        notas_page.setVisible(true);
        home_page.setVisible(false);
        cursos_page.setVisible(false);
        alunos_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_notas.setStyle("-fx-background-color: #141313;");
    }

    private void resetarBotoes() {
        gerenciar_alunos.setStyle("-fx-background-color: #353A56;");
        btn_home.setStyle("-fx-background-color: #353A56;");
        btn_cursos.setStyle("-fx-background-color: #353A56;");
        btn_notas.setStyle("-fx-background-color: #353A56;");
        btn_sair.setStyle("-fx-background-color: #353A56;");
    }

    // ====================== ALERTA ======================
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void sairAplicacao(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de saída");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja sair da aplicação?");

        resetarBotoes();
        btn_sair.setStyle("-fx-background-color: #141313;");

        if (alert.showAndWait().get() == ButtonType.OK) System.exit(0);
    }
}
