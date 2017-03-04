package neuralnet;

public class NeuralNet {
	private int hiddenNodes = 10;
	private int outputNodes = 2;
	private Layer hiddenLayer;
	private Layer outputLayer;

	public NeuralNet(double[] hiddenWeights, double[] outputWeights) {
		hiddenLayer = new Layer(hiddenNodes, hiddenWeights);
		outputLayer = new Layer(outputNodes, outputWeights);
	}
	
	public boolean[] play(double[] inputs) {
		return null;
	}
}