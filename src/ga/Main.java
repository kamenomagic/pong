package ga;

import java.util.Arrays;
import java.util.Random;

import static ga.GeneticAlgorithm.crossover;
import static ga.GeneticAlgorithm.fitness;
import static ga.GeneticAlgorithm.mutate;


public class Main {

	public static final int populationSize = 1000;
	public static final int genomeSize = 82;
	public static final double survivalRate = 0.01;
	public static final int survivalCount = (int)(populationSize * survivalRate);
	public static final int keepSurvivorsCount = 0; // must be less than survivalCount
	public static final double randomSurvivalRate = 0.005;
	public static final int generations = 1000;

	public static void main(String[] args) {

		Random rand = new Random();

		//initialization
		double population[][] = new double[populationSize][];
		int generation = 0;
		//random weights
		for (int i = 0; i < populationSize; i++) {
			double genome[] = new double[genomeSize];
			for (int j = 0; j < genomeSize; j++) {
				genome[j] = (rand.nextDouble() - 0.5);
			}
			population[i] = genome;
		}

		while(true) {
			//selection
			System.out.println();
			System.out.print("generation (" + generation + "): ");
			double fitnesses[] = fitness(population, false);
			double sortedFitnesses[] = Arrays.copyOf(fitnesses, fitnesses.length);
			Arrays.sort(sortedFitnesses);
			double minimumFitness = sortedFitnesses[populationSize - survivalCount];
			System.out.print(minimumFitness + ", ");
			System.out.print(sortedFitnesses[sortedFitnesses.length - 1]);

			double survivors[][] = new double[survivalCount][];
			int nextSurvivor = 0;
			for (int i = 0; i < populationSize; i++) {
				if (fitnesses[i] >= minimumFitness || rand.nextDouble() < randomSurvivalRate) {

					survivors[nextSurvivor++] = population[i];
				}
				if(fitnesses[i] == sortedFitnesses[sortedFitnesses.length -1]) {
					fitness(population[i], generation > 15);
				}
				if (nextSurvivor >= survivalCount) {
					break;
				}
			}

			population = new double[populationSize][];

			//previous generation survivors
			for (int i = 0; i < keepSurvivorsCount; i++) {
				population[i] = survivors[i];
			}

			//genetic operators
			for (int i = keepSurvivorsCount; i < populationSize; i++) {
				int parent1 = rand.nextInt(survivors.length);
				int parent2 = rand.nextInt(survivors.length);

				double afterCrossover[] = crossover(survivors[parent1], survivors[parent2]);

				double afterMutation[] = mutate(afterCrossover);

				population[i] = afterMutation;
			}

			//termination
			if (generation++ > generations) {
				break;
			}
		}
	}
}