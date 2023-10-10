package dao.Implement;

import dao.CardDAO;
import dao.JDBCConnection;
import model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardDAOImpl implements CardDAO<Card> {
    private final String tableName = "card";
    private final String userName = "userName";
    private final String passWord = "passWord";
    private final String balance = "balance";
    private final String InsertCard = "INSERT INTO %s(%s, %s, %s)VALUES (?, ?, ?)".
            formatted(tableName, userName, passWord, balance);
    private final String UpdateCard = "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;".
            formatted(tableName, passWord, balance, userName);
    private final String DeleteCard = "DELETE FROM %s WHERE %s = ?;".formatted(tableName, userName);
    private final String SelectCard = "SELECT * FROM %s;".formatted(tableName);
    private final String SelectById = "SELECT * FROM %s WHERE %s = ?;".formatted(tableName, userName);

    public static CardDAOImpl getInstance() {
        return new CardDAOImpl();
    }

    @Override
    public void insert(Card card) {
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(InsertCard);
            preparedStatement.setString(1, card.getUserName());
            preparedStatement.setString(2, card.getPassWord());
            preparedStatement.setFloat(3, card.getBalance());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("added successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Card card) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(UpdateCard);
            preparedStatement.setString(1, card.getPassWord());
            preparedStatement.setFloat(2, card.getBalance());
            preparedStatement.setString(3, card.getUserName());
            int result = preparedStatement.executeUpdate();
            connection.commit();
            if (result != 0) {
                System.out.println("update successfully!");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
    }

    @Override
    public void delete(String id) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DeleteCard);
            preparedStatement.setString(1, id);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("delete successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
    }

    @Override
    public ArrayList<Card> selectAll() {
        Connection connection = null;
        ArrayList<Card> cardArrayList = new ArrayList<Card>();
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectCard);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String passWord = resultSet.getString(this.passWord);
                float balance = resultSet.getFloat(this.balance);
                Card card = new Card(userName, passWord, balance);
                cardArrayList.add(card);
            }
            if (cardArrayList != null) {
                System.out.println("select successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
        return cardArrayList;
    }

    @Override
    public Card selectById(String id) {
        Connection connection = null;
        Card card = null;
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectById);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String passWord = resultSet.getString(this.passWord);
                float balance = resultSet.getFloat(this.balance);
                card = new Card(userName, passWord, balance);
            }
            if (card != null) {
                System.out.println("select by id successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
        return card;
    }

}