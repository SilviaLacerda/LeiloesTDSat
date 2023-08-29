
/**
 *
 * @author Silvia Lacerda
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    conectaDAO conexao;

    public ProdutosDAO() {
        this.conexao = new conectaDAO();
        this.conn = this.conexao.connectDB();
    }

    public void cadastrarProduto(ProdutosDTO produto) {
        // conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos(nome, valor, status) VALUES "
                + "(?, ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.execute();
             JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
            //tratando o erro, caso ele ocorra
        } catch (Exception e) {
            System.out.println("Erro ao inserir cadastro de produtos: " + e.getMessage());
        }
    }

    public List<ProdutosDTO> listarProdutos() {

        String sql = "SELECT * FROM produtos";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            //      stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            List<ProdutosDTO> listaProdutos = new ArrayList<>();
            //percorrer o resultSet e salvar as informações dentro de uma variável
            //Depois salva essa variavel dentro da lista

            //Estrutura de repetição While
            while (rs.next()) { //.next retorna verdadeiro caso exista uma próxima posição dentro do array
                ProdutosDTO produto = new ProdutosDTO();
                //Salvar dentro da variavel empresa, as informações            
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                //Adicionando os elementos na lista criada
                listaProdutos.add(produto);
            }
            //Após finalizar o while, o retorno será a listaProdutoss, onde cada posição é um registro do banco de dados
            return listaProdutos;

            //Se o método entrar no "Catch" quer dizer que não encontrou nenhuma empresa, então damos um "return null"
        } catch (Exception e) {
            return null;
        }

    }
 public void venderProduto (int id){
      
      //string sql com o código de update para o banco de dados
      String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
      try {
          PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            //Setando os parâmetros
             stmt.setInt(1, id);
           //  System.out.println("será?");
            //Executando a query
            stmt.execute();
          //tratando o erro, caso ele ocorra
      } catch (Exception e) {
          System.out.println("Erro ao editar empresa: " + e.getMessage());
      }
  }
}
