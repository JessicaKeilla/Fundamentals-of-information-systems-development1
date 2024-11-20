public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String marital_status;
    private String licence_type;
    private Integer driving_skills_points;

    public User(Long id, String firstName, String lastName, Integer age, String marital_status, String licence_type, Integer driving_skills_points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.marital_status = marital_status;
        this.licence_type = licence_type;
        this.driving_skills_points = driving_skills_points;
    }

    public User(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User(String marital_status, String licence_type, Integer driving_skills_points, Long id) {
        this.marital_status = marital_status;
        this.licence_type = licence_type;
        this.driving_skills_points = driving_skills_points;
        this.id = id;
    }

    public  Long getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public String getLicence_type() {
        return licence_type;
    }

    public Integer getDriving_skills_points() {
        return driving_skills_points;
    }
}
