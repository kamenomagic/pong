package ml.neuralnet;

public class Layer {
	public Node[] nodes;

	public Layer(int size, double[] weights) {
		nodes = new Node[size];
		int inputs = weights.length / size;
		double[] nodeWeights;
		for (int i = 0; i < nodes.length - 1; i++) {
			nodeWeights = new double[inputs];
			for(int j = 0; j < nodeWeights.length; j++) {
				nodeWeights[j] = weights[i * inputs + j];
			}
			nodes[i] = new Node(nodeWeights);
		}
	}

	public double[] calculateOutputs(double inputs[]) {
		double[] outputs = new double[nodes.length + 1];
		for (int i = 0; i < nodes.length - 1; i++) {
			outputs[i] = ((Node) nodes[i]).calculateNet(inputs);
		}
		outputs[outputs.length - 1] = 1;
		return outputs;
	}

}