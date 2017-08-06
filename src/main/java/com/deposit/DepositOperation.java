package com.deposit;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by SHonchar on 5/29/2017.
 */
public class DepositOperation implements Comparable<DepositOperation> {

    @Getter @Setter private DepositOperations operation;
    @Getter @Setter private Date dateOfOperation;
    @Getter @Setter private Double sum;

    @Override
    public int compareTo(DepositOperation o) {
        return Comparator
                .comparing(DepositOperation::getDateOfOperation)
                .thenComparing(DepositOperation::getOperation)
                .compare(o, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositOperation that = (DepositOperation) o;

        if (operation != that.operation) return false;
        if (!dateOfOperation.equals(that.dateOfOperation)) return false;
        return sum != null ? sum.equals(that.sum) : that.sum == null;

    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + dateOfOperation.hashCode();
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("dateOfOperation = " + dateOfOperation)
                .add("operation = " + operation)
                .add("sum = " + sum)
                .toString();
    }

}
