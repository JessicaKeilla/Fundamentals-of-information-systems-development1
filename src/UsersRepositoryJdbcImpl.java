import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;

    private static final String SQL_SELECT_FROM_DRIVER = "select * from driver";
    private static final String SQL_INSERT_INTO_DRIVER = "insert into driver (name, surname, age) values (?,?,?)";
    private static final String SQL_UPDATE_DRIVER = "update driver set marital_status = ?, driver_licence_type  = ?, driving_skills_points = ? where id = ?";
    private static final String SQL_DELETE_DRIVER = "delete from driver where id = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {this.connection = connection;}
    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVER);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
            result.add(user);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        String command = SQL_SELECT_FROM_DRIVER+" where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                User u = new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
                return Optional.of(u);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode()+"\n"+sqlException.getSQLState()+"\n"+sqlException.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void save(User entity) {
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL_INSERT_INTO_DRIVER);
            pstm.setString(1, entity.getFirstName());
            pstm.setString(2, entity.getLastName());
            pstm.setInt(3, entity.getAge());

            int linhas = pstm.executeUpdate();
            System.out.println("Number of inserted lines"+linhas);
            pstm.close();

            System.out.println("Data successfully inserted on DB!!");
        } catch (SQLException e) {
            System.out.println("SQL State: "+e.getSQLState()+"\n"+"Message: "+e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL_UPDATE_DRIVER);
            pstm.setString(1, entity.getMarital_status());
            pstm.setString(2, entity.getLicence_type());
            pstm.setInt(3, entity.getDriving_skills_points());
            pstm.setLong(4,entity.getId());

            int linhas = pstm.executeUpdate();
            System.out.println("Number of updated lines"+linhas);
            pstm.close();

            System.out.println("Data successfully updated on DB!!");
        } catch (SQLException e) {
            System.out.println("SQL State: "+e.getSQLState()+"\n"+"Message: "+e.getMessage());
        }
    }

//    @Override
//    public void remove(User entity)
//    {
//
//
//    }

    @Override
    public void removeById(Long id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL_DELETE_DRIVER);
            pstm.setLong(1, id);

            int updated = pstm.executeUpdate();
            System.out.println("Number of updated lines"+updated);
            pstm.close();

            System.out.println("Driver removed from DB!");
        } catch (SQLException e) {
            System.out.println("SQL State: "+e.getSQLState()+"\n"+"Message: "+e.getMessage());
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        String command = SQL_SELECT_FROM_DRIVER +"where age = ?";
        List<User> usersByAge = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setInt(1, age);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                User u = new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
                usersByAge.add(u);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode()+"\n"+sqlException.getSQLState()+"\n"+sqlException.getMessage());
        }
        return usersByAge;
    }

    @Override
    public List<User> findAllByMaritalStatus(String maritalStatus) {
        String command = SQL_SELECT_FROM_DRIVER+" where marital_status = ?";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, maritalStatus);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                User u = new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));

                users.add(u);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode()+"\n"+sqlException.getSQLState()+"\n"+sqlException.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findAllByLicenceType(String licenceType) {
        String command = SQL_SELECT_FROM_DRIVER+" where driver_licence_type = ?";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, licenceType);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                User u = new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));
                users.add(u);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode()+"\n"+sqlException.getSQLState()+"\n"+sqlException.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findAllByPintsGreaterThan(int points) {
        String command = SQL_SELECT_FROM_DRIVER+" where CAST (driving_skills_points AS INTEGER) > ?";
        List<User> usersByAge = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setInt(1, points);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                User u = new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));
                usersByAge.add(u);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode()+"\n"+sqlException.getSQLState()+"\n"+sqlException.getMessage());
        }
        return usersByAge;
    }
}
