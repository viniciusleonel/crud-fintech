package br.com.fiap.fintech.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Teste2 {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String usuario = "RM97971";
        String senha = "270192";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            // Exemplo de sequência
            String sql = "SELECT sua_sequencia.NEXTVAL FROM DUAL";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[] { "CODIGO" })) {
                pstmt.setString(1, "Novo objeto");

                // Execução da instrução SQL
                int linhasAfetadas = pstmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    // Recuperação do ResultSet contendo as chaves geradas
                    ResultSet rs = pstmt.getGeneratedKeys();

                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        System.out.println("O ID gerado é: " + idGerado);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
