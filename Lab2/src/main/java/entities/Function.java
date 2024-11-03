package entities;

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
public class Function
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "modification",nullable = false)
    @Enumerated(EnumType.STRING)
    private Modification_type mod;
    private String hash; // remake
    @Column(name = "arr_x",nullable = false)
    private Double[] xArr;
    @Column(name = "arr_y",nullable = false)
    private Double[] yArr;
}
