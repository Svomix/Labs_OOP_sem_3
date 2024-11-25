package Labs_OOP_sem_3.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "functions")
public final class FunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "function_type", nullable = false)
    private String name;
    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonManagedReference
    private List<PointEntity> points;
}
