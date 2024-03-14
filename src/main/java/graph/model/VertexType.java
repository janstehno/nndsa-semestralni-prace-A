package graph.model;

public enum VertexType {
    S(0), V(1), E(2), SE(3), C(4);

    private final Integer order;

    VertexType(int order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
}
