package Labs_OOP_sem_3.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "points")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    //@JoinColumn(name = "function_id", nullable = false)
    private FunctionEntity function;

    @Column(name = "x", nullable = false)
    private Double xValue;

    @Column(name = "y", nullable = false)
    private Double yValue;
}
