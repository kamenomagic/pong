package ga;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static ga.GeneticAlgorithm.crossover;
import static ga.GeneticAlgorithm.fitness;
import static ga.GeneticAlgorithm.mutate;


public class Main {

	public static final int populationSize = 1000;
	public static final int genomeSize = 82;
	public static final double survivalRate = 0.1;
	public static final int survivalCount = (int)(populationSize * survivalRate);
	public static final int keepSurvivorsCount = 3; // must be less than survivalCount
	public static final double randomSurvivalRate = 0.005;
	public static final int generations = 1000;
	private static final double mutationRate = 0.60;

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
			double fitnesses[] = fitness(population, false/*generation > 60*/);

			List<Tuple<double[], Double>> survivors = new ArrayList<Tuple<double[], Double>>();
			for (int i = 0; i < population.length; i++) {
				survivors.add(new Tuple<double[],Double>(population[i], fitnesses[i]));
			}

			Comparator<Tuple<double[], Double>> tupleComparator = new Comparator<Tuple<double[], Double>>()
		    {

		        public int compare(Tuple<double[], Double> tupleA,
		                Tuple<double[], Double> tupleB)
		        {
		            return tupleA.getSecond().compareTo(tupleB.getSecond());
		        }

		    };

		    Collections.sort(survivors, tupleComparator.reversed());

		    survivors = (List<Tuple<double[], Double>>) survivors.subList(0, survivalCount);

		    System.out.print(survivors.get(0).getSecond() + "," + survivors.get(survivors.size() - 1).getSecond());

			population = new double[populationSize][];


			//previous generation survivors
			for (int i = 0; i < keepSurvivorsCount; i++) {
				population[i] = survivors.get(i).getFirst();
			}

			//genetic operators
			for (int i = keepSurvivorsCount; i < populationSize; i++) {
				int parent1 = rand.nextInt(4);
				int parent2 = rand.nextInt(survivors.size());

				double afterCrossover[] = crossover(survivors.get(parent1).getFirst(), survivors.get(parent2).getFirst());

				double afterMutation[] = mutate(afterCrossover, mutationRate);

				population[i] = afterMutation;
			}

			//termination
			if (generation++ > generations) {
				break;
			}
		}
	}

}