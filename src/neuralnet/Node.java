package neuralnet;

public class Node {
	public double net;
	public double[] weights;

	public Node(double[] weights) {
		this.weights = weights;
	}

	public double calculateNet(double inputs[]) {
		if (inputs.length != weights.length) {
			throw new RuntimeException("Input count was different than weight count into node");
		}
		net = 0;
		for (int i = 0; i < inputs.length; i++) {
			net += weights[i] * inputs[i];
		}
		return net;
	}
}