package model.resolver;

import model.hardware.Metrics;

import java.util.Map;
import java.util.Optional;

public interface Resolver<T, T2> {

    <E extends Exception> Optional<Map.Entry<Metrics, T>> resolve(T2 param) throws E;
}
