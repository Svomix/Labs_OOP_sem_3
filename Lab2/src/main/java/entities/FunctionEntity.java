package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
    @Column(name = "modification", nullable = false)
    @Enumerated(EnumType.STRING)
    private Modification_type mod;
    private String hash; // remake
    @Column(name = "arr_x", nullable = false)
    private ArrayList<Double> xArr;
    @Column(name = "arr_y", nullable = false)
    private Double[] yArr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionEntity that = (FunctionEntity) o;
        return mod == that.mod && Objects.equals(hash, that.hash) && Objects.deepEquals(xArr, that.xArr) && Objects.deepEquals(yArr, that.yArr);
    }

    @Override
    public int hashCode() {

    }
}
