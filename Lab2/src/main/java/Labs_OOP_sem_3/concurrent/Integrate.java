package Labs_OOP_sem_3.concurrent;

import Labs_OOP_sem_3.functions.MathFunction;
import Labs_OOP_sem_3.functions.TabulatedFunction;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Integrate implements MathFunction, Serializable {
    @Serial
    private static final long serialVersionUID = 7604611741409988062L;
    private final transient ExecutorService executorService;
    private final int numberOfThreads;

    @JsonCreator
    public Integrate() {
        int numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    public Integrate(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    public double integrate(TabulatedFunction tabulatedFunction, int overallNumberOfSections) throws InterruptedException, ExecutionException {
        double integrationSegmentLength = (tabulatedFunction.rightBound() - tabulatedFunction.leftBound()) / numberOfThreads;
        List<Future<Double>> futures = new ArrayList<>();
        int sectionsPerThread = Math.max(overallNumberOfSections / numberOfThreads, 1);

        for (int threadIndex = 0; threadIndex < numberOfThreads; threadIndex++) {
            double leftBound = tabulatedFunction.leftBound() + threadIndex * integrationSegmentLength;
            double rightBound = leftBound + integrationSegmentLength;

            IntegrationTask task = new IntegrationTask(tabulatedFunction, leftBound, rightBound, sectionsPerThread);
            futures.add(executorService.submit(task));
        }

        double summedIntegrationResult = 0;
        for (Future<Double> future : futures) {
            summedIntegrationResult += future.get();
        }

        return summedIntegrationResult;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        return 462792689;
    }
}