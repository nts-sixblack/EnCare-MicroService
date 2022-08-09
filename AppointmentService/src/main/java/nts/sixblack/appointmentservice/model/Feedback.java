package nts.sixblack.appointmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;
    private String comment;
    private long rating;

    private long userId;

    @OneToOne
    @JoinColumn(name = "appointmentId")
    private Appointment appointment;

    private Feedback(long feedbackId){
        this.feedbackId = feedbackId;
    }
}
