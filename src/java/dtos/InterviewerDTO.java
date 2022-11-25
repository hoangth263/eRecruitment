package dtos;

/**
 *
 * @author Thien's
 */
public class InterviewerDTO {
    private String id;
    private String email;
    private String name;
    private String major_id;
    private boolean isAvailable;

    public InterviewerDTO() {
    }

    public InterviewerDTO(String id, String email, String name, String major_id, boolean isAvailable) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.major_id = major_id;
        this.isAvailable = isAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
