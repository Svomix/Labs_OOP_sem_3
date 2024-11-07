package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "points")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "function_id", nullable = false)
    private FunctionEntity functionEntity;

    @Column(name = "x", nullable = false)
    private Double xValue;

    @Column(name = "y", nullable = false)
    private Double yValue;
}
