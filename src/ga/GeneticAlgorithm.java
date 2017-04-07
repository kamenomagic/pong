package ga;
import java.util.ArrayList;
import java.util.Random;

import pong.core.PongComponent;

public class GeneticAlgorithm {
	private static double mutationRate = 0.10;
	
	public static double fitness(double[] weights, boolean render) {
		PongComponent pong = new PongComponent().render(render);
		return pong.run(weights);
	}
	
	public static double[] fitness(double[][] population, boolean render) {
		double[] fitnesses = new double[population.length];
		for(int i = 0; i < population.length; i++) {
			fitnesses[i] = fitness(population[i], render);
//			System.out.print(fitnesses[i] + ",");
		}
		return fitnesses;
	}

	public static double[] crossover(double[] parent1, double[] parent2) {
		ArrayList<Double> offspring = new ArrayList<Double>();
		for (int i = 0; i < parent1.length; i++)
        {
            offspring.add(null);
        }
		Random ran = new Random();
		int start = ran.nextInt(parent1.length);
        int end = ran.nextInt(parent1.length);
        if (start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
		for (int i = start; i <= end; i++)
        {
            offspring.set(i, parent1[i]);
        }
		for (int i = 0; i < parent2.length; i++)
        {
			if (!offspring.contains(parent2[i]))
            {
				for (int k = 0; k < offspring.size(); k++)
                {
					if (offspring.get(k) == null)
                    {
                        offspring.set(k, parent2[i]);
                        break;
                    }
				}
			}
		}

		double[] result = new double[offspring.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = offspring.get(i);
		}
		return result;
	}

	public static double[] mutate(double[] weights) {
		Random ran = new Random();
		for (int i = 0; i < weights.length; i++) {
			if (ran.nextDouble() < mutationRate) {
				int b = ran.nextInt(weights.length);
				weights[i] = weights[i] + ran.nextDouble() * 2 - 1;
			}
		}
		return weights;
	}
}