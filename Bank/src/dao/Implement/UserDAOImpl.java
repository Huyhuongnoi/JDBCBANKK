package dao.Implement;

import dao.JDBCConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements dao.UserDAO<User> {
    private final String tableName = "user";
    private final String userName = "userName";
    private final String fullName = "fullName";
    private final String age = "age";
    private final String sex = "sex";
    private final String address = "address";
    private final String InsertUser = "INSERT INTO %s(%s, %s, %s, %s, %s)VALUES (?, ?, ?, ?, ?)".
            formatted(tableName, userName, fullName, age, sex, address);
    private final String UpdateUser = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;".

            formatted(tableName, fullName, age, sex, address, userName);
    private final String DeleteUser = "DELETE FROM %s WHERE %s = ?;".formatted(tableName, userName);
    private final String SelectAll = "SELECT * FROM %s;".formatted(tableName);
    private final String SelectById = "SELECT * FROM %s WHERE %s = ?;".formatted(tableName, userName);

    public static UserDAOImpl getInstance() {
        return new UserDAOImpl();
    }

    @Override
    public void insert(User user) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(InsertUser);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getSex());
            preparedStatement.setString(5, user.getAddress());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("added successfully!");
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
    public void update(User user) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UpdateUser);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getUserName());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("update successfully!");
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
    public void delete(String id) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DeleteUser);
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
    public ArrayList<User> selectAll() {
        Connection connection = null;
        ArrayList<User> userArrayList = new ArrayList<User>();
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                User user = new User(userName, fullName, age, sex, address);
                userArrayList.add(user);
            }
            if (userArrayList != null) {
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
        return userArrayList;
    }

    @Override
    public User selectById(String id) {
        Connection connection = null;
        User user = null;
        try {
            connection = JDBCConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectById);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                user = new User(userName, fullName, age, sex, address);

            }
            if (user != null) {
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
        return user;
    }
}
