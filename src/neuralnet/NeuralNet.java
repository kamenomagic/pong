package neuralnet;

public class NeuralNet {
	private int inputs = 5;
	private int hiddenNodes = 10;
	private int outputNodes = 2;
	private Layer hiddenLayer;
	private Layer outputLayer;
	
	public NeuralNet(double[] weights) {
		double[] hiddenWeights = new double[(inputs + 1) * hiddenNodes];
		double[] outputWeights = new double[(hiddenNodes + 1) * outputNodes];
		int index;
		for(index = 0; index < hiddenWeights.length; index++) {
			hiddenWeights[index] = weights[index];
		}
		for(int j = 0; j < outputWeights.length; j++) {
			outputWeights[j] = weights[index + j];
		}
		hiddenLayer = new Layer(hiddenNodes, hiddenWeights);
		outputLayer = new Layer(outputNodes, outputWeights);
	}

	public boolean[] play(double[] inputs) {
		double[] hiddenNets = hiddenLayer.calculateOutputs(inputs);
		double[] nets = outputLayer.calculateOutputs(hiddenNets);
		return new boolean[]{nets[0] > 0, nets[1] > 0};
	}
}