package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "functions")
public final class FunctionEntity {
    @Id
    @Column(name = "id")
    private Integer hash;
    @Column(name = "arr_x", nullable = false)
    private ArrayList<Double> xArr;
    @Column(name = "arr_y", nullable = false)
    private Double[] yArr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionEntity that = (FunctionEntity) o;
        return Objects.equals(hash, that.hash) && Objects.deepEquals(xArr, that.xArr) && Objects.deepEquals(yArr, that.yArr);
    }
}
