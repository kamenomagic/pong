package ga;
import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
	private static double mutationRate;
	
	public static double fitness(double[] weights) {
		return 0;
	}
	
	public static double[] fitness(double[][] population) {
		return null;
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
		double[] offspring = new double[parent1.length];
		for (int i = 0; i < parent1.Count; i++)
        {
            offspring.Add(null);
        }
		Random ran = new Random();
		int start = ran.Next(0, parent1.Count);
        int end = ran.Next(0, parent1.Count);
        if (start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
		for (int i = start; i <= end; i++)
        {
            offspring[i] = parent1[i];
        }
		for (int i = 0; i < parent2.Count; i++)
        {
			if (!offspring.Contains(parent2[i]))
            {
				for (int k = 0; k < offspring.Count; k++)
                {
					if (offspring[k] == null)
                    {
                        offspring[k] = parent2[i];
                        break;
                    }
				}
			}
		}
		return offspring;
	}

	public static double[] mutate(double[] weights) {
		Random ran = new Random();
		for (int a = 0; a < weights.length; a++) {
			if (ran.nextDouble() < mutationRate) {
				int b = ran.nextInt(weights.length);
				
				double temp = weights[a];
				weights[a] = weights[b];
				weights[b] = temp;
			}
		}
		return null;
	}
}