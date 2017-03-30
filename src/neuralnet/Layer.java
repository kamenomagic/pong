package neuralnet;

public class Layer {
	public Node[] nodes;

	public Layer(int size, double[] weights) {
		nodes = new Node[size];
		int inputs = weights.length / size;
		double[] nodeWeights;
		for (int i = 0; i < nodes.length; i++) {
			nodeWeights = new double[inputs];
			for(int j = 0; j < nodeWeights.length; j++) {
				nodeWeights[j] = weights[i * inputs + j];
			}
			nodes[i] = new Node(nodeWeights);
		}
	}

	public double[] calculateOutputs(double inputs[]) {
		double[] outputs = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			outputs[i] = ((Node) nodes[i]).calculateNet(inputs);
		}
		return outputs;
	}
}