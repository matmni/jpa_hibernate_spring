package entity.hibernate;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
public class Computer extends Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial_number")
    private String serialNumber;
    @JoinTable(
            name = "computer_student",
            joinColumns = {
                    @JoinColumn(name = "computer_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "student_id")
            }
    )
    @ElementCollection(targetClass = Student.class)
    private Set<Student> students = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", students=" + students +
                '}';
    }
}
