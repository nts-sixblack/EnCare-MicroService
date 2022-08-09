package nts.sixblack.appointmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointmentId;
    private String symptoms;
    private String description;
    private int time;

    @Temporal(TemporalType.TIMESTAMP)
    private Date day;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private long doctorId;
    private long userId;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    @OneToOne(mappedBy = "appointment")
    private Feedback feedback;

    public Appointment(long appointmentId){
        this.appointmentId = appointmentId;
    }
}
