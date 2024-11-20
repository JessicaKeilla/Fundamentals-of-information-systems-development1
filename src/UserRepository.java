import java.util.List;

public interface UserRepository extends CrudRepository<User> {
    List<User> findAllByAge(Integer age);
    List<User> findAllByMaritalStatus(String maritalStatus);
    List<User> findAllByLicenceType(String licenceType);
    List<User> findAllByPintsGreaterThan(int points);
}
