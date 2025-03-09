import logic.Chromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final double CROSSOVER_RATE = 80; // 80% chance to crossover
    private static final double MUTATION_RATE = 0.10;  // 10% chance to mutate

    public static void main(String[] args) {
        int numChromo, numGen;
        double totalFitness;
        ArrayList<Chromosome> population = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("--------------------- Hello User -------------------");

        // Ensure numChromo is even
        do {
            System.out.print("Enter the number of chromosomes you want (even number): ");
            numChromo = scanner.nextInt();

            if (numChromo % 2 != 0) {
                System.out.println("{ Error: Invalid input. " + numChromo + " is not an even number. Try again. }");
            }
        } while (numChromo % 2 != 0);

        System.out.print("Enter the number of generations: ");
        numGen = scanner.nextInt();

        // Initialize Population
        totalFitness = 0;
        for (int i = 0; i < numChromo; i++) {
            Chromosome chromosome = Chromosome.generateRandom();
            population.add(chromosome);
            totalFitness += chromosome.getFitness();
        }

        Chromosome maxChromo = getMaxFitnessChromo(population);
        // Run Genetic Algorithm
        for (int gen = 0; gen < numGen; gen++) {
            System.out.println("\n=== Generation " + (gen + 1) + " ===");
            ArrayList<Chromosome> newPopulation = new ArrayList<>();

            // Step 1: Selection & Crossover
            for (int i = 0; i < numChromo / 2; i++) {
                // Select parents
                Chromosome parent1 = rouletteSelection(population, totalFitness, rand);
                Chromosome parent2 = rouletteSelection(population, totalFitness, rand);

                // Decide whether to crossover
                if (rand.nextDouble() * 100 < CROSSOVER_RATE) {
                    Chromosome[] offspring = Chromosome.crossover(parent1, parent2);
                    newPopulation.add(offspring[0]);
                    newPopulation.add(offspring[1]);
                } else {
                    newPopulation.add(parent1);
                    newPopulation.add(parent2);
                }
            }

            // Step 2: Mutation
            for (int i = 0; i < newPopulation.size(); i++) {
                if (rand.nextDouble() * 100 < MUTATION_RATE) {
                    newPopulation.set(i, Chromosome.mutate(newPopulation.get(i)));
                }
            }

            Chromosome tmpChromo = getMaxFitnessChromo(newPopulation);
            if(tmpChromo.getFitness() > maxChromo.getFitness()){
                maxChromo = tmpChromo;
            }

            // Update population
            population = newPopulation;

            // Recalculate total fitness
            totalFitness = 0;
            for (Chromosome c : population) {
                totalFitness += c.getFitness();
            }
        }

        // Display Final Population
        System.out.println("\nFinal Population:");
        for (Chromosome c : population) {
            c.printBoard();
            System.out.printf("Fitness: %.2f \n", c.getFitness());
            System.out.println("----------------------");
        }

        System.out.println("\n----------------------------------------------");
        System.out.println("Min fitness found: ");
        maxChromo.printBoard();
        System.out.printf("Fitness: %.2f \n", maxChromo.getFitness());
        System.out.println("----------------------");
    }

    // Roulette Wheel Selection
    private static Chromosome rouletteSelection(ArrayList<Chromosome> population, double totalFitness, Random rand) {
        double randomPick = rand.nextDouble() * 100.0;  // Random value between 0 and 100
        double cumulativeSum = 0.0;

        for (Chromosome c : population) {
            cumulativeSum += c.getProbability(totalFitness);
            if (randomPick < cumulativeSum) {
                return c;
            }
        }
        return population.getLast(); // Edge case: return last if rounding issues occur
    }

    private static Chromosome getMaxFitnessChromo(List<Chromosome> population){
        Chromosome min = population.getFirst();
        for (Chromosome cm : population) {
            if (cm.getFitness() > min.getFitness()){
                min = cm;
            }
        }
        return min;
    }
}
