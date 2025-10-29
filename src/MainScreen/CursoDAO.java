package MainScreen;

import universidade.amazonia.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public void inserirCurso(Cursos curso) {
        String sql = "INSERT INTO cursos (nome, nivel, ano) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getNivel());
            stmt.setInt(3, curso.getAno());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Inserção falhou, nenhuma linha afetada.");
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) curso.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cursos> listarCursos() {
        List<Cursos> list = new ArrayList<>();
        String sql = "SELECT id, nome, nivel, ano FROM cursos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Cursos(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("nivel"),
                        rs.getInt("ano")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deletarCurso(int id) {
        String sql = "DELETE FROM cursos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
