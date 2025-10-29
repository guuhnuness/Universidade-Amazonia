package universidade.amazonia;

import MainScreen.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, status, np1, np2, curso_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getStatus());
            stmt.setDouble(3, aluno.getNp1());
            stmt.setDouble(4, aluno.getNp2());
            stmt.setInt(5, aluno.getCursoId());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Inserção falhou, nenhuma linha afetada.");
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) aluno.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT id, nome, status, np1, np2, curso_id FROM alunos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                alunos.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("status"),
                        rs.getDouble("np1"),
                        rs.getDouble("np2"),
                        rs.getInt("curso_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public void deletarAluno(int id) {
        String sql = "DELETE FROM alunos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
