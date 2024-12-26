package Labs_OOP_sem_3.entities;

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
@Table(name = "functions")
public class FunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "function_hash", nullable = false)
    private String hash;
    @Column(name = "function_type", nullable = false)
    private String type;
    @Column(name = "id_user", nullable = false)
    private int id_user;
    @Column(name = "composite")
    private String composite;
    @Column(name = "name")
    private String name;
    @Column(name = "id_comp")
    private int id_comp;
}
