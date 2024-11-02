package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "functions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Function
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "modification",nullable = false)
    private Modification_type mod;
    private String hash; // remake
    @Column(name = "arr_x",nullable = false)
    private Double[] xArr;
    @Column(name = "arr_y",nullable = false)
    private Double[] yArr;
}

enum Modification_type
{
    usual,
    strict,
    unmodifiable,
    strictUnmodifiable
}